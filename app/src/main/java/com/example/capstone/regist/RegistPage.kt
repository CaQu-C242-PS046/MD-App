package com.example.capstone.regist

import android.content.Intent
import android.os.Bundle
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class RegistPage : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signupButton: MaterialButton
    private lateinit var googleSignInButton: ImageView
    private lateinit var loginTextView: TextView
    private lateinit var backButton: ImageView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize UI elements
        initializeViews()

        // Set up click listeners
        setupClickListeners()
    }

    private fun initializeViews() {
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signupButton = findViewById(R.id.signupButton)
        googleSignInButton = findViewById(R.id.googleSignInButton)
        loginTextView = findViewById(R.id.loginTextView)
        backButton = findViewById(R.id.backButton)
    }

    private fun setupClickListeners() {
        signupButton.setOnClickListener { performSignUp() }
        googleSignInButton.setOnClickListener { performGoogleSignIn() }
        loginTextView.setOnClickListener { navigateToLogin() }
        backButton.setOnClickListener { navigateToGetStarted() }
    }

    private fun performSignUp() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        // Validations
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
        if (password.length < 6) {
            passwordEditText.error = "Password must be at least 6 characters"
            passwordEditText.requestFocus()
            return
        }

        // Sign up with Firebase Auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sign-Up Successful", Toast.LENGTH_SHORT).show()
                    navigateToHome()
                } else {
                    handleSignUpError(task.exception)
                }
            }
    }

    private fun handleSignUpError(exception: Exception?) {
        when (exception) {
            is FirebaseAuthUserCollisionException -> {
                emailEditText.error = "Email already in use"
                emailEditText.requestFocus()
            }
            else -> {
                Toast.makeText(this, "Sign-Up Failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performGoogleSignIn() {
        // Placeholder for Google Sign-In functionality
        Toast.makeText(this, "Google Sign-In Clicked", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHome() {
        // Navigate to HomeActivity after registration
        val intent = Intent(this, HomeFragment::class.java) // Replace HomeActivity with your actual main activity
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToGetStarted() {
        val intent = Intent(this, GetStarted::class.java)
        startActivity(intent)
        finish()
    }
}
