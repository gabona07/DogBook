package com.example.dogbook

import com.example.dogbook.repository.UserRepository
import com.example.dogbook.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single { GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .apply {
            requestIdToken(androidContext().getString(R.string.default_web_client_id))
            requestEmail()
        }.build()
    }

    single { GoogleSignIn.getClient(androidContext(), get()) }

    single { UserRepository() }

    viewModel { UserViewModel(get()) }
}