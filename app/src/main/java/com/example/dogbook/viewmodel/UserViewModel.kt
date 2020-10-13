package com.example.dogbook.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dogbook.model.Dog
import com.example.dogbook.repository.UserRepository

class UserViewModel(private val repository: UserRepository): ViewModel() {


    fun saveDogToDatabase(dog: Dog) {
        repository.saveDogToDatabase(dog)
    }
}