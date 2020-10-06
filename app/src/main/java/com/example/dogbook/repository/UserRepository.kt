package com.example.dogbook.repository

import androidx.lifecycle.MutableLiveData
import com.example.dogbook.model.User
import com.google.firebase.auth.FirebaseAuth

class UserRepository {

    private val userAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String): MutableLiveData<User>{
        val authUser = MutableLiveData<User>()

        userAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = userAuth.currentUser
                    currentUser?.let {
                        authUser.value = User(it.uid, it.email ?: "")
                    }
                } else {
                    authUser.value = User()
                }
            }
        return authUser
    }
}