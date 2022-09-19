package com.denuradhan.cashbookapp.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Helper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query1 = ("CREATE TABLE " + Helper.TABLE_NAME1 + " ("
                + Helper.ID_COL + " INTEGER PRIMARY KEY, " +
                Helper.STATUS + " TEXT," +
                Helper.TANGGAL_COl + " TEXT," +
                Helper.NOMINAL_COL + " INTEGER," +
                Helper.KETERANGAN_COL + " TEXT," +
                Helper.IMG + " TEXT" + ")")

        val query2 = ("CREATE TABLE " + Helper.TABLE_NAME2 + " ("
                + Helper.ID_COL + " INTEGER PRIMARY KEY, " +
                Helper.USERNAME + " TEXT," +
                Helper.PASSWORD + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query1)
        db.execSQL(query2)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addUang(status : String,tanggal : String, nominal : String , keterangan : String, img : String){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(Helper.STATUS, status)
        values.put(Helper.TANGGAL_COl, tanggal)
        values.put(Helper.NOMINAL_COL, nominal)
        values.put(Helper.KETERANGAN_COL, keterangan)
        values.put(Helper.IMG, img)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(Helper.TABLE_NAME1, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    fun addUser(username : String, password : String){
        val values = ContentValues()

        values.put(Helper.USERNAME, username)
        values.put(Helper.PASSWORD, password)

        val db = this.writableDatabase

        db.insert(Helper.TABLE_NAME2, null, values)
        db.close()
    }

//    fun getUser(): Cursor? {
//        val db = this.readableDatabase
//        return db.rawQuery("SELECT * FROM " + Helper.TABLE_NAME2, null)
//    }

    fun cekLogin(username: String, password: String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + Helper.TABLE_NAME2 + " WHERE " + Helper.USERNAME + " = '$username' " + " AND " + Helper.PASSWORD + " = '$password' ", null)
    }

    fun updatePassword(username: String, new_password: String){
        val values = ContentValues()

        values.put(Helper.PASSWORD, new_password)

        val db = this.writableDatabase

        db.update(Helper.TABLE_NAME2, values, Helper.USERNAME + " = '$username' ", null)
    }

    // below method is to get
    // all data from our database
    fun getUang(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

//        val arrayList = ArrayList<String>()
        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + Helper.TABLE_NAME1, null)

    }

    fun getPemasukan(): Cursor? {
        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM " + Helper.TABLE_NAME1 + " WHERE " + Helper.IMG + " = 'in' ", null)
    }

    fun getPengeluaran(): Cursor? {
        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM " + Helper.TABLE_NAME1 + " WHERE " + Helper.IMG + " = 'out' ", null)
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "PENCATATAN_KEUANGAN"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME1 = "UANG"
        val TABLE_NAME2 = "USER"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val TANGGAL_COl = "tanggal"

        // below is the variable for age column
        val NOMINAL_COL = "nominal"
        val KETERANGAN_COL = "keterangan"
        val STATUS = "status"
        val IMG = "img"

        val USERNAME = "username"
        val PASSWORD = "password"
    }
}
