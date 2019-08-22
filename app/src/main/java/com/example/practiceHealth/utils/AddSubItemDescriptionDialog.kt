package com.example.practiceHealth.utils

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.practiceHealth.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_layout.view.*
import kotlinx.android.synthetic.main.levels_details_dialog_layout.view.*

class AddSubItemDescriptionDialog : DialogFragment() {
    private var delegate: ItemAdded? = null
    private var args: Bundle? = null

    companion object {

        const val DESCRIPTION = "description"

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_layout, container, false)
        args = arguments ?: Bundle()
        var subItemDetails =
            Gson().fromJson(args!!.getString(DESCRIPTION), SubItemDetails::class.java)
        view.etAddSubItemDescription.requestFocus()
        if (subItemDetails != null) {

            view.etAddSubItemDescription.setText(subItemDetails.description)}
        view.tvSaveSubItemDescription.setOnClickListener {
            if (isSubmitAble()) {
                if (subItemDetails != null) {
                    delegate?.onUpdated(view.etAddSubItemDescription.text.toString())
                } else {
                    delegate?.onItemAdded(view.etAddSubItemDescription.text.toString())
                }





                dismiss()
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

        fun onUpdated(text: String)
    }

    private fun isSubmitAble(): Boolean {
        var returnValue = true
        if (!validateText()) returnValue = false
        return returnValue
    }

    private fun validateText(): Boolean {
        return when {
            (view!!.etAddSubItemDescription.text.toString().trim { it <= ' ' }.isEmpty()) -> {
                view!!.inputLayoutAddSubItemDescription.error =
                    getString(R.string.please_enter_sub_level_name)
                AppUtils.requestFocus(requireActivity(), view!!.etAddSubItemDescription)
                false
            }

            else -> {
                view!!.inputLayoutAddSubItemDescription.isErrorEnabled = false
                true
            }
        }
    }


}