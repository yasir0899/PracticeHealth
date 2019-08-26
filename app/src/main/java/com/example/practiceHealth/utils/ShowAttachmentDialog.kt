package com.example.practiceHealth.utils

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.practiceHealth.R
import com.example.practiceHealth.SubItemDetails
import com.example.practiceHealth.adapters.SubItemDetailsAdapter
import com.example.practiceHealth.models.responseModels.PracticeDetailsResponseModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.show_attachment_dialog_layout.view.*
import java.io.File

class ShowAttachmentDialog : DialogFragment() {
    private var userImageRealPath: String = ""
    private var positionFor: Int = 0
    private var args: Bundle? = null
    private lateinit var list: ArrayList<SubItemDetails>
    private lateinit var adapterU: SubItemDetailsAdapter

    companion object {

        const val SUB_LEVEL_DATA = "subLevelData"

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.show_attachment_dialog_layout, container, false)
        args = arguments ?: Bundle()
        var subLevelsDetails = Gson().fromJson(args!!.getString(SUB_LEVEL_DATA), PracticeDetailsResponseModel::class.java)
        if (subLevelsDetails != null) {

            val file = File(subLevelsDetails.path)
            if (subLevelsDetails.path == "" || !file.exists()) {
                view.tvNoAttachment.visibility = View.VISIBLE
                view.ivShowAttachmentDialog.visibility = View.INVISIBLE
            } else {

                view.tvNoAttachment.visibility = View.INVISIBLE
                view.ivShowAttachmentDialog.visibility = View.VISIBLE
                Glide.with(requireContext())
                    .load(subLevelsDetails.path)
                    .into(view.ivShowAttachmentDialog)

            }
        }

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        dialog?.window?.setLayout((width * 0.8).toInt(), (height * 0.4).toInt())
        return view
    }
}


