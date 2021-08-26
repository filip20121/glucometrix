package com.example.glucometrix

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.glucometrix.dataClass.User

class Login : AppCompatActivity() {

    @SuppressLint("ShowToast", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registerButton = findViewById<Button>(R.id.buttonRegister)
        val loginButton = findViewById<Button>(R.id.buttonLogIn)
        val errorText: TextView = findViewById(R.id.errorMsg)

        registerButton.setOnClickListener(){
            registerNewUser(it)
        }
        loginButton.setOnClickListener() {
            if (validate()) {
                errorText.text=""
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                //Toast.makeText(applicationContext, "Login or password is invalid", Toast.LENGTH_LONG)
                errorText.text = "Nazwa użytkownika lub hasło jest niepoprawne"
            }
        }
    }
    private fun validate(): Boolean{
        val login = findViewById<EditText>(R.id.editTextTextUserLogin)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val loginText = login.text.toString()
        val passwordText = password.text.toString()
        return DatabaseHandler(this).validateUser(loginText.trim(), passwordText.trim())
    }

    @SuppressLint("ShowToast")
    fun registerNewUser(view: View){
        val login = findViewById<EditText>(R.id.editTextTextUserLogin)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val loginText = login.text.toString()
        val passwordText = password.text.toString()

        val databaseHandler = DatabaseHandler(this)
        if(loginText.isNotEmpty() && passwordText.isNotEmpty()) {
            val status = databaseHandler.addUser(User(0, loginText, passwordText))
            if(status > -1) {
                Toast.makeText(applicationContext, "User saved", Toast.LENGTH_SHORT).show()
            }

        }
        else{
            Toast.makeText(applicationContext, "Login and password cannot be blank", Toast.LENGTH_LONG).show()
        }
    }
}