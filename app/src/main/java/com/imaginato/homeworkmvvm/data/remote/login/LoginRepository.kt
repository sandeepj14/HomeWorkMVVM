package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.remote.login.request.LoginRequest
import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by sjadhav on 20-12-2024.
 */
interface LoginRepository {

    suspend fun login(loginRequest: LoginRequest): Flow<LoginResponse>
}