package com.example.practiceHealth.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.practiceHealth.R
import com.example.practiceHealth.interfaces.RecyclerViewItemPositionViewHolderClickListener
import com.example.practiceHealth.models.responseModels.PracticeDetailsResponseModel
import kotlinx.android.synthetic.main.practice_details_list.view.*


class PracticeDetailsAdapter(
    private val practiceItemsList: ArrayList<PracticeDetailsResponseModel>,
    private val context: Context
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<PracticeDetailsAdapter.ViewHolder>() {

    private var listener: RecyclerViewItemPositionViewHolderClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.practice_details_list,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.clPracticeDetailsList.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation)
        val obj = practiceItemsList[position]
        holder.checkBox.text = obj.levelName
        //true
        holder.checkBox.isChecked = obj.iSComplete == 1
        if (obj.notes == null) {

            Glide.with(context)
                .load(R.drawable.add_note)
                .into(holder.image)

        } else {
            Glide.with(context)
                .load(R.drawable.note_added)
                .into(holder.image)


        }
        /*   if ()
               holder.checkBox.isChecked = true
           else holder.checkBox.isChecked = false*/
        holder.image.setOnClickListener {

            if (listener != null) {
                listener?.onAdapterPostionViewHolderListner(position, holder, b = false, fromCheckBox = false)
            }

        }

        holder.checkBox.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            if (listener != null) {
                listener?.onAdapterPostionViewHolderListner(position,holder,b,true)
            }
            Log.i("is Checked :", "$b")

        }
    }

    var clickedPosition: Int = -1


    fun deleteItem(pos: Int) {
        practiceItemsList.removeAt(pos)
        practiceItemsList.size

    }

    fun getItem(pos: Int): PracticeDetailsResponseModel {
        return practiceItemsList[pos]
    }

    fun setOnAdapterClickListener(listener1: RecyclerViewItemPositionViewHolderClickListener) {
        this.listener = listener1
    }


    override fun getItemCount(): Int {
        return practiceItemsList.size
    }


    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {


        var checkBox: CheckBox = view.cb1
        var image: ImageView = view.ivAddNote
        var clPracticeDetailsList: ConstraintLayout = view.clPracticeDetailsList


    }


}