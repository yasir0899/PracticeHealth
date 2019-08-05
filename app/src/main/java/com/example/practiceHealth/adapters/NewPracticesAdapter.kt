package com.example.practiceHealth.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.practiceHealth.R
import com.example.practiceHealth.interfaces.RecyclerViewItemClickListener
import com.example.practiceHealth.models.responseModels.NewPracticesResponseModel
import kotlinx.android.synthetic.main.new_practice_list.view.*

class NewPracticesAdapter(
        private val newPracticesList: ArrayList<NewPracticesResponseModel>,
        private val context: Context, val showProgressBar: Boolean = true
) :
        androidx.recyclerview.widget.RecyclerView.Adapter<NewPracticesAdapter.ViewHolder>() {

    private var listener: RecyclerViewItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.new_practice_list,
                        parent,
                        false
                )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.clNewPractice.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation)
        holder.image.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation)
        val obj = newPracticesList[position]
        holder.name.text = obj.practice
        val letter = newPracticesList[position].practice?.get(0)
        /*  val drawable = TextDrawable.builder()
              .buildRound("$letter", ContextCompat.getColor(context, R.color.colorAccent))*/
        /*Glide.with(context)
            .load(drawable)
            .into(holder.image)*/
        //holder.image.setImageDrawable(drawable)
        if (showProgressBar) {
            var progress = obj.remaining!!.split(".")
            var progressRemainingValue = 100 - progress[0].toInt()
            holder.pb.visibility = View.VISIBLE
            holder.pbText.visibility = View.VISIBLE
            holder.pb.progress = progressRemainingValue
            holder.pbText.text = "${obj.remaining}% Remaining"
        }




        holder.clNewPractice.setOnClickListener {

            if (listener != null) {
                listener?.onAdapterClickListener(position)
            }

        }
    }


    var clickedPosition: Int = -1


    fun deleteItem(pos: Int) {
        newPracticesList.removeAt(pos)
        newPracticesList.size

    }

    fun getItem(pos: Int): NewPracticesResponseModel {
        return newPracticesList[pos]
    }

    fun setOnAdapterClickListener(listener1: RecyclerViewItemClickListener) {
        this.listener = listener1
    }


    override fun getItemCount(): Int {
        return newPracticesList.size
    }


    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {


        var name: TextView = view.tvNewPracticeName
        var image: ImageView = view.ivNewPractice
        var pb: ProgressBar = view.pbNewPractices
        var pbText: TextView = view.tvPercentage
        var clNewPractice: ConstraintLayout = view.clNewPractice
        var view: View = view.viewNewPractice

    }


}