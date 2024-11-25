package com.example.capstone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone.utils.SharedPreferencesHelper

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    // Optional: Tambahkan metode untuk memperbarui teks jika diperlukan
    fun setText(value: String) {
        _text.value = value
    }
}

