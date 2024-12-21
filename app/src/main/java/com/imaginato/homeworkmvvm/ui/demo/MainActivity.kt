package com.imaginato.homeworkmvvm.ui.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.imaginato.homeworkmvvm.databinding.ActivityMainBinding
import com.imaginato.homeworkmvvm.ui.login.LoginScreen
import com.imaginato.homeworkmvvm.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.compat.ScopeCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainActivityViewModel>()
    private val loginViewModel by viewModel<LoginViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnDemo.setOnClickListener {
//            viewModel.getDemoData()
            setContent {
                LoginScreen(loginViewModel)
            }
        }
        initObserve()
    }

    private fun initObserve() {
        viewModel.resultLiveData.observe(this, Observer {
            binding.tvResult.text = it
        })
        viewModel.progress.observe(this, Observer {
            binding.pbLoading.isVisible = it
        })

        loginViewModel.resultLiveData.observe(this, Observer {
            Toast.makeText(
                this@MainActivity,
                it,
                Toast.LENGTH_LONG
            ).show()
        })
        loginViewModel.progress.observe(this, Observer {
            binding.pbLoading.isVisible = it
        })
    }
}