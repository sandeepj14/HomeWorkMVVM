package com.imaginato.homeworkmvvm.data.local.login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by sjadhav on 20-12-2024.
 */
@Entity(tableName = "Login")
data class Login constructor(
    @PrimaryKey
    val userId: String,
    @ColumnInfo(name = "username")
    var userName: String,
    @SerializedName("isDeleted")
    val isDeleted: Boolean
)
