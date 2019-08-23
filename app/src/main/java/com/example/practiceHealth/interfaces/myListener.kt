package com.example.practiceHealth.interfaces

import android.view.View
import com.example.practiceHealth.adapters.LevelsAdapter

interface myListener {
    fun onAdapterPositionViewHolderListener(
        position: Int,
        holder: LevelsAdapter.ViewHolder,
        b: Boolean,
        fromFabButtom: Boolean,
        it: View
    )

    fun itemClicked(position: Int)}