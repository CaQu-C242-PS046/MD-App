package com.example.capstone.regist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.R

class GetStarted : AppCompatActivity() {

    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_started)


        registerButton = findViewById(R.id.button1)
        loginButton = findViewById(R.id.button2)


        registerButton.setOnClickListener {
            navigateToRegister()
        }

        loginButton.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToRegister() {
        try {
            val intent = Intent(this, RegistPage::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("GetStarted", "Error navigating to register: ${e.message}")
        }
    }
    private fun navigateToLogin() {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)

    }
}
