package com.denuradhan.cashbookapp

import com.denuradhan.cashbookapp.adapter.AdapterRV
import com.denuradhan.cashbookapp.helper.Helper
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetailCashFlowActivity : AppCompatActivity() {
    companion object{
        const val extra_username = "username"
    }

    var username = "username"

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_cash_flow)

        val recyclerView : RecyclerView = findViewById(R.id.list_view)

        val listData_status = ArrayList<String>()
        val listData_tanggal = ArrayList<String>()
        val listData_nominal = ArrayList<Int>()
        val listData_keterangan = ArrayList<String>()
        val listData_img = ArrayList<String>()


//        val myListAdapter = com.faridaziz.sqlite1.adapter.Adapter(this, listData_tanggal,listData_nominal,listData_keterangan)
//        listView.adapter = myListAdapter

        val adapterRV = AdapterRV(listData_status,listData_tanggal, listData_nominal, listData_keterangan, listData_img)

        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapterRV

        username = intent.getStringExtra(MainActivity.extra_username).toString()

        //read sqlide data
        val db = Helper(this, null)

        val cursor = db.getUang()

        cursor!!.moveToFirst()

        if (cursor.moveToFirst()){
            listData_status += (cursor.getString(cursor.getColumnIndex(Helper.STATUS)) + "\n")
            listData_tanggal += (cursor.getString(cursor.getColumnIndex(Helper.TANGGAL_COl)) + "\n")
            listData_nominal += (cursor.getInt(cursor.getColumnIndex(Helper.NOMINAL_COL)))
            listData_keterangan += (cursor.getString(cursor.getColumnIndex(Helper.KETERANGAN_COL)) + "\n")
            listData_img += (cursor.getString(cursor.getColumnIndex(Helper.IMG)) + "\n")

            while(cursor.moveToNext()){
                listData_status += (cursor.getString(cursor.getColumnIndex(Helper.STATUS)) + "\n")
                listData_tanggal += (cursor.getString(cursor.getColumnIndex(Helper.TANGGAL_COl)) + "\n")
                listData_nominal += (cursor.getInt(cursor.getColumnIndex(Helper.NOMINAL_COL)))
                listData_keterangan += (cursor.getString(cursor.getColumnIndex(Helper.KETERANGAN_COL)) + "\n")
                listData_img += (cursor.getString(cursor.getColumnIndex(Helper.IMG)) + "\n")
            }
        }

        cursor.close()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val back = Intent(this@DetailCashFlowActivity, MainActivity::class.java)
        back.putExtra(MainActivity.extra_username, username)
        overridePendingTransition(0, 0)
        startActivity(back)
        overridePendingTransition(0, 0)
    }
}