package com.example.practiceHealth.signInModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class SignInVM : ViewModel() {


    private val signInRepository = SignInRepository()


    fun loginIn(username: String, pin: String): LiveData<Any>? {

        return SignInRepository().callApi(username, pin)


    }
}