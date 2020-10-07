package com.example.dogbook.repository

import androidx.lifecycle.MutableLiveData
import com.example.dogbook.model.AuthUser
import com.google.firebase.auth.FirebaseAuth

class UserRepository {

    private val userAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String): MutableLiveData<AuthUser>{
        val authUser = MutableLiveData<AuthUser>()

        userAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = userAuth.currentUser
                    currentUser?.let {
                        authUser.value = AuthUser(it.uid, it.email, null)
                    }
                } else {
                    authUser.value = AuthUser(null, null, task.exception)
                }
            }
        return authUser
    }
}