package com.example.practiceHealth.utils

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.practiceHealth.R
import com.example.practiceHealth.models.responseModels.SubLevelsDetailsItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.levels_details_dialog_layout.view.*

class LevelDialog : DialogFragment() {
    private var delegate: ItemAdded? = null
    private var args: Bundle? = null

    companion object {

        const val SUB_LEVEL_DATA = "subLevelData"
        const val LEVEL_ID = "levelID"
        const val  TOTAL_WEIGHT="totalWeight"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.levels_details_dialog_layout, container, false)
        args = arguments ?: Bundle()
        var totalRemaining=args!!.getInt(TOTAL_WEIGHT)
      //  view.tvRemainingWeight.text="$totalRemaining% Remaining"
        var subLevelsDetailsItem = Gson().fromJson(args!!.getString(SUB_LEVEL_DATA), SubLevelsDetailsItem::class.java)
        var levelId = args!!.getInt(LEVEL_ID)
        if (subLevelsDetailsItem != null) {
            view.etText.setText(subLevelsDetailsItem.sublevelName)
            view.etWeight.setText(subLevelsDetailsItem.weightage.toString())
        }
        view.etText.requestFocus()
        view.btnSave.setOnClickListener {
            if (isSubmitAble()) {
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

    private fun isSubmitAble(): Boolean {
        var returnValue = true
        if (!validateText()) returnValue = false
        if (!validateWeight()) returnValue = false
        return returnValue
    }

    private fun validateText(): Boolean {
        return when {
            (view!!.etText.text.toString().trim { it <= ' ' }.isEmpty()) -> {
                view!!.inputLayoutText.error = getString(R.string.please_enter_sub_level_name)
                AppUtils.requestFocus(requireActivity(), view!!.etText)
                false
            }

            else -> {
                view!!.inputLayoutText.isErrorEnabled = false
                true
            }
        }
    }

    private fun validateWeight(): Boolean {
        return when {
            (view!!.etWeight.text.toString().trim { it <= ' ' }.isEmpty()) -> {
                view!!.inputLayoutWeight.error = getString(R.string.please_enter_weightage)
                AppUtils.requestFocus(requireActivity(), view!!.etWeight)
                false
            }

            else -> {
                view!!.inputLayoutWeight.isErrorEnabled = false
                true
            }
        }
    }
}