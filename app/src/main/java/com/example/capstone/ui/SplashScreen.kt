package com.example.capstone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.capstone.R
import com.example.capstone.regist.GetStarted

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val countDownTimer = object : CountDownTimer(3000L, 1000){
            override fun onTick(millisUnitilFinished: Long) {
            }

            override fun onFinish() {
                navigateToGetStarted()
            }
        }
        countDownTimer.start()
    }
    private fun navigateToGetStarted() {
        val intent = Intent(this, GetStarted::class.java)
        startActivity(intent)
        finish()
    }



}