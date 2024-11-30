package com.example.capstone.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.databinding.FragmentHomeBinding
import com.example.capstone.utils.SharedPreferencesHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.hide()


        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())


        val username = sharedPreferencesHelper.getUsername() ?: "User"


        binding.welcomeText.text = "Welcome, $username!"


        homeViewModel.text.observe(viewLifecycleOwner) { welcomeText ->
            binding.welcomeText.text = welcomeText
        }


        binding.subtitleText.text = "“The best time to start was yesterday. The next best time is now.”"


        binding.recommendButton.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur rekomendasi akan datang!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
