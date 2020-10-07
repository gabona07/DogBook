package com.example.dogbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dogbook.R
import com.example.dogbook.viewmodel.SharedPrefViewModel
import com.example.dogbook.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModel()
    private val sharedPrefViewModel: SharedPrefViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerButton.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val email = email.text.toString()
        val password = password.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            userViewModel.registerUser(email, password).observe(this, { authUser ->
                when (authUser.authException) {
                    null -> sharedPrefViewModel.saveUserToken(authUser.userToken!!)
                    is FirebaseAuthUserCollisionException -> Toast.makeText(this,"${authUser.authException.message}", Toast.LENGTH_SHORT).show()
                    is FirebaseAuthInvalidCredentialsException -> Toast.makeText(this,"${authUser.authException.message}", Toast.LENGTH_SHORT).show()
                    is FirebaseAuthWeakPasswordException -> Toast.makeText(this,"${authUser.authException.message}", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this,"${authUser.authException.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}