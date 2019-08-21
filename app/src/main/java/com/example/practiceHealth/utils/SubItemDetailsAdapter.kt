package com.example.practiceHealth.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practiceHealth.R
import com.example.practiceHealth.RecyclerViewItemClickListener
import com.example.practiceHealth.myListener
import kotlinx.android.synthetic.main.sub_item_desc_list.view.*
import java.io.File

class SubItemDetailsAdapter(
    private val subItemDetailsList: ArrayList<SubItemDetails>,
    private val context: Context
) : RecyclerView.Adapter<SubItemDetailsAdapter.ViewHolder>() {
    private var listener: RecyclerViewItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.sub_item_desc_list,
                parent,
                false
            )
        )

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.clLevels.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation)
        val list = subItemDetailsList[position]
        holder.description.text = list.description
        holder.status.isChecked = list.status


    }


    var clickedPosition: Int = -1


    fun deleteItem(pos: Int) {
        subItemDetailsList.removeAt(pos)
        subItemDetailsList.size
        notifyDataSetChanged()
        notifyItemRemoved(pos)

    }

    fun getItem(pos: Int): SubItemDetails {
        return subItemDetailsList[pos]
    }

    fun setOnAdapterClickListener(listener1: RecyclerViewItemClickListener) {
        this.listener = listener1
    }


    override fun getItemCount(): Int {
        return subItemDetailsList.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var description: TextView = view.tvItemDescription
        var clLevels: ConstraintLayout = view.clSubItemDetails
        var attactment: ImageView = view.ivAttachment
        var showAttactment: ImageView = view.ivShowAttachment
        var status: CheckBox = view.cbStatus


        init {
            attactment.setOnClickListener {
                 listener?.onAdapterClickListener(adapterPosition,false)

            }


        }
        init {
            showAttactment.setOnClickListener {
                listener?.onAdapterClickListener(adapterPosition,true)

            }


        }
    }


}