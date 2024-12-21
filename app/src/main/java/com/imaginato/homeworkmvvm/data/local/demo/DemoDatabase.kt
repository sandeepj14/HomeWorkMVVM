package com.imaginato.homeworkmvvm.data.local.demo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.imaginato.homeworkmvvm.data.local.login.Login
import com.imaginato.homeworkmvvm.data.local.login.LoginDao
import com.imaginato.homeworkmvvm.data.remote.login.response.LoginModel

@Database(entities = [Demo::class, Login::class], version = 1, exportSchema = false)
abstract class DemoDatabase : RoomDatabase() {
    abstract val demoDao: DemoDao
    abstract val loginDao: LoginDao
}