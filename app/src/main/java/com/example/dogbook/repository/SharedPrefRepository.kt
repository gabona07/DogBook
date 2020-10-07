package com.example.dogbook.repository

import android.content.SharedPreferences

class SharedPrefRepository(private val sharedPref: SharedPreferences) {

    fun saveUserToken(token: String) {
        sharedPref.edit().putString("authToken", token).apply()
    }

    fun getUserToken(): String? {
        return sharedPref.getString("authToken", null)
    }
}