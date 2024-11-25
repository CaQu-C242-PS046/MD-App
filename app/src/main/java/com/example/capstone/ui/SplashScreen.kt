package com.example.capstone.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.MainActivity
import com.example.capstone.R
import com.example.capstone.regist.GetStarted
import com.example.capstone.utils.SharedPreferencesHelper

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Hitung mundur selama 3 detik sebelum navigasi
        val countDownTimer = object : CountDownTimer(3000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Tidak ada yang perlu dilakukan pada setiap tick
            }

            override fun onFinish() {
                navigateToNextScreen()
            }
        }
        countDownTimer.start()
    }

    private fun navigateToNextScreen() {
        // Periksa status login dari SharedPreferences
        val sharedPreferencesHelper = SharedPreferencesHelper(this)
        val nextActivity = if (sharedPreferencesHelper.isLoggedIn()) {
            MainActivity::class.java
        } else {
            GetStarted::class.java
        }

        // Navigasi ke aktivitas yang sesuai
        val intent = Intent(this, nextActivity)
        startActivity(intent)
        finish()
    }
}
