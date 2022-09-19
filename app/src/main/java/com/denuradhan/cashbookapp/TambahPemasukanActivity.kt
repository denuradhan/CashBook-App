package com.denuradhan.cashbookapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.denuradhan.cashbookapp.helper.Helper
import java.text.SimpleDateFormat
import java.util.*

class TambahPemasukanActivity : AppCompatActivity() {

    var button_date: ImageButton? = null
    var textview_date: TextView? = null
    var  button_simpan: Button? = null
    var nominalSimpan: EditText? = null
    var ed_keterangan: EditText? = null
    var cal = Calendar.getInstance()
    var btn_kembali: Button? = null
    var username = "username"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pemasukan)

        textview_date = findViewById(R.id.tanggalPemasukan)
        button_date = findViewById(R.id.btn_kalenderPemasukan)
        button_simpan = findViewById(R.id.btn_simpanPemasukan)
        nominalSimpan = findViewById(R.id.nominalPemasukan)
        ed_keterangan = findViewById(R.id.keteranganPemasukan)
        btn_kembali = findViewById(R.id.btn_kembali)

        username = intent.getStringExtra(MainActivity.extra_username).toString()

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        button_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@TambahPemasukanActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        button_simpan!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val db = Helper(this@TambahPemasukanActivity, null)

                // creating variables for values
                // in name and age edit texts
                val status = "[+] Rp."
                val tanggal = textview_date!!.text.toString()
                val nominal = nominalSimpan!!.text.toString()
                val keterangan = ed_keterangan!!.text.toString()
                val img = "in"

                var isEmptyFields = false

                if (tanggal.isEmpty()) {
                    isEmptyFields = true
                    textview_date!!.error = "Tanggal tidak boleh kosong"
                }
                if (nominal.isEmpty()) {
                    isEmptyFields = true
                    nominalSimpan!!.error = "Nominal tidak boleh kosong"
                }
                if (keterangan.isEmpty()) {
                    isEmptyFields = true
                    ed_keterangan!!.error = "Keterangan tidak boleh kosong"
                }

                if (!isEmptyFields){
                    // calling method to add
                    // name to our database
                    db.addUang(status,tanggal, nominal, keterangan, img)

                    // at last, clearing edit texts
                    nominalSimpan!!.text.clear()
                    ed_keterangan!!.text.clear()

                    Toast.makeText(this@TambahPemasukanActivity,"Data Berhasil Disimpan!", Toast.LENGTH_SHORT).show()
                    finish()
                    onBackPressed()
                }

            }
        })

        btn_kembali!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                onBackPressed()
            }
        })

    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date?.setText(sdf.format(cal.getTime()))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val back = Intent(this@TambahPemasukanActivity, MainActivity::class.java)
        back.putExtra(MainActivity.extra_username, username)
        overridePendingTransition(0, 0)
        startActivity(back)
        overridePendingTransition(0, 0)
    }

    companion object{
        const val extra_username = "username"
    }
}