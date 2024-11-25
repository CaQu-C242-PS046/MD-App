package com.example.capstone.regist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.MainActivity
import com.example.capstone.databinding.LoginPageBinding
import com.example.capstone.retrofit.ApiClient
import com.example.capstone.retrofit.ApiService
import com.example.capstone.retrofit.LoginRequest
import com.example.capstone.retrofit.LoginResponse
import com.example.capstone.utils.SharedPreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPage : AppCompatActivity() {

    private lateinit var binding: LoginPageBinding
    private lateinit var apiService: ApiService
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi SharedPreferencesHelper
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        // Setup Retrofit
        val retrofit = ApiClient.getClient()
        apiService = retrofit.create(ApiService::class.java)

        // Listener untuk tombol login
        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (validateInputs(username, password)) {
                login(username, password)
            }
        }
    }

    private fun validateInputs(username: String, password: String): Boolean {
        return when {
            username.isEmpty() -> {
                binding.usernameEditText.error = "Username tidak boleh kosong"
                false
            }
            password.isEmpty() -> {
                binding.passwordEditText.error = "Kata sandi tidak boleh kosong"
                false
            }
            password.length < 8 -> {
                binding.passwordEditText.error = "Kata sandi harus memiliki minimal 8 karakter"
                false
            }
            else -> true
        }
    }

    private fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)
        val call = apiService.login(loginRequest)

        binding.loginButton.isEnabled = false // Tampilkan indikator loading

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                binding.loginButton.isEnabled = true

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.success == true) {
                        // Simpan token dan username
                        sharedPreferencesHelper.saveTokens(
                            loginResponse.accessToken,
                            loginResponse.refreshToken
                        )
                        sharedPreferencesHelper.saveUsername(username)

                        Log.d("LoginPage", "Access Token: ${loginResponse.accessToken}")
                        Log.d("LoginPage", "Username: $username")

                        // Beralih ke MainActivity
                        val intent = Intent(this@LoginPage, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginPage,
                            "Login gagal: ${loginResponse?.message ?: "Kesalahan tidak diketahui"}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@LoginPage,
                        "Login gagal: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                binding.loginButton.isEnabled = true
                Toast.makeText(this@LoginPage, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
