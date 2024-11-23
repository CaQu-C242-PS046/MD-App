package com.example.capstone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.regist.LoginPage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Mengecek apakah token ada
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("accessToken", null)

        if (accessToken == null) {
            // Token tidak ditemukan, kembali ke LoginPage
            Log.d("MainActivity", "Token tidak ditemukan, kembali ke login.")
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        } else {
            Log.d("MainActivity", "Token ditemukan: $accessToken")
            // Lanjutkan aplikasi ke halaman utama
        }
    }
}
