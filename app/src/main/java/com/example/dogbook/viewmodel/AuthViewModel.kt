package com.example.dogbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogbook.model.AuthUser
import com.example.dogbook.repository.AuthRepository

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val currentUser = MutableLiveData<AuthUser.OnSuccess>()

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

    fun getCurrentUserData(): LiveData<AuthUser.OnSuccess> {
        return currentUser
    }

    fun checkForCurrentUser() {
        val user = repository.getCurrentUser()
        if (user != null) {
            currentUser.value = AuthUser.OnSuccess(user.uid, user.email!!)
        } else {
            currentUser.value = null
        }
    }
}