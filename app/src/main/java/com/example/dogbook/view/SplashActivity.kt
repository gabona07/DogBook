package com.example.dogbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.dogbook.viewmodel.UserViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            val authToken = userViewModel.getAuthToken()
            if (authToken.isNullOrEmpty()) {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
        }, 2000)
    }
}