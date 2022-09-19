package com.denuradhan.cashbookapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.denuradhan.cashbookapp.helper.Helper

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var ed_username : EditText
    lateinit var ed_password : EditText

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ed_username  = findViewById(R.id.ed_username)
        ed_password  = findViewById(R.id.ed_password)

        val btn_login : Button = findViewById(R.id.btn_login)
        val btn_register : TextView = findViewById(R.id.btn_register)

        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.btn_login ->{

                val db = Helper(this, null)

                val username = ed_username.text.toString()
                val password = ed_password.text.toString()

                var isEmptyFields = false

                if (username.isEmpty()) {
                    isEmptyFields = true
                    ed_username.error = "Masukan username anda"
                }
                if (password.isEmpty()) {
                    isEmptyFields = true
                    ed_password.error = "Masukan password anda"
                }

                if (!isEmptyFields) {

                    val cursor = db.cekLogin(username, password)

                    cursor!!.moveToFirst()

                    if (cursor.moveToFirst()){
                        Toast.makeText(
                            this@LoginActivity,
                            "Login Berhasil!",
                            Toast.LENGTH_SHORT
                        ).show()

                        ed_username.text.clear()
                        ed_password.text.clear()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra(MainActivity.extra_username, username)
                        startActivity(intent)
                    }
                    else{
                        ed_username.text.clear()
                        ed_password.text.clear()

                        Toast.makeText(
                            this@LoginActivity,
                            "Username atau password salah!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            R.id.btn_register ->{
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}