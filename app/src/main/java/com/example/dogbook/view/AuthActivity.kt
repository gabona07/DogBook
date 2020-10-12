package com.example.dogbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.dogbook.R
import com.example.dogbook.model.AuthUser
import com.example.dogbook.transitionlistener.LoginTransitionListener
import com.example.dogbook.transitionlistener.RegisterTransitionListener
import com.example.dogbook.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_auth.*
import org.koin.android.viewmodel.ext.android.viewModel

class AuthActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModel()
    private lateinit var googleSignInClient: GoogleSignInClient
//    private val auth = FirebaseAuth.getInstance()

    companion object {
        private const val TAB_INDEX_KEY = "TAB_INDEX_KEY"
        const val RC_SIGN_IN = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        addFormOptionListener()
        savedInstanceState?.let {
            val currentTabPosition = it.getInt(TAB_INDEX_KEY)
            formOptions.selectTab(formOptions.getTabAt(currentTabPosition))
        }
        loginForm.setTransitionListener(LoginTransitionListener)
        registerForm.setTransitionListener(RegisterTransitionListener)
        userViewModel.getAuthUserData().observe(this, {
            validateAuthUser(it)
        })
//        setupGoogleSignInOptions()
//        googleSingInBtn.setOnClickListener { loginWithGoogle() }
        signInBtn.setOnClickListener { loginUser() }
        signUpBtn.setOnClickListener { registerUser() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAB_INDEX_KEY, formOptions.selectedTabPosition)
    }

    private fun addFormOptionListener() {
        formOptions.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.text) {
                    getString(R.string.option_sign_in) -> loginForm.visibility = View.VISIBLE
                    getString(R.string.option_sign_up) -> registerForm.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when(tab?.text) {
                    getString(R.string.option_sign_in) -> loginForm.visibility = View.GONE
                    getString(R.string.option_sign_up) -> registerForm.visibility = View.GONE
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    private fun loginUser() {
        val userEmail = loginEmail.text.toString()
        val userPassword = loginPassword.text.toString()
        loginEmailLayout.isErrorEnabled = false
        if (userEmail.isNotEmpty() && userPassword.isNotEmpty()) {
            loginEmail.clearFocus()
            loginPassword.clearFocus()
            loginForm.transitionToState(R.id.loginFormStateEnd)
            userViewModel.loginUser(userEmail, userPassword)
        }
    }

    private fun registerUser() {
        val email = registerEmail.text.toString()
        val password = registerPassword.text.toString()
        val confirmPassword = registerPasswordConfirm.text.toString()
        registerEmailLayout.isErrorEnabled = false
        registerPasswordLayout.isErrorEnabled = false
        registerPasswordConfirmLayout.isErrorEnabled = false
        if (password != confirmPassword && email.isNotEmpty()) {
            registerEmail.clearFocus()
            registerPassword.clearFocus()
            registerPasswordConfirm.clearFocus()
            registerPasswordLayout.error = getString(R.string.error_password_mismatch)
            registerPasswordConfirmLayout.error = " "
            if (registerPasswordConfirmLayout.childCount == 2) {
                registerPasswordConfirmLayout.getChildAt(1).visibility = View.GONE
            }
        } else if (email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
            registerEmail.clearFocus()
            registerPassword.clearFocus()
            registerPasswordConfirm.clearFocus()
            registerForm.transitionToState(R.id.registerFormStateEnd)
            userViewModel.registerUser(email, password)
        }
    }

    private fun validateAuthUser(user: AuthUser) {
        when(user.authException) {
            null -> {
                val mainPageIntent = Intent(this, MainPageActivity::class.java)
                startActivity(mainPageIntent)
                finish()
            }
            is FirebaseAuthUserCollisionException -> {
                registerEmailLayout.error = getString(R.string.error_email_in_use)
                hideRegisterLoading()
            }
            is FirebaseAuthInvalidCredentialsException -> {
                if (user.isNew) {
                    registerEmailLayout.error = getString(R.string.error_invalid_email)
                    hideRegisterLoading()
                }
                else {
                    loginEmailLayout.error = getString(R.string.error_failed_login)
                    hideLoginLoading()
                }
            }
            is FirebaseAuthWeakPasswordException -> {
                registerPasswordLayout.error = getString(R.string.error_password_too_weak)
                registerPasswordConfirmLayout.error = " "
                if (registerPasswordConfirmLayout.childCount == 2) {
                    registerPasswordConfirmLayout.getChildAt(1).visibility = View.GONE
                }
                hideRegisterLoading()
            }
            else -> Log.d("AuthActivity", "validateAuthUser: ${user.authException}")
        }
    }

    private fun hideLoginLoading() {
        loginForm.setTransition(R.id.loginBtnStateEnd, R.id.start)
        loginForm.transitionToEnd()
    }

    private fun hideRegisterLoading() {
        registerForm.setTransition(R.id.registerBtnStateEnd, R.id.start)
        registerForm.transitionToEnd()
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
//                    auth.currentUser
//                    val mainPageIntent = Intent(this, MainPageActivity::class.java)
//                    startActivity(mainPageIntent)
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