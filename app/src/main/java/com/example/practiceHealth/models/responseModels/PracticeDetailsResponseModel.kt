package com.example.practiceHealth.models.responseModels

import com.google.gson.annotations.SerializedName

data class PracticeDetailsResponseModel(

	@SerializedName("PracticeStageLevelId")
	val practiceStageLevelId: Int? = null,
	@SerializedName("notes")
	val notes: Any? = null,
	@SerializedName("ISComplete")
	val iSComplete: Int? = null,
	@SerializedName("LevelName")
	val levelName: String? = null,
	@SerializedName("StageLevelId")
	val stageLevelId: Int? = null
)
