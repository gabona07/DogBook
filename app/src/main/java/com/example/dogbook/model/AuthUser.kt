package com.example.dogbook.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.lang.Exception

@Parcelize
data class AuthUser(val userId: String?, val email: String?, val authException: Exception?): Parcelable {
}