package com.example.practiceHealth.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceHealth.R
import com.example.practiceHealth.RecyclerViewItemClickListener
import kotlinx.android.synthetic.main.sub_item_desc_list.view.*

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
        holder.clLevels.animation =
            AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation)
        val list = subItemDetailsList[position]
        holder.description.text = list.description
        //  holder.status.isChecked = list.status

        holder.clLevels.setOnClickListener {

            listener?.onAdapterClickListener(
                position,
                fromShowAttachment = false,
                statusIsCheck = false,
                fromCheckBox = false
            )
        }

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


        /*   init {
               clLevels.setOnClickListener {
                     listener?.onAdapterClickListener(adapterPosition,false,false,false)

                }


            }*/

        /* init {
             showAttactment.setOnClickListener {
                 listener?.onAdapterClickListener(adapterPosition, true,false,false)

             }
         }
             init {
             status.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
                 if (listener != null) {
                     listener?.onAdapterClickListener(adapterPosition,false,true,true)
                 }
                 Log.i("is Checked :", "$b")

             }

         }*/
    }


}