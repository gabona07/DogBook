package com.example.dogbook.repository

import androidx.lifecycle.MutableLiveData
import com.example.dogbook.model.AuthUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val authUser = MutableLiveData<AuthUser>()

    fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { registerTask ->
                if (registerTask.isSuccessful) {
                    val currentUser = registerTask.result?.user
                    currentUser?.let {
                       authUser.value = AuthUser(it.uid, it.email,true, null)
                    }
                } else {
                    authUser.value = AuthUser(null, null, true, registerTask.exception)
                }
            }
    }

    fun loginUserWithEmailAndPassword(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { loginTask ->
                if (loginTask.isSuccessful) {
                    val currentUser = loginTask.result?.user
                    currentUser?.let {
                        authUser.value = AuthUser(it.uid, it.email, false, null)
                    }
                } else {
                    authUser.value = AuthUser(null, null, false, loginTask.exception)
                }
            }
    }

    fun loginWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { loginTask ->
                if (loginTask.isSuccessful) {
                    val currentUser = loginTask.result?.user
                    currentUser?.let {
                        authUser.value = AuthUser(it.uid, it.email, false, null)
                    }
                } else {
                    authUser.value = AuthUser(null, null, false, loginTask.exception)
                }
            }
    }

    fun getAuthUser(): MutableLiveData<AuthUser> {
        return this.authUser
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}