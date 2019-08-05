package com.example.practiceHealth.restAPI

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger: HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.i("RAW_RESPONSE", message)
    }
}