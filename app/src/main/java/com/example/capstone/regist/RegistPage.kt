package com.example.capstone.regist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.MainActivity
import com.example.capstone.databinding.RegisterPageBinding
import com.example.capstone.retrofit.ApiClient
import com.example.capstone.retrofit.ApiService
import com.example.capstone.retrofit.RegisterRequest
import com.example.capstone.retrofit.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistPage : AppCompatActivity() {

    private lateinit var binding: RegisterPageBinding
    private lateinit var apiService: ApiService
    private val TAG = "RegistPage" // Tag untuk logging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = ApiClient.getClient()
        apiService = retrofit.create(ApiService::class.java)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.signupButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (validateInputs(username, email, password)) {
                register(username, email, password)
            }
        }
    }

    private fun validateInputs(username: String, email: String, password: String): Boolean {
        var isValid = true

        if (username.isEmpty() || username.length < 3) {
            binding.usernameEditText.error = "Username minimal 3 karakter"
            isValid = false
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEditText.error = "Format email tidak valid"
            isValid = false
        }

        if (password.isEmpty() || password.length < 8) {
            binding.passwordEditText.error = "Password minimal 8 karakter"
            isValid = false
        }

        return isValid
    }

    private fun register(username: String, email: String, password: String) {
        binding.signupButton.isEnabled = false

        val registerRequest = RegisterRequest(username, email, password)
        apiService.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                binding.signupButton.isEnabled = true

                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse?.success == true) {
                        Toast.makeText(this@RegistPage, registerResponse.message, Toast.LENGTH_SHORT).show()
                        navigateToMainActivity()
                    } else {
                        Toast.makeText(this@RegistPage, registerResponse?.message ?: "Registrasi gagal", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegistPage, "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                binding.signupButton.isEnabled = true
                Toast.makeText(this@RegistPage, "Gagal terhubung ke server: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Network error", t)
            }
        })
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}
