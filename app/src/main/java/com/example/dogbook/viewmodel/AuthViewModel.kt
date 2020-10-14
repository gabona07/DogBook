package com.example.dogbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dogbook.model.AuthUser
import com.example.dogbook.repository.AuthRepository

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    fun registerUser(email: String, password: String) {
        repository.registerUser(email, password)
    }

    fun loginUser(email: String, password: String) {
        repository.loginUserWithEmailAndPassword(email, password)
    }

    fun loginWithGoogle(idToken: String?) {
        repository.loginWithGoogle(idToken)
    }

    fun getAuthUserData(): LiveData<AuthUser> {
        return repository.getAuthUser()
    }

    fun getCurrentUserData(): LiveData<AuthUser?> {
        return repository.getCurrentUser()
    }

    fun checkForCurrentUser() {
        repository.checkForCurrentUser()
    }
}