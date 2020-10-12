package com.example.dogbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dogbook.R

class MainPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
    }

    fun navigateToForm(view: View) {
        val intent = Intent(this, FormActivity::class.java)
        startActivity(intent)
    }
}