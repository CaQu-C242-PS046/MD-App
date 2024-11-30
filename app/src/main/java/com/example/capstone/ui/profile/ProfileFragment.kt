package com.example.capstone.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.capstone.R
import com.example.capstone.regist.LoginPage
import com.example.capstone.ui.change.ChangePassword
import com.example.capstone.utils.SharedPreferencesHelper

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi SharedPreferencesHelper
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())

        // Bind views
        val ivProfilePicture = view.findViewById<ImageView>(R.id.ivProfilePicture)
        val ivEditProfile = view.findViewById<ImageView>(R.id.ivEditProfile)
        val tvProfileName = view.findViewById<TextView>(R.id.tvProfileName)
        val tvStats = view.findViewById<TextView>(R.id.tvStats)
        val statsSection = view.findViewById<LinearLayout>(R.id.statsSection)
        val tvChangePassword = view.findViewById<TextView>(R.id.tvChangePassword)
        val tvTerms = view.findViewById<TextView>(R.id.tvTerms)
        val btnLogOut = view.findViewById<Button>(R.id.btnLogOut)

        val changePasswordSection = view.findViewById<LinearLayout>(R.id.changePasswordSection)

        changePasswordSection.setOnClickListener {
            // Berpindah ke aktivitas ChangePassword
            val intent = Intent(requireContext(), ChangePassword::class.java)
            startActivity(intent)
        }

        // Ambil username dari SharedPreferencesHelper
        val username = sharedPreferencesHelper.getUsername() ?: "User"
        tvProfileName.text = username

        // Simulasikan kondisi untuk menampilkan status statistik
        val hasRecommendation = sharedPreferencesHelper.getAccessToken() != null
        if (hasRecommendation) {
            tvStats.text = "Your stats: Expert Level"
            tvStats.setTextColor(resources.getColor(R.color.black))
        } else {
            tvStats.text = "Awaiting recommendations..."
            tvStats.setTextColor(resources.getColor(R.color.white))
        }

        // Tombol Logout
        btnLogOut.setOnClickListener {
            // Hapus semua data pengguna dari SharedPreferencesHelper
            sharedPreferencesHelper.clearUserData()

            // Berpindah ke halaman Login
            val intent = Intent(requireContext(), LoginPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
