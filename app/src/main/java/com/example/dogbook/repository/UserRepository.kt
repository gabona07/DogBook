package com.example.dogbook.repository

import androidx.lifecycle.MutableLiveData
import com.example.dogbook.model.AuthUser
import com.google.firebase.auth.FirebaseAuth

class UserRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val authUser = MutableLiveData<AuthUser>()

    fun registerUser(email: String, password: String) {
//        firebaseAuth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { authTask ->
//                if (authTask.isSuccessful) {
//                    val currentUser = authTask.result?.user
//                    currentUser?.let {
//                       authUser.value = it
//                    }
//                }
//            }
    }

    fun loginUserWithEmailAndPassword(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { loginTask ->
                if (loginTask.isSuccessful) {
                    val currentUser = loginTask.result?.user
                    val isNewUser = loginTask.result?.additionalUserInfo?.isNewUser
                    currentUser?.let {
                        authUser.value = AuthUser(it.uid, it.email, isNewUser, loginTask.exception)
                    }
                } else {
                    authUser.value = AuthUser(null, null, null, loginTask.exception)
                }
            }
    }

    fun getAuthUser(): MutableLiveData<AuthUser> {
        return this.authUser
    }

}