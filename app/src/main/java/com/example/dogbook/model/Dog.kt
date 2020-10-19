package com.example.dogbook.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Dog(var ownerUid: String = "", var dogUid: String = "", val dogName: String, val ownerName: String, val location: String, val dogPersonality: String, val description: String): Parcelable {

}