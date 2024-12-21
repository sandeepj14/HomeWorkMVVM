package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by sjadhav on 20-12-2024.
 */
interface LoginApi {

    @Headers("Content-Type: application/json", "IMSI:357175048449937", "IMEI:510110406068589")
    @POST("login")
    fun login(@Body requestBody: RequestBody): Deferred<LoginResponse>
}