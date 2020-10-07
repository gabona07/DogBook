package com.example.dogbook.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dogbook.repository.SharedPrefRepository

class SharedPrefViewModel(private val repository: SharedPrefRepository) : ViewModel() {

    fun saveUserToken(userToken: String) {
        repository.saveUserToken(userToken)
    }

    fun getUserToken(): String? {
        return repository.getUserToken()
    }
}