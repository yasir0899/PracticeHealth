package com.example.practiceHealth.interfaces

import com.example.practiceHealth.adapters.PracticeDetailsAdapter

interface RecyclerViewItemPositionViewHolderClickListener {

    fun onAdapterPostionViewHolderListner(
        position: Int,
        holder: PracticeDetailsAdapter.ViewHolder,
        b: Boolean,
        fromCheckBox: Boolean
    )
}