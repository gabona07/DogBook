package com.example.dogbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.dogbook.viewmodel.UserViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = userViewModel.getCurrentUser()
        Handler(Looper.getMainLooper()).postDelayed({
            if (currentUser == null) {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, MainPageActivity::class.java)
                startActivity(intent)
            }
        }, 2000)
    }
}