package com.example.practiceHealth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.practiceHealth.models.requestModels.AddSubItemRequestModel

class LevelsVM:ViewModel() {
    fun getLevels(): LiveData<ArrayList<LevelsDto>>? {

        return LevelsRespository().callApi()


    }

    fun addSubLevelItem(addSubItemRequestModel: AddSubItemRequestModel): LiveData<String>? {

        return LevelsRespository().addSubItemApi(addSubItemRequestModel)


    }
}
