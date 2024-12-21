package com.imaginato.homeworkmvvm.data.remote.login.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LoginModel(
    @SerializedName("userId")
    val userId: String? = null,
    @SerializedName("userName")
    val userName: String? = null,
    @SerializedName("isDeleted")
    val isDeleted: Boolean? = null
) : Parcelable