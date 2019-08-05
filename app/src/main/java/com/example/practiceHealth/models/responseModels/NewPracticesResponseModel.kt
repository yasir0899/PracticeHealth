package com.example.practiceHealth.models.responseModels

import com.google.gson.annotations.SerializedName

data class NewPracticesResponseModel(
    @SerializedName("Practice")
    val practice: String? = null,

    @SerializedName("PracticeStageId")
    val practiceStageId: String? = null,

    @SerializedName("Remaining")
    val remaining: String? = null
)
