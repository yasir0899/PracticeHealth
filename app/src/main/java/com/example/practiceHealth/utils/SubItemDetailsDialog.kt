package com.example.practiceHealth.utils

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.practiceHealth.R
import com.example.practiceHealth.SubLevelsDetailsItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.sub_level_item_details_dialog_layout.view.*

class SubItemDetailsDialog : DialogFragment() {

    private var args: Bundle? = null

    companion object {

        const val SUB_LEVEL_DATA = "subLevelData"
        const val LEVEL_ID = "levelID"
        const val TOTAL_WEIGHT = "totalWeight"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.sub_level_item_details_dialog_layout, container, false)
        args = arguments ?: Bundle()
        var totalRemaining = args!!.getInt(TOTAL_WEIGHT)
        //  view.tvRemainingWeight.text="$totalRemaining% Remaining"
        var subLevelsDetailsItem = Gson().fromJson(args!!.getString(SUB_LEVEL_DATA), SubLevelsDetailsItem::class.java)
        var levelId = args!!.getInt(LEVEL_ID)
        if (subLevelsDetailsItem != null) {
            view.tvSubItemDetailTitle.text = subLevelsDetailsItem.sublevelName
            view.tvSubItemDetailWeight.text = subLevelsDetailsItem.weightage.toString()
        }

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        dialog?.window?.setLayout((width * 0.8).toInt(), (height * 0.4).toInt())
        return view
    }


}