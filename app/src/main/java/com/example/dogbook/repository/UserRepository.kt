package com.example.dogbook.repository

import androidx.lifecycle.MutableLiveData
import com.example.dogbook.model.AuthUser
import com.google.firebase.auth.FirebaseAuth

class UserRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String): MutableLiveData<AuthUser>{
        val authUser = MutableLiveData<AuthUser>()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    currentUser?.let {
                        it.getIdToken(true).addOnCompleteListener { tokenTask ->
                            if (tokenTask.isSuccessful) {
                                authUser.value = AuthUser(it.uid, it.email, null, tokenTask.result?.token)
                            }
                        }
                    }
                } else {
                    authUser.value = AuthUser(null, null, authTask.exception, null)
                }
            }
        return authUser
    }
}