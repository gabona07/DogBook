package com.example.dogbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dogbook.R
import com.example.dogbook.model.Dog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_form.*
import java.util.*

class FormActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
    }

    fun validateData(view: View) {
        val dogName = formDogName.text.toString()
        val ownerName = formOwnerName.text.toString()
        val location = formLocation.text.toString()
        val dogPersonality = formDogPersonality.text.toString()
        val description = formDescription.text.toString()

        if (dogName.isNotEmpty() && ownerName.isNotEmpty() && location.isNotEmpty() && dogPersonality.isNotEmpty() && description.isNotEmpty()) {
            saveDogToDatabase(dogName, ownerName, location, dogPersonality, description)
        }
    }

    private fun saveDogToDatabase(dogName: String,ownerName: String, location: String, dogPersonality: String, description: String) {
        val ownerUid = FirebaseAuth.getInstance().uid ?: ""
        val dogUid = UUID.randomUUID().toString()
        val reference = database.getReference("/users/$ownerUid/$dogUid")
        val dog = Dog(ownerUid, dogUid, dogName, ownerName, location, dogPersonality, description)
        reference.setValue(dog)
    }
}