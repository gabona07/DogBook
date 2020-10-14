package com.example.dogbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.dogbook.model.AuthUser
import com.example.dogbook.viewmodel.AuthViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel.getCurrentUserData().observe(this, {
            navigateToActivity(it)
        })
        Handler(Looper.getMainLooper()).postDelayed({
            authViewModel.checkForCurrentUser()
        }, 2000)
    }

    private fun navigateToActivity(currentUser: AuthUser?) {
        if (currentUser == null) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        } else {
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}