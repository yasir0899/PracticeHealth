package com.example.practiceHealth.interfaces

import com.example.practiceHealth.models.responseModels.DescriptionModel

interface RecycleViewPracticeDetailsDescriptionCbClickListener {
    fun onItemClicked(Position: Int, item: DescriptionModel)
}