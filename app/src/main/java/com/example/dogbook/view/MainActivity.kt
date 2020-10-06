package com.example.dogbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dogbook.R
import com.example.dogbook.viewmodel.UserViewModel
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
                userViewModel.registerUser(email, password).observe(this, Observer{
                    if (it.userId.isEmpty()) {
                        println("Nem oké, nulla")
                        //TODO error message/red
                    } else {
                        println(it)
                    }
                })
            }
        }
    }
}