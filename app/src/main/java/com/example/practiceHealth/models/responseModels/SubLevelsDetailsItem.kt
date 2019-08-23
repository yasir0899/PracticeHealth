package com.example.practiceHealth.models.responseModels

import com.google.gson.annotations.SerializedName

data class SubLevelsDetailsItem(
    @field:SerializedName("SubLevelId")
    val subLevelId: Int = 0,

    @field:SerializedName("LevelId")
    var levelId: Int = 0,

    @field:SerializedName("Weightage")
    var weightage: Int = 0,

    @field:SerializedName("SublevelName")
    var sublevelName: String = ""

)