package com.example.dogbook.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var userId: String = "", var email: String = "") : Parcelable {}