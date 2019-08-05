package com.example.practiceHealth.utils

import android.content.Context
import androidx.multidex.MultiDexApplication


class AppController : MultiDexApplication() {


    companion object {
        lateinit var ApplicationContext: Context


    }

    override fun onCreate() {
        super.onCreate()
        //initialize on app level here
        ApplicationContext = this
        DataHandler()
    }
}