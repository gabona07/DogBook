package com.example.dogbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dogbook.R
import com.example.dogbook.model.AuthUser
import com.example.dogbook.model.Dog
import com.example.dogbook.viewmodel.AuthenticationViewModel
import com.example.dogbook.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_form.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class FormActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModel()

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
            val dog = Dog("", "", dogName, ownerName, location, dogPersonality, description)
            userViewModel.saveDogToDatabase(dog)
        }
    }
}