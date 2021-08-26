package com.example.glucometrix

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity


class Account : AppCompatActivity() {
    lateinit var imageView: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null

    companion object {
        var img: String? = null
    }

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        val curLogin = findViewById<TextView>(R.id.loginNameData)
        val curPass = findViewById<TextView>(R.id.passwordNameData)

        curLogin.text = DatabaseHandler(applicationContext).showLogin()
        curPass.text = DatabaseHandler(applicationContext).showPassword()
        val currID = DatabaseHandler(applicationContext).showID()

        imageView = findViewById(R.id.accountImage)

        val changeLoginButton:Button  = findViewById(R.id.buttonChangeLogin)
        val changePasswordButton:Button  = findViewById(R.id.buttonChangePassword)
        val inputLogin: EditText = findViewById(R.id.loginChanges)
        val inputPassword: EditText = findViewById(R.id.passwordChanges)
        changeLoginButton.setOnClickListener{
            val login = inputLogin.text.toString()
            DatabaseHandler(this).changeUserData("login", login, currID)
            curLogin.text = login
            Toast.makeText(applicationContext, "Login was changed", LENGTH_LONG)
        }
        changePasswordButton.setOnClickListener{
            val password = inputPassword.text.toString()
            DatabaseHandler(this).changeUserData("password", password, currID)
            curPass.text = password
            Toast.makeText(applicationContext, "Password was changed", LENGTH_LONG)
        }
        /* load image from gallery and display it, after pressing the back button image disapear
        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }*/
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }
    override fun onResume() {
        super.onResume()
        val sharedPref = getPreferences(MODE_PRIVATE)
        img = sharedPref.getString(
            "dane",
            "")
        //imageView.setImageURI(Uri.parse(img))
    }

    override fun onPause() {
        super.onPause()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("dane", imageUri.toString())
        editor.apply()
    }
}