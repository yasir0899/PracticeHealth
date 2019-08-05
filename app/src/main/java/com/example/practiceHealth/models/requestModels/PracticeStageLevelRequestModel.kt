package com.example.practiceHealth.models.requestModels

data class PracticeStageLevelRequestModel(
	var ModifiedById: Int? = null,
	var notes: String? = null,
	var PracticeStageId: Int? = null,
	var CreatedById: Int? = null,
	var IsCompleted: Boolean? = null,
	var CreatedDate: String? = null,
	var PracticeStageLevelId: Int? = null,
	var Deleted: Boolean? = null,
	var ModifiedDate: String? = null,
	var StageLevelId: Int? = null
)
