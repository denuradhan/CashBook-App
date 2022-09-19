package com.denuradhan.cashbookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.denuradhan.cashbookapp.helper.Helper

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var rg_username : EditText
    lateinit var rg_password : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        rg_username  = findViewById(R.id.rg_username)
        rg_password = findViewById(R.id.rg_password)

        val btn_kembali : Button = findViewById(R.id.btn_kembali)
        val btn_register : Button = findViewById(R.id.btn_simpanRegister)

        btn_kembali.setOnClickListener(this)
        btn_register.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.btn_simpanRegister ->{
                val db = Helper(this@RegisterActivity, null)

                val username = rg_username.text.toString()
                val password = rg_password.text.toString()

                var isEmptyFields = false

                if (username.isEmpty()) {
                    isEmptyFields = true
                    rg_username.error = "Masukan username anda"
                }
                if (password.isEmpty()) {
                    isEmptyFields = true
                    rg_password.error = "Masukan password anda"
                }

                if (!isEmptyFields) {

                    db.addUser(username,password)

                    Toast.makeText(
                        this@RegisterActivity,
                        "Registrasi Berhasil!",
                        Toast.LENGTH_SHORT
                    ).show()

                    onBackPressed()
                }
            }
            R.id.btn_kembali ->{
                onBackPressed()
            }
        }
    }
}