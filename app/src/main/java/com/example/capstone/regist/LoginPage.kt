package com.example.capstone.regist

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.R
import com.example.capstone.ui.home.HomeFragment
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginPage : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var showHidePasswordIcon: ImageView
    private lateinit var signUpTextView: TextView
    private lateinit var loginButton: MaterialButton
    private var isPasswordVisible = false
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        showHidePasswordIcon = findViewById(R.id.showHidePasswordIcon)
        signUpTextView = findViewById(R.id.signupHereReg)
        loginButton = findViewById(R.id.loginButton)


        showHidePasswordIcon.setOnClickListener {
            togglePasswordVisibility()
        }


        signUpTextView.setOnClickListener {
            val intent = Intent(this, RegistPage::class.java)
            startActivity(intent)
        }


        loginButton.setOnClickListener {
            performLogin()
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

    private fun performLogin() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()


        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            emailEditText.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Invalid email format"
            emailEditText.requestFocus()
            return
        }
        if (password.isEmpty()) {
            passwordEditText.error = "Password is required"
            passwordEditText.requestFocus()
            return
        }


        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful, navigate to HomeFragment
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    navigateToHomeFragment()
                } else {
                    // Handle login failure
                    handleLoginError(task.exception)
                }
            }
    }

    private fun handleLoginError(exception: Exception?) {
        when (exception) {
            is FirebaseAuthInvalidUserException -> {
                Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
            }
            is FirebaseAuthInvalidCredentialsException -> {
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Login Failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHomeFragment() {
        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_home, homeFragment)
            .commit()
    }
}
