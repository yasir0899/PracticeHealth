package com.example.practiceHealth.settingsModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.practiceHealth.models.responseModels.LevelsDto
import com.example.practiceHealth.models.requestModels.AddSubItemRequestModel

class LevelsVM:ViewModel() {
    fun getLevels(): LiveData<ArrayList<LevelsDto>>? {

        return LevelsRepository().callApi()


    }

    fun addSubLevelItem(addSubItemRequestModel: AddSubItemRequestModel): LiveData<String>? {

        return LevelsRepository().addSubItemApi(addSubItemRequestModel)


    }
}
