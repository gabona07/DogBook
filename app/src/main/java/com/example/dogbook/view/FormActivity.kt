package com.example.dogbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dogbook.R
import com.example.dogbook.model.Dog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_form.*

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
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val reference = database.getReference("/users/$uid")
        val dog = Dog(uid, dogName, ownerName, location, dogPersonality, description)
        reference.setValue(dog)
    }
}