package com.example.practiceHealth.interfaces

import com.example.practiceHealth.adapters.PracticeDetailsAdapter

interface RecyclerViewItemPositionViewHolderClickListener {

    fun onAdapterPositionViewHolderListener(
        position: Int,
        holder: PracticeDetailsAdapter.ViewHolder,
        b: Boolean,
        fromCheckBox: Boolean,
        fromCheckBoxText: Boolean,
        fromAddNote: Boolean,
        fromAddAttach: Boolean,
        fromShowAttachment: Boolean
    )
}