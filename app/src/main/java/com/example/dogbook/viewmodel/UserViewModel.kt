package com.example.dogbook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.dogbook.model.User
import com.example.dogbook.repository.UserRepository

class UserViewModel(application: Application, private val repository: UserRepository): AndroidViewModel(application) {

    constructor(application: Application) : this(application, UserRepository())

    fun registerUser(email: String, password: String): LiveData<User> {
        return repository.registerUser(email, password)
    }

}