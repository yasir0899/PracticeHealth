package com.example.practiceHealth.restAPI


import com.example.practiceHealth.LevelsDto
import com.example.practiceHealth.models.requestModels.AddSubItemRequestModel
import com.example.practiceHealth.models.requestModels.PracticeStageLevelRequestModel
import com.example.practiceHealth.models.responseModels.NewPracticesResponseModel
import com.example.practiceHealth.models.responseModels.PracticeDetailsResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApisList {

    @GET("Values")  //1
    fun values(@Query("UserName") UserName: String?, @Query("PIN") PIN: String?): Call<String>


     @GET("PracticeStages")  //2
       fun practiceStages(@Query("stageId") stageId: String?): Call<ArrayList<NewPracticesResponseModel>>


       @GET("PracticeStages")  //3
       fun getPracticeDetails(@Query("selectedId") selectedId: String?): Call<ArrayList<PracticeDetailsResponseModel>>

       @POST("PracticeStages")  //4
       fun practiceStageLevel(@Body practiceStageLevelRequestModel: PracticeStageLevelRequestModel):  Call<String>

    @GET("PracticeStages")  //5
    fun getLevels(): Call<ArrayList<LevelsDto>>


    @POST("values")  //6
    fun addSubLevelItem(@Body addSubItemRequestModel: AddSubItemRequestModel): Call<String>
}