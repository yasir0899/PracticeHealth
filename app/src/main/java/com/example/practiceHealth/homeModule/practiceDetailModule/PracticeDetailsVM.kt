package com.example.practiceHealth.homeModule.practiceDetailModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.practiceHealth.models.requestModels.PracticeStageLevelRequestModel
import com.example.practiceHealth.models.responseModels.PracticeDetailsResponseModel

class PracticeDetailsVM : ViewModel() {
var practiceDetailsRepository=PracticeDetailsRepository()

    fun getPracticesDetails(stageId: String): LiveData<ArrayList<PracticeDetailsResponseModel>>? {

        return PracticeDetailsRepository().callApi(stageId)


    }

    fun updatePracticesDetails(practiceStageLevelRequestModel: PracticeStageLevelRequestModel): LiveData<String> {

        return PracticeDetailsRepository().callPracticeDetailUpdateApi(practiceStageLevelRequestModel) }
    }
