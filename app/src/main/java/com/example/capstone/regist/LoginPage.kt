package com.example.capstone.regist

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.R

class LoginPage : AppCompatActivity() {

    private lateinit var passwordEditText: EditText
    private lateinit var showHidePasswordIcon: ImageView
    private lateinit var signUpTextView: TextView
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        // Initialize views
        passwordEditText = findViewById(R.id.passwordEditText)
        showHidePasswordIcon = findViewById(R.id.showHidePasswordIcon)
        signUpTextView = findViewById(R.id.signupHereReg)

        // Handle show/hide password icon click
        showHidePasswordIcon.setOnClickListener {
            togglePasswordVisibility()
        }

        // Navigate to Registration page when "sign up" is clicked
        signUpTextView.setOnClickListener {
            val intent = Intent(this, RegistPage::class.java)
            startActivity(intent)
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide password
            passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            showHidePasswordIcon.setImageResource(R.drawable.baseline_visibility_off_24) // Set to "hidden" icon
        } else {
            // Show password
            passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            showHidePasswordIcon.setImageResource(R.drawable.baseline_remove_red_eye_24) // Set to "visible" icon
        }
        isPasswordVisible = !isPasswordVisible
        passwordEditText.setSelection(passwordEditText.text.length) // Maintain cursor position
    }
}
