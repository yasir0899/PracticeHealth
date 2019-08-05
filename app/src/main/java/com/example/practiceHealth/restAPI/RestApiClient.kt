package com.example.practiceHealth.restAPI



import com.example.practiceHealth.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestApiClient {
    companion object {

        private val retrofitWithHeaders: ApisList by lazy {
            val okHttpClientBuild = OkHttpClient().newBuilder()
            okHttpClientBuild.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor())
            //  okHttpClientBuild.addInterceptor(getHeaderInterceptor())
            val okHttpClient = okHttpClientBuild.build()

            Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build().create(ApisList::class.java)
        }
        private val retrofitWithOutHeaders: ApisList by lazy {
            val okHttpClientBuild = OkHttpClient().newBuilder()
            okHttpClientBuild.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor())
            val okHttpClient = okHttpClientBuild.build()

            Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL).client(okHttpClient).addConverterFactory(
                GsonConverterFactory.create()
            ).build().create(ApisList::class.java)
        }

        /* private fun getHeaderInterceptor(): Interceptor {
             return Interceptor { chain ->
                 val token = User.getStoredInstance().loginToken
                 Log.i("LoginToken","$token")
                 if (token != null) {
                     val request = chain.request().newBuilder().addHeader("loginToken", "$token").build()
                     chain.proceed(request)
                 } else {
                     chain.proceed(chain.request())
                 }
             }
         }*/

        private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor(HttpLogger())
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

        fun getClient(addHeaders: Boolean): ApisList {
            return if (addHeaders) {
                retrofitWithHeaders
            } else {
                retrofitWithOutHeaders
            }
        }
    }
}