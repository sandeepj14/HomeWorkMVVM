package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.local.login.Login
import com.imaginato.homeworkmvvm.data.local.login.LoginDao
import com.imaginato.homeworkmvvm.data.remote.login.request.LoginRequest
import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * Created by sjadhav on 20-12-2024.
 */
class LoginDataRepository constructor(
    private var api: LoginApi,
    private var loginDao: LoginDao
) : LoginRepository {

    override suspend fun login(loginRequest: LoginRequest): Flow<LoginResponse> = flow {
        val requestBody =
            loginRequest.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val response = api.login(requestBody).await()
        val loginModel = response.data
        loginModel.let {
            loginDao.insertLogin(
                Login(it?.userId ?: "", it?.userName ?: "", it?.isDeleted ?: false)
            )
        }
        emit(response)
    }.flowOn(Dispatchers.IO)
}