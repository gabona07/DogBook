package com.example.dogbook.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val authUser = MutableLiveData<FirebaseUser>()

    fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val currentUser = authTask.result?.user
                    currentUser?.let {
                       authUser.value = it
                    }
                }
            }
    }

    fun loginUserWithEmailAndPassword(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { loginTask ->
                if (loginTask.isSuccessful) {
                    val currentUser = loginTask.result?.user
                    currentUser?.let {
                        authUser.value = it
                    }
                }
            }
    }

    fun getAuthUser(): MutableLiveData<FirebaseUser> {
        return this.authUser
    }

}