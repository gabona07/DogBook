package com.example.dogbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dogbook.model.AuthUser
import com.example.dogbook.repository.UserRepository

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun registerUser(email: String, password: String) {
        repository.registerUser(email, password)
    }

    fun loginUser(email: String, password: String) {
        repository.loginUserWithEmailAndPassword(email, password)
    }

    fun getAuthUserData(): LiveData<AuthUser> {
        return repository.getAuthUser()
    }
}