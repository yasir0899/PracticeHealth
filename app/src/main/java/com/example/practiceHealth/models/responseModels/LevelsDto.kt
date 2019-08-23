package com.example.practiceHealth.models.responseModels

import com.google.gson.annotations.SerializedName

data class LevelsDto (

    @field:SerializedName("SubLevelsDetails")
    val subLevelsDetails: ArrayList<SubLevelsDetailsItem> = ArrayList(),

    @field:SerializedName("levelName")
    val levelName: String = "",

    @field:SerializedName("LevelId")
    val levelId: Int = 0

    )