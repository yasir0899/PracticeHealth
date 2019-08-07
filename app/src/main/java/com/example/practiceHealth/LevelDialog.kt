package com.example.practiceHealth

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.levels_details_dialog_layout.view.*

class LevelDialog : DialogFragment() {
    private var delegate: ItemAdded? = null
    private var args: Bundle? = null

    companion object {

        const val SUB_LEVEL_DATA = "subLevelData"
        const val LEVEL_ID = "levelID"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.levels_details_dialog_layout, container, false)
        args = arguments ?: Bundle()
        var subLevelsDetailsItem = Gson().fromJson(args!!.getString(SUB_LEVEL_DATA), SubLevelsDetailsItem::class.java)
        var levelId = args!!.getInt(LEVEL_ID)
        if (subLevelsDetailsItem != null) {
            view.etText.setText(subLevelsDetailsItem.sublevelName)
            view.etWeight.setText(subLevelsDetailsItem.weightage.toString())
        }
        view.etText.requestFocus()
        view.btnSave.setOnClickListener {
            val text = view.etText.text.toString()
            val weight = view.etWeight.text.toString()
            var items = SubLevelsDetailsItem()
            if (subLevelsDetailsItem != null) items.levelId = subLevelsDetailsItem.levelId
            else items.levelId = levelId
            items.sublevelName = text
            items.weightage = Integer.parseInt(weight.trim())

            dismiss()
            if (subLevelsDetailsItem != null) delegate?.onItemUpdated(items)
            else delegate?.onItemAdded(items)
        }

        view.tvCancel.setOnClickListener {
            delegate?.onCancel()
            dismiss()
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
        fun onItemUpdated(item: SubLevelsDetailsItem)
        fun onCancel()
    }
}