package com.example.practiceHealth

interface RecyclerViewItemClickListener {
    fun onAdapterClickListener(position: Int,fromShowAttachment:Boolean,statusIsCheck:Boolean,fromCheckBox:Boolean)
}