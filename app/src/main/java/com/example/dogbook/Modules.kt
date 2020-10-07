package com.example.dogbook

import com.example.dogbook.repository.UserRepository
import com.example.dogbook.viewmodel.UserViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single { UserRepository() }
    viewModel { UserViewModel(get()) }
}