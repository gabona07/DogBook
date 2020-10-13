package com.example.dogbook.repository

import androidx.lifecycle.MutableLiveData
import com.example.dogbook.model.Dog
import com.google.firebase.auth.FirebaseAuth

class DogRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val dogs = MutableLiveData<Dog>()

}