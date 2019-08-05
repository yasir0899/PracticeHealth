package com.example.practiceHealth.models.requestModels

data class AddSubItemRequestModel(
	val createdById: Int? = null,
	val CreatedDate: String? = null,
	val Deleted: Boolean? = false,
	val LevelId: Int? = null,
	val Weightage: Int? = null,
	val SublevelName: String? = null
)
