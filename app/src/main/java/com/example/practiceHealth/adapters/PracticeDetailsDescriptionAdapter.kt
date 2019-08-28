package com.example.practiceHealth.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import com.example.practiceHealth.R
import com.example.practiceHealth.interfaces.RecycleViewPracticeDetailsDescriptionCbClickListener
import com.example.practiceHealth.models.responseModels.DescriptionModel
import kotlinx.android.synthetic.main.prac_item_desc_list.view.*


class PracticeDetailsDescriptionAdapter(
    private val list: ArrayList<DescriptionModel>,
    private val context: Context,
    private var parentPosition: Int
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<PracticeDetailsDescriptionAdapter.ViewHolder>() {

    private var listener: RecycleViewPracticeDetailsDescriptionCbClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.prac_item_desc_list,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val obj = list[position]
        holder.desc.text = obj.description
        holder.checkBoxStatus.isChecked = obj.isCheck
        holder.checkBoxStatus.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->


            var descriptionModel = obj
            descriptionModel.description = obj.description
            descriptionModel.isCheck = b
            listener?.onItemClicked(position, descriptionModel,parentPosition)
        }
    }





    var clickedPosition: Int = -1


    fun deleteItem(pos: Int) {
        list.removeAt(pos)
        list.size

    }

    fun getItem(pos: Int): DescriptionModel {
        return list[pos]
    }

    fun onAdapterClickListener(listener1: RecycleViewPracticeDetailsDescriptionCbClickListener) {
        this.listener = listener1
    }


    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        var checkBoxStatus: CheckBox = view.cbStatus
        var desc: TextView = view.tvPracticeDetailsDescription


    }


}