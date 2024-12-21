package com.imaginato.homeworkmvvm.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imaginato.homeworkmvvm.data.local.login.Login
import com.imaginato.homeworkmvvm.data.local.login.LoginDao
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.data.remote.login.request.LoginRequest
import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import com.imaginato.homeworkmvvm.ui.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject

/**
 * Created by sjadhav on 20-12-2024.
 */
@KoinApiExtension
class LoginViewModel : BaseViewModel() {

    private val repository: LoginRepository by inject()
    private var _resultLiveData: MutableLiveData<String> = MutableLiveData()
    private var _progress: MutableLiveData<Boolean> = MutableLiveData()
    val progress: LiveData<Boolean>
        get() {
            return _progress
        }

    val resultLiveData: LiveData<String>
        get() {
            return _resultLiveData
        }

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            repository.login(LoginRequest(userName = userName, password = password))
                .onStart {
                    _progress.value = true
                }.catch {
                    _progress.value = false
                }
                .onCompletion {
                }.collect { loginResponse ->
                    if (loginResponse.errorCode == "00") {
                        _progress.value = false
                        _resultLiveData.value =
                            "${loginResponse.data?.userName} logged in successfully."
                    } else {
                        _progress.value = false
                        _resultLiveData.value = loginResponse.errorMessage ?: "Error"
                    }
                }
        }
    }
}