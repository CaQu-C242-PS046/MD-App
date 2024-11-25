package com.example.capstone.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Menghubungkan ViewModel
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Observasi data dari ViewModel
        homeViewModel.text.observe(viewLifecycleOwner) { welcomeText ->
            binding.welcomeText.text = welcomeText
        }

        // Set quote (dapat diambil dari API)
        binding.subtitleText.text = "“The best time to start was yesterday. The next best time is now.”"

        // Event klik pada tombol rekomendasi
        binding.recommendButton.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur rekomendasi akan datang!", Toast.LENGTH_SHORT).show()
        }

        // Event klik pada tombol riwayat
        binding.historyButton1.setOnClickListener {
            Toast.makeText(requireContext(), "Riwayat 1 diklik!", Toast.LENGTH_SHORT).show()
        }

        binding.historyButton2.setOnClickListener {
            Toast.makeText(requireContext(), "Riwayat 2 diklik!", Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
