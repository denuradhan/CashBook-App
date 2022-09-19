package com.denuradhan.cashbookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.denuradhan.cashbookapp.helper.Helper

class PengaturanActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val extra_username = "username"
    }

    var username = "username"

    lateinit var tv_username : TextView
    lateinit var ed_passwordLama : EditText
    lateinit var ed_passwordBaru : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)

        ed_passwordLama = findViewById(R.id.ed_passwordLama)
        ed_passwordBaru = findViewById(R.id.ed_passwordBaru)
        var btn_simpanPassword: Button = findViewById(R.id.btn_simpanPassword)
        val btn_kembali: Button = findViewById(R.id.btn_kembali)

        //dump
        tv_username = findViewById(R.id.tv_username)
        username = intent.getStringExtra(extra_username).toString()
        tv_username.setText(username)

        btn_kembali.setOnClickListener(this)
        btn_simpanPassword.setOnClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val back = Intent(this@PengaturanActivity, MainActivity::class.java)
        back.putExtra(MainActivity.extra_username, username)
        overridePendingTransition(0, 0)
        startActivity(back)
        overridePendingTransition(0, 0)
    }

    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.btn_simpanPassword ->{
                val db = Helper(this, null)

                val username = tv_username.text.toString()
                val passwordLama = ed_passwordLama.text.toString()
                val passwordBaru = ed_passwordBaru.text.toString()

                var isEmptyFields = false

                if (passwordLama.isEmpty()) {
                    isEmptyFields = true
                    ed_passwordLama.error = "Masukan password lama anda"
                }
                if (passwordBaru.isEmpty()) {
                    isEmptyFields = true
                    ed_passwordBaru.error = "Masukan password baru anda"
                }

                if (!isEmptyFields) {

                    val cursor = db.cekLogin(username, passwordLama)

                    cursor!!.moveToFirst()

                    if (cursor.moveToFirst()) {
                        db.updatePassword(username, passwordBaru)

                        Toast.makeText(
                            this@PengaturanActivity,
                            "Ganti password berhasil!",
                            Toast.LENGTH_SHORT
                        ).show()
                        onBackPressed()

                    } else {
                        ed_passwordLama.text.clear()
                        ed_passwordBaru.text.clear()

                        Toast.makeText(
                            this@PengaturanActivity,
                            "Password lama salah!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            R.id.btn_kembali ->{
                onBackPressed()
            }

        }

    }
}