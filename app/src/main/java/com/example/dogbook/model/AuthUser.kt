package com.example.dogbook.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class AuthUser{
    @Parcelize
    data class OnSuccess(val uid: String, val email: String) : AuthUser(), Parcelable

    data class OnError(val authException: Exception, val isLoginAttempt: Boolean) : AuthUser()
}