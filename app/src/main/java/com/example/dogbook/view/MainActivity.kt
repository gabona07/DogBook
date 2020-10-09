package com.example.dogbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.dogbook.R
import com.example.dogbook.viewmodel.SharedPrefViewModel
import com.example.dogbook.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModel()
    private val sharedPrefViewModel: SharedPrefViewModel by viewModel()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth = FirebaseAuth.getInstance()
    companion object {
        val RC_SIGN_IN = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupGoogleSignInOptions()
        registerButton.setOnClickListener { registerUser() }
        googleSignIn.setOnClickListener { loginWithGoogle()  }
    }

    private fun setupGoogleSignInOptions() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun registerUser() {
        val email = email.text.toString()
        val password = password.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            userViewModel.registerUser(email, password).observe(this, { authUser ->
                when (authUser.authException) {
                    null -> sharedPrefViewModel.saveUserToken(authUser.userToken!!)
                    is FirebaseAuthUserCollisionException -> Toast.makeText(this,"${authUser.authException.message}", Toast.LENGTH_SHORT).show()
                    is FirebaseAuthInvalidCredentialsException -> Toast.makeText(this,"${authUser.authException.message}", Toast.LENGTH_SHORT).show()
                    is FirebaseAuthWeakPasswordException -> Toast.makeText(this,"${authUser.authException.message}", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this,"${authUser.authException.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("MainActivity", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("MainActivity", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("MainActivity", "signInWithCredential:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("MainActivity", "signInWithCredential:failure", task.exception)
                }
            }
    }


    fun loginWithGoogle() {

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
}