package com.example.dogbook.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthUser(val uid: String?, val email: String?, val isNew: Boolean, val authException: Exception?) : Parcelable