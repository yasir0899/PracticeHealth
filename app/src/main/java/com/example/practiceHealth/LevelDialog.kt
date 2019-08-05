package com.example.practiceHealth

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.levels_details_dialog_layout.view.*

class LevelDialog : DialogFragment() {
    private var delegate: ItemAdded? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.levels_details_dialog_layout, container, false)
        view.etText.requestFocus()
        view.btnSave.setOnClickListener {
            val text = view.etText.text.toString()
            val weight = view.etWeight.text.toString()
            var items = SubLevelsDetailsItem()
            items.levelId=1
            items.sublevelName=text
            items.weightage=Integer.parseInt(weight.trim())

            dismiss()
            delegate?.onItemAdded(items)
        }
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        dialog?.window?.setLayout((width * 0.8).toInt(), (height * 0.4).toInt())
        return view
    }

    fun setCallBack(itemAdded: ItemAdded) {
        delegate = itemAdded
    }

    interface ItemAdded {
        fun onItemAdded(item: SubLevelsDetailsItem)
    }
}