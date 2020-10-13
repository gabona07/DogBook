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
import com.example.dogbook.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_auth.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class AuthActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModel()
    private val googleSignInClient: GoogleSignInClient by inject()

    companion object {
        private const val TAB_INDEX_KEY = "TAB_INDEX_KEY"
        private const val RC_SIGN_IN = 101
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
        authViewModel.getAuthUserData().observe(this, {
            validateAuthUser(it)
        })
        signInBtn.setOnClickListener { loginUser() }
        googleSingInBtn.setOnClickListener { loginWithGoogle() }
        signUpBtn.setOnClickListener { registerUser() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAB_INDEX_KEY, formOptions.selectedTabPosition)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    authViewModel.loginWithGoogle(it.idToken)
                }
            } catch (e: ApiException) {
                // TODO Display google login error
                Log.d("AuthActivity", "onActivityResult: Google login failed: $e")
            }
        }

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
            authViewModel.loginUser(userEmail, userPassword)
        }
    }

    private fun loginWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
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
            authViewModel.registerUser(email, password)
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
}