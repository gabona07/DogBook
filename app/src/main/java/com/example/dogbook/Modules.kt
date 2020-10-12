package com.example.dogbook

import android.content.Context
import com.example.dogbook.repository.UserRepository
import com.example.dogbook.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single { UserRepository(androidContext().getSharedPreferences("DogBookPrefs", Context.MODE_PRIVATE)) }

    viewModel { UserViewModel(get()) } }