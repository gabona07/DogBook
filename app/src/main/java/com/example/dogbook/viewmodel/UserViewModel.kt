package com.example.dogbook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.dogbook.model.AuthUser
import com.example.dogbook.repository.UserRepository

class UserViewModel(application: Application, private val repository: UserRepository): AndroidViewModel(application) {

    fun registerUser(email: String, password: String): LiveData<AuthUser> {
        return repository.registerUser(email, password)
    }

}