package com.example.practiceHealth.utils

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.practiceHealth.R
import kotlinx.android.synthetic.main.dialog_layout.view.*
import kotlinx.android.synthetic.main.levels_details_dialog_layout.view.*

class AddSubItemDescriptionDialog : DialogFragment() {
    private var delegate: ItemAdded? = null
    private var args: Bundle? = null

    companion object {

        const val SUB_LEVEL_DATA = "subLevelData"
        const val LEVEL_ID = "levelID"
        const val  TOTAL_WEIGHT="totalWeight"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_layout, container, false)
        args = arguments ?: Bundle()

        view.etAddSubItemDescription.requestFocus()
        view.tvSaveSubItemDescription.setOnClickListener {
            if (isSubmitAble()) {
                val text = view.etAddSubItemDescription.text.toString()
                dismiss()
                 delegate?.onItemAdded(text)
            }
        }

        view.tvCancelSubItemDescription.setOnClickListener {
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
        fun onItemAdded(text: String)

        fun onCancel()
    }

    private fun isSubmitAble(): Boolean {
        var returnValue = true
        if (!validateText()) returnValue = false
        return returnValue
    }

    private fun validateText(): Boolean {
        return when {
            (view!!.etAddSubItemDescription.text.toString().trim { it <= ' ' }.isEmpty()) -> {
                view!!.inputLayoutAddSubItemDescription.error = getString(R.string.please_enter_sub_level_name)
                AppUtils.requestFocus(requireActivity(), view!!.etText)
                false
            }

            else -> {
                view!!.inputLayoutAddSubItemDescription.isErrorEnabled = false
                true
            }
        }
    }


}