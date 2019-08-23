package com.example.practiceHealth.interfaces

import com.example.practiceHealth.models.responseModels.SubLevelsDetailsItem

interface RecycleViewChildItemClickListener {
    fun onItemClicked(Position:Int,subLevelsDetailsItem: SubLevelsDetailsItem)
}