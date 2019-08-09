package com.example.practiceHealth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practiceHealth.models.requestModels.AddSubItemRequestModel
import com.example.practiceHealth.restAPI.ErrorDto
import com.example.practiceHealth.restAPI.RestApiClient
import com.example.practiceHealth.restAPI.RetrofitApiManager
import com.example.practiceHealth.utils.AppController

class LevelsRepository {
    val data = MutableLiveData<ArrayList<LevelsDto>>()

    val data1 = MutableLiveData<String>()
    fun callApi(): LiveData<ArrayList<LevelsDto>> {
        object : RetrofitApiManager<ArrayList<LevelsDto>>(AppController.ApplicationContext) {

            override fun onSuccess(t: ArrayList<LevelsDto>?) {

                data.value = t

            }

            init {
                callServer(RestApiClient.getClient(addHeaders = false).getLevels())
            }

            override fun onFailure(t: ErrorDto?) {

                data.value = null
            }


            override fun tokenRefreshed() {

            }
        }

        return data
    }
    fun addSubItemApi(addSubItemRequestModel: AddSubItemRequestModel): LiveData<String> {
        object : RetrofitApiManager<String>(AppController.ApplicationContext) {

            override fun onSuccess(t: String?) {

                data1.value = t

            }

            init {
                callServer(RestApiClient.getClient(addHeaders = false).addSubLevelItem(addSubItemRequestModel))
            }

            override fun onFailure(t: ErrorDto?) {

                data1.value = null
            }


            override fun tokenRefreshed() {

            }
        }

        return data1
    }
}
