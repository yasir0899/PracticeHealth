package com.example.practiceHealth.models.responseModels

import com.google.gson.annotations.SerializedName

data class PracticeDetailsResponseModel(

	@SerializedName("PracticeStageLevelId")
	val practiceStageLevelId: Int? = null,
	@SerializedName("notes")
	val notes: Any? = null,
	@SerializedName("ISComplete")
    var iSComplete: Int? = null,
	@SerializedName("LevelName")
	val levelName: String? = null,
	@SerializedName("StageLevelId")
	val stageLevelId: Int? = null,


	@SerializedName("path")
	var path:String?=null,
	@SerializedName("description")
	var description:String?=null,

	@SerializedName("list")
	var list:ArrayList<DescriptionModel>?=null
)
