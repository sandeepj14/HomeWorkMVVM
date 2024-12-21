package com.imaginato.homeworkmvvm.ui.login

import androidx.lifecycle.Observer
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.data.remote.login.response.LoginModel
import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

/**
 * Created by sjadhav on 20-12-2024.
 */
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class LoginViewModelTest : KoinTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: LoginRepository = mockk()
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        startKoin {
            modules(
                module {
                    single { repository }
                }
            )
        }
        loginViewModel = LoginViewModel()
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun `login success updates LiveData`() = runTest {
        // Mock successful login response
        val loginResponse = LoginResponse(
            errorCode = "00",
            errorMessage = "Sukses.",
            data = LoginModel(userId = "83878737", userName = "zhangsan", isDeleted = true)
        )

        coEvery { repository.login(any()) } returns flowOf(loginResponse)

        val observer = mockk<Observer<String>>(relaxed = true)
        loginViewModel.resultLiveData.observeForever(observer)

        loginViewModel.login("testUser", "password123")

        coVerify { observer.onChanged("testUser logged in successfully.") }
        loginViewModel.resultLiveData.removeObserver(observer)
    }

    @Test
    fun login_success() = runTest {
        // Mock repository response
        val loginResponse = LoginResponse(
            errorCode = "00",
            errorMessage = "Sukses.",
            data = LoginModel(userId = "83878737", userName = "zhangsan", isDeleted = true)
        )
        coEvery { repository.login(any()) } returns flowOf(loginResponse)

        // Observe LiveData
        val progressObserver = mockk<Observer<Boolean>>(relaxed = true)
        val resultObserver = mockk<Observer<String>>(relaxed = true)
        loginViewModel.progress.observeForever(progressObserver)
        loginViewModel.resultLiveData.observeForever(resultObserver)

        // Call login
        loginViewModel.login("username","1111111")

        // Verify LiveData updates
        coVerifySequence {
            progressObserver.onChanged(true) // Show progress
            progressObserver.onChanged(false) // Hide progress
            resultObserver.onChanged("testUser logged in successfully.") // Success message
        }

        // Cleanup
        loginViewModel.progress.removeObserver(progressObserver)
        loginViewModel.resultLiveData.removeObserver(resultObserver)
    }

    @Test
    fun login_failure() = runTest {
        // Mock repository response
        val loginResponse = LoginResponse(
            errorCode = "01",
            errorMessage = "Invalid credentials",
            data = null
        )
        coEvery { repository.login(any()) } returns flowOf(loginResponse)

        // Observe LiveData
        val progressObserver = mockk<Observer<Boolean>>(relaxed = true)
        val resultObserver = mockk<Observer<String>>(relaxed = true)
        loginViewModel.progress.observeForever(progressObserver)
        loginViewModel.resultLiveData.observeForever(resultObserver)

        // Call login
        loginViewModel.login("wrongUser", "wrongPassword")

        // Verify LiveData updates
        coVerifySequence {
            progressObserver.onChanged(true) // Show progress
            progressObserver.onChanged(false) // Hide progress
            resultObserver.onChanged("Invalid credentials") // Error message
        }

        // Cleanup
        loginViewModel.progress.removeObserver(progressObserver)
        loginViewModel.resultLiveData.removeObserver(resultObserver)
    }
}