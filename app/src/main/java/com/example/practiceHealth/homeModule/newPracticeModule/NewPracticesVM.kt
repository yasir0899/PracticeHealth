package com.example.practiceHealth.homeModule.newPracticeModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.practiceHealth.models.responseModels.NewPracticesResponseModel

class NewPracticesVM : ViewModel() {
var newPracticesRepository=NewPracticesRepository()

    fun getNewPractices(stageId: String): LiveData<ArrayList<NewPracticesResponseModel>>? {

        return NewPracticesRepository().callApi(stageId)


    }
}