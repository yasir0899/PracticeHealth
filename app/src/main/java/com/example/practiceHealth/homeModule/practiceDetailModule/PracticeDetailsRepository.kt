package com.example.practiceHealth.homeModule.practiceDetailModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practiceHealth.models.requestModels.PracticeStageLevelRequestModel
import com.example.practiceHealth.models.responseModels.PracticeDetailsResponseModel
import com.example.practiceHealth.restAPI.ErrorDto
import com.example.practiceHealth.restAPI.RestApiClient
import com.example.practiceHealth.restAPI.RetrofitApiManager
import com.example.practiceHealth.utils.AppController
import org.json.JSONObject


class PracticeDetailsRepository {

    val data = MutableLiveData<ArrayList<PracticeDetailsResponseModel>>()
    val practiceDetailsUpdated = MutableLiveData<String>()

    fun callApi(stageId: String): LiveData<ArrayList<PracticeDetailsResponseModel>> {

        object : RetrofitApiManager<ArrayList<PracticeDetailsResponseModel>>(AppController.ApplicationContext) {

            override fun onSuccess(t: ArrayList<PracticeDetailsResponseModel>?) {

                data.value = t

            }

            init {
                callServer(RestApiClient.getClient(addHeaders = false).getPracticeDetails(stageId))
            }

            override fun onFailure(t: ErrorDto?) {

           data.value = null
            }


            override fun tokenRefreshed() {

            }
        }

        return data
    }

    fun callPracticeDetailUpdateApi(practiceStageLevelRequestModel: PracticeStageLevelRequestModel):LiveData<String> {
        object : RetrofitApiManager<String>(AppController.ApplicationContext) {

            override fun onSuccess(t: String?) {

                practiceDetailsUpdated.value = t

            }

            init {
                callServer(RestApiClient.getClient(addHeaders = false).practiceStageLevel(practiceStageLevelRequestModel))
            }

            override fun onFailure(t: ErrorDto?) {

                practiceDetailsUpdated.value = null
            }


            override fun tokenRefreshed() {

            }
        }

        return practiceDetailsUpdated
    }
}
