package com.example.dogbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dogbook.R
import com.example.dogbook.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(UserViewModel::class.java)
        setRegistrationListener()
    }

    private fun setRegistrationListener() {
        registerButton.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                userViewModel.registerUser(email, password).observe(this, {
                    when (it.authException) {
                        null -> println("oké")
                        is FirebaseAuthUserCollisionException -> Toast.makeText(this,"${it.authException.message}", Toast.LENGTH_SHORT).show()
                        is FirebaseAuthInvalidCredentialsException -> Toast.makeText(this,"${it.authException.message}", Toast.LENGTH_SHORT).show()
                        is FirebaseAuthWeakPasswordException -> Toast.makeText(this,"${it.authException.message}", Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(this,"${it.authException.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}