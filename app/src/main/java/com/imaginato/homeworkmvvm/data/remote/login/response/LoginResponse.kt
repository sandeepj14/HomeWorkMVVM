package com.imaginato.homeworkmvvm.data.remote.login.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LoginResponse(
    @SerializedName("errorCode")
    val errorCode: String? = null,
    @SerializedName("errorMessage")
    val errorMessage: String? = null,
    @SerializedName("data")
    val `data`: LoginModel? = null
) : Parcelable