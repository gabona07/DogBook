package com.example.dogbook.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthUser(val uid: String?, val email: String?, val authException: Exception?) : Parcelable