package com.example.dogbook.repository

import androidx.lifecycle.MutableLiveData
import com.example.dogbook.model.Dog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class UserRepository {

    private val dogs = MutableLiveData<Dog>()

    private val database = FirebaseDatabase.getInstance()

    fun saveDogToDatabase(dog: Dog) {
        dog.ownerUid = FirebaseAuth.getInstance().uid ?: ""
        dog.dogUid = UUID.randomUUID().toString()
        val reference = database.getReference("/users/$dog.ownerUid/$dog.dogUid")
        reference.setValue(dog)
    }
}