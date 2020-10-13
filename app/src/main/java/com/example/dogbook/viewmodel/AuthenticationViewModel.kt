package com.example.dogbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dogbook.model.AuthUser
import com.example.dogbook.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

class AuthenticationViewModel(private val repository: AuthenticationRepository) : ViewModel() {

    fun registerUser(email: String, password: String) {
        repository.registerUser(email, password)
    }

    fun loginUser(email: String, password: String) {
        repository.loginUserWithEmailAndPassword(email, password)
    }

    fun getAuthUserData(): LiveData<AuthUser> {
        return repository.getAuthUser()
    }

    fun getCurrentUser(): FirebaseUser? {
        return repository.getCurrentUser()
    }
}