package com.example.practiceHealth.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.practiceHealth.R
import kotlinx.android.synthetic.main.levels_details_dialog_layout.*

class AddStageDialog(position: Int) : DialogFragment() {

   var position=position
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Do all the stuff to initialize your custom view

        return inflater.inflate(R.layout.levels_details_dialog_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var text = etText.text.toString()
        var weight = etWeight.text.toString()
        btnSave.setOnClickListener {
          /*  var list = adapterU.getItem(position).list
            list.add(
                LevelsDetailsItems(
                    text, weight.toInt()
                )
            )
            val item = LevelsItems(adapterU.getItem(position).levelName, list)



            levelItemsList[position] = item
            adapterU.notifyItemChanged(position)*/}}}