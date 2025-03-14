package com.example.practiceHealth.homeModule.newPracticeModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practiceHealth.models.responseModels.NewPracticesResponseModel
import com.example.practiceHealth.restAPI.ErrorDto
import com.example.practiceHealth.restAPI.RestApiClient
import com.example.practiceHealth.restAPI.RetrofitApiManager
import com.example.practiceHealth.utils.AppController
import org.json.JSONObject


class NewPracticesRepository {

    private var errorBody: String? = null
    private var jsonObj: JSONObject? = null


    val showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val dataReceived: MutableLiveData<String> = MutableLiveData()
    val errorOccurred: MutableLiveData<ErrorDto> = MutableLiveData()
    val data = MutableLiveData<ArrayList<NewPracticesResponseModel>>()
    /* fun loginIn(username: String, pin: String): LiveData<String> {


         RestApiClient.getClient(false).values(username, pin)
             .enqueue(object : Callback<String> {
                 override fun onResponse(
                     call: Call<String>,
                     response: Response<String>
                 ) {
                     if (response.isSuccessful && response.body() != null) {
                         if (response.code() === HttpStatusCodes.SC_OK) {
                             data.value = response.body()
                         }
                     } else if (response.code() === HttpStatusCodes.SC_FORBIDDEN) {
                         try {
                             errorBody = response.errorBody()?.string()
                             if (errorBody != null) {
                                 jsonObj = JSONObject(errorBody!!)
                                 if (jsonObj!!.has("status_code")) {
                                     val statusCode = Integer.parseInt(jsonObj!!.getString("status_code"))
                                     Log.e("StatusCode", "" + statusCode)
                                     data.value = errorBody
                                 }
                             }
                         } catch (e: IOException) {
                             e.printStackTrace()
                         } catch (e: JSONException) {
                             e.printStackTrace()
                         }

                     }
                 }

                 override fun onFailure(call: Call<String>, t: Throwable) {
                     t.printStackTrace()
                 }
             })
         return data
     }*/


    fun callApi(stageId: String): LiveData<ArrayList<NewPracticesResponseModel>> {
        showLoading.value = true
        object : RetrofitApiManager<ArrayList<NewPracticesResponseModel>>(AppController.ApplicationContext) {

            override fun onSuccess(t: ArrayList<NewPracticesResponseModel>?) {
                showLoading.value = false
                data.value = t

            }

            init {
                callServer(RestApiClient.getClient(addHeaders = false).practiceStages(stageId))
            }

            override fun onFailure(t: ErrorDto?) {
                showLoading.value = false
           data.value = null
            }


            override fun tokenRefreshed() {

            }
        }

        return data
    }


}
