package com.example.practiceHealth.restAPI

import android.content.Context
import android.util.Log
import com.example.practiceHealth.R
import com.example.practiceHealth.utils.HttpStatusCodes
import com.example.practiceHealth.utils.InternetConnectionUtil
import com.example.practiceHealth.utils.ToastUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class RetrofitApiManager<T>(private val mContext: Context) : Callback<T> {

    private val tag = this.javaClass.simpleName
    private var errorBody: String? = null
    private var jsonObj: JSONObject? = null

    init {
        Log.i("Tag", tag)
    }

    fun <A : Call<T>> callServer(t: A) {
        if (InternetConnectionUtil(mContext).isConnectedToInternet()) {
            if (!t.isExecuted) t.enqueue(this)
        } else {
            ToastUtil.showShortToast(mContext, mContext.getString(R.string.no_internet))
            responseFailure(ErrorDto(mContext.getString(R.string.no_internet), 0))
        }
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        //check for null response
        if (response?.isSuccessful!! && response.body() != null) {
            when {
                ((response.code() == HttpStatusCodes.SC_OK)) -> {
                    responseSuccess(response.body())
                }

                response.code() == HttpStatusCodes.SC_NO_CONTENT -> {
                    responseSuccess(response.body())
                }
                response.code() == HttpStatusCodes.SC_UNAUTHORIZED -> {

                    responseFailure(ErrorDto(response.message(), response.code()))
                }


                response.code() == HttpStatusCodes.SC_BAD_REQUEST -> {
                    /*responseFailure(ErrorDto(response.message(),HttpStatusCodes.SC_BAD_REQUEST))*/
                }
                /*else -> responseFailure(ErrorDto((response.body() as GenericResponseModel<*>).responseMessage ?: mContext.getString(R.string.error_occurred), (response.body() as GenericResponseModel<*>).status ?: response.code()))*/
            }
        } else if (response.code() == HttpStatusCodes.SC_UNAUTHORIZED) {
            errorBody = response.errorBody()?.string()
            jsonObj = JSONObject(errorBody)
            if (jsonObj?.has("statusCode")!!) {
                val statusCode = jsonObj?.getString("statusCode")!!.toInt()
                // DataHandler.updatePreferences(AppConstants.SP_EMAIL_VERIFY_STATUS_CODE,statusCode)
            }


            responseFailure(ErrorDto(response.message(), response.code()))
        } else {
            responseFailure(ErrorDto(response.message(), response.code()))
            /*responseFailure(ErrorDto(mContext.getString(R.string.error_occurred), response.code()))*/
        }
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        t?.printStackTrace()
        if (InternetConnectionUtil(mContext).isConnectedToInternet()) {
            responseFailure(ErrorDto(mContext.getString(R.string.error_occurred), 0))
        } else responseFailure(ErrorDto(mContext.getString(R.string.no_internet), 0))
    }


    abstract fun onSuccess(t: T?)

    abstract fun onFailure(t: ErrorDto?)

    abstract fun tokenRefreshed()

    private fun responseSuccess(t: T?) {
        onSuccess(t)
    }

    private fun responseFailure(t: ErrorDto?) {
        onFailure(t)
    }
}