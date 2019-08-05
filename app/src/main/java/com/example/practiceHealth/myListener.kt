package com.example.practiceHealth

import android.view.View

interface myListener {
    fun onAdapterPostionViewHolderListner(
        position: Int,
        holder: LevelsAdapter.ViewHolder,
        b: Boolean,
        fromFabButtom: Boolean,
        it: View
    )

    fun itemClicked(position: Int)}