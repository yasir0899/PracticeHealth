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
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practiceHealth.R
import com.example.practiceHealth.interfaces.RecycleViewPracticeDetailsDescriptionCbClickListener
import com.example.practiceHealth.interfaces.RecyclerViewItemPositionViewHolderClickListener
import com.example.practiceHealth.models.responseModels.DescriptionModel
import com.example.practiceHealth.models.responseModels.PracticeDetailsResponseModel
import kotlinx.android.synthetic.main.practice_details_list.view.*


class PracticeDetailsAdapter(
    private val practiceItemsList: ArrayList<PracticeDetailsResponseModel>,
    private val context: Context
) :
    RecyclerView.Adapter<PracticeDetailsAdapter.ViewHolder>(),
    RecycleViewPracticeDetailsDescriptionCbClickListener {
    private lateinit var adapterU: PracticeDetailsDescriptionAdapter
    var clickedPosition: Int = -1
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
          //holder.clPracticeDetailsList.animation =
        AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation)
        val obj = practiceItemsList[position]
        holder.checkBoxText.text = obj.levelName
        adapterU = PracticeDetailsDescriptionAdapter(obj.list!!, context, position)
        holder.rcvPracticeDetailsDescription.adapter = adapterU
        adapterU.onAdapterClickListener(this)

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
               holder.isCheck.isChecked = true
           else holder.isCheck.isChecked = false*/

        holder.checkBoxText.setOnClickListener {
            listener?.onAdapterPositionViewHolderListener(
                position,
                holder,
                b = false,
                fromCheckBox = false,
                fromCheckBoxText = true,
                fromAddNote = false,
                fromAddAttach = false,
                fromShowAttachment = false
            )

            clickedPosition = position


        }
        holder.image.setOnClickListener {

            if (listener != null) {
                listener?.onAdapterPositionViewHolderListener(
                    position,
                    holder,
                    b = false,
                    fromCheckBox = false,
                    fromCheckBoxText = false,
                    fromAddNote = true,
                    fromAddAttach = false,
                    fromShowAttachment = false

                )
            }

        }

        holder.checkBox.setOnCheckedChangeListener { button: CompoundButton, b: Boolean ->
            if (listener != null) {

                if (button.isPressed) {
                    listener?.onAdapterPositionViewHolderListener(
                        position, holder, b, true,
                        fromCheckBoxText = false,
                        fromAddNote = false,
                        fromAddAttach = false,
                        fromShowAttachment = false
                    )
                    clickedPosition = position
                }

            }



            Log.i("is Checked :", "$b")

        }

        holder.attachment.setOnClickListener {
            listener?.onAdapterPositionViewHolderListener(
                position, holder,
                b = false,
                fromCheckBox = false,
                fromCheckBoxText = false,
                fromAddNote = false,
                fromAddAttach = true,
                fromShowAttachment = false
            )

        }
        holder.showAttachment.setOnClickListener {
            listener?.onAdapterPositionViewHolderListener(
                position, holder,
                b = false,
                fromCheckBox = false,
                fromCheckBoxText = false,
                fromAddNote = false,
                fromAddAttach = false,
                fromShowAttachment = true
            )

        }

        holder.clPracticeDetailsList.setOnClickListener {

            listener?.onAdapterPositionViewHolderListener(
                position, holder,
                b = false,
                fromCheckBox = false,
                fromCheckBoxText = false,
                fromAddNote = false,
                fromAddAttach = false,
                fromShowAttachment = false
            )

        }

    }


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


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var checkBox: CheckBox = view.cb1
        var checkBoxText: TextView = view.textView4
        var image: ImageView = view.ivAddNote
        var attachment: ImageView = view.ivAddAttachment
        var showAttachment: ImageView = view.ivShowAttachment
        var clPracticeDetailsList: ConstraintLayout = view.clPracticeDetailsList
        var rcvPracticeDetailsDescription: RecyclerView = view.rcvPracticeDetailsDescription


    }

    override fun onItemClicked(
        Position: Int,
        item: DescriptionModel,
        parentPosition: Int
    ) {
        practiceItemsList[parentPosition].list!![Position].isCheck = item.isCheck
        practiceItemsList[parentPosition].list!![Position].description = item.description
        notifyItemChanged(parentPosition)

        Log.e("practiceItemsList", "$practiceItemsList")
        Log.e("updatedItem", "${practiceItemsList[parentPosition]}")

        var ischecked = practiceItemsList[parentPosition].list!!.filter { it.isCheck }
        Log.e("ischecked", "$ischecked")

        if (ischecked.size == practiceItemsList[parentPosition].list!!.size) {

            practiceItemsList[parentPosition].iSComplete = 1
            notifyItemChanged(parentPosition)
        } else {
            practiceItemsList[parentPosition].iSComplete = 0
            notifyItemChanged(parentPosition)
        }
    }

}