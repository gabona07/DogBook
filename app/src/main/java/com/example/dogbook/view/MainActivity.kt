package com.example.dogbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dogbook.R
import com.example.dogbook.model.AuthUser
import com.example.dogbook.transitionlistener.LoginTransitionListener
import com.example.dogbook.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModel()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth = FirebaseAuth.getInstance()

    companion object {
        const val RC_SIGN_IN = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginForm.setTransitionListener(LoginTransitionListener)
        userViewModel.getAuthUserData().observe(this, {
            validateAuthUser(it)
        })

//        setupGoogleSignInOptions()
//        googleSignIn.setOnClickListener { loginWithGoogle() }
        signInBtn.setOnClickListener { loginUser() }
//        registerButton.setOnClickListener { registerUser() }
    }

    private fun loginUser() {
        val userEmail = loginEmail.text.toString()
        val userPassword = loginPassword.text.toString()
        if (userEmail.isNotEmpty() && userPassword.isNotEmpty()) {
            loginEmail.clearFocus()
            loginPassword.clearFocus()
            loginForm.setTransition(R.id.start, R.id.LoginFormStateEnd)
            loginForm.transitionToEnd()
            userViewModel.loginUser(userEmail, userPassword)
        }
    }

    private fun registerUser() {
        val email = loginEmail.text.toString()
        val password = loginPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            userViewModel.registerUser(email, password)
        }
    }

    private fun validateAuthUser(user: AuthUser) {
        when(user.authException) {
            null -> println("Van User $user")
            is FirebaseAuthInvalidCredentialsException -> {
                loginEmailLayout.error = getString(R.string.error_failed_login)
                hideLoginLoading()
            }
            else -> println("${user.authException}")
        }
    }

    private fun hideLoginLoading() {
        loginForm.setTransition(R.id.LoginBtnStateEnd, R.id.start)
        loginForm.transitionToEnd()
    }

//    private fun setupGoogleSignInOptions() {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInApi
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)!!
//                Log.d("MainActivity", "firebaseAuthWithGoogle:" + account.id)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately
//                Log.w("MainActivity", "Google sign in failed", e)
//            }
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d("MainActivity", "signInWithCredential:success")
//                    val user = auth.currentUser
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w("MainActivity", "signInWithCredential:failure", task.exception)
//                }
//            }
//    }
//
//    private fun loginWithGoogle() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
}