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
        // Setup register button listener
        binding.signupButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            // Input validation
            if (validateInputs(username, email, password)) {
                Log.d(TAG, "Starting registration process")
                register(username, email, password)
            }
        }

        // Jika ada tombol back, tambahkan listener
        binding.backButton?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun validateInputs(username: String, email: String, password: String): Boolean {
        var isValid = true

        // Username validation
        when {
            username.isEmpty() -> {
                binding.usernameEditText.error = "Username tidak boleh kosong"
                isValid = false
            }
            username.length < 3 -> {
                binding.usernameEditText.error = "Username minimal 3 karakter"
                isValid = false
            }
        }

        // Email validation
        when {
            email.isEmpty() -> {
                binding.emailEditText.error = "Email tidak boleh kosong"
                isValid = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailEditText.error = "Format email tidak valid"
                isValid = false
            }
        }

        // Password validation
        when {
            password.isEmpty() -> {
                binding.passwordEditText.error = "Kata sandi tidak boleh kosong"
                isValid = false
            }
            password.length < 8 -> {
                binding.passwordEditText.error = "Kata sandi minimal 8 karakter"
                isValid = false
            }
            !password.matches(".*[A-Z].*".toRegex()) -> {
                binding.passwordEditText.error = "Kata sandi harus memiliki minimal 1 huruf besar"
                isValid = false
            }
            !password.matches(".*[a-z].*".toRegex()) -> {
                binding.passwordEditText.error = "Kata sandi harus memiliki minimal 1 huruf kecil"
                isValid = false
            }
            !password.matches(".*[0-9].*".toRegex()) -> {
                binding.passwordEditText.error = "Kata sandi harus memiliki minimal 1 angka"
                isValid = false
            }
        }

        return isValid
    }

    private fun register(username: String, email: String, password: String) {
        // Disable signup button to prevent double submission
        binding.signupButton.isEnabled = false

        // Show loading indicator if you have one
        // binding.progressBar.visibility = View.VISIBLE

        val registerRequest = RegisterRequest(username, email, password)
        val call = apiService.register(registerRequest)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                // Re-enable signup button
                binding.signupButton.isEnabled = true
                // Hide loading indicator
                // binding.progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse != null) {
                        Log.d(TAG, "Registration successful: ${registerResponse.message}")

                        // Show success message
                        Toast.makeText(this@RegistPage, registerResponse.message, Toast.LENGTH_SHORT).show()

                        // Navigate to MainActivity with delay
                        binding.root.postDelayed({
                            try {
                                val intent = Intent(this@RegistPage, MainActivity::class.java).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                }
                                Log.d(TAG, "Starting MainActivity")
                                startActivity(intent)
                                finish()
                                Log.d(TAG, "RegistPage finished")
                            } catch (e: Exception) {
                                Log.e(TAG, "Error starting MainActivity: ${e.message}", e)
                                Toast.makeText(this@RegistPage,
                                    "Terjadi kesalahan saat membuka halaman utama",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }, 500) // Delay 500ms
                    } else {
                        Log.e(TAG, "Registration response body is null")
                        Toast.makeText(this@RegistPage,
                            "Terjadi kesalahan pada server",
                            Toast.LENGTH_SHORT).show()
                    }
                } else {
                    try {
                        val errorBody = response.errorBody()?.string()
                        Log.e(TAG, "Registration failed with error: $errorBody")
                        Toast.makeText(this@RegistPage,
                            "Registrasi gagal: ${errorBody ?: "Terjadi kesalahan"}",
                            Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing error response: ${e.message}", e)
                        Toast.makeText(this@RegistPage,
                            "Terjadi kesalahan saat memproses response server",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "Network error during registration", t)
                // Re-enable signup button
                binding.signupButton.isEnabled = true
                // Hide loading indicator
                // binding.progressBar.visibility = View.GONE

                Toast.makeText(this@RegistPage,
                    "Gagal terhubung ke server: ${t.message}",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "Back button pressed, finishing RegistPage")
        finish()
    }
}