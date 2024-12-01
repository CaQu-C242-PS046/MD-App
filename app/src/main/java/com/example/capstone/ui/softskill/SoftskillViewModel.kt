package com.example.capstone.ui.softskill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone.retrofit.BaseClient
import com.example.capstone.retrofit.ResponseSoftSkills
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SoftskillViewModel : ViewModel() {

    private val _softSkills = MutableLiveData<List<String>>()
    val softSkills: LiveData<List<String>> get() = _softSkills

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val api = BaseClient.getSoftSkillsApi()

    // Function to fetch soft skills
    suspend fun fetchSoftSkills() {
        try {
            val response = withContext(Dispatchers.IO) {
                api.getSoftSkillNames() // Fetch the soft skill names from the API
            }
            _softSkills.postValue(response.data)  // Update LiveData with the response data
        } catch (e: Exception) {
            _errorMessage.postValue("Error: ${e.message}")  // In case of error, post error message
        }
    }
}
