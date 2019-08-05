package com.example.practiceHealth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.levels_list.view.*

class LevelsAdapter(
    val levelList: ArrayList<LevelsDto>,
    private val context: Context,
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<LevelsAdapter.ViewHolder>() {
    private var listener: myListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.levels_list,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.clLevels.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation)
        val level = levelList[position]
        holder.title.text = level.levelName
        holder.rcv.adapter = LevelsChildApapter(level.subLevelsDetails, context)
        holder.clLevels.setOnClickListener {
            if (listener != null) {
                listener?.onAdapterPostionViewHolderListner(position, holder, false, false, it)//fpr fab item
            }

        }

    }


    var clickedPosition: Int = -1


    fun deleteItem(pos: Int) {
        levelList.removeAt(pos)
        levelList.size

    }

    fun getItem(pos: Int): LevelsDto {
        return levelList[pos]
    }

    fun setOnAdapterClickListener(listener1: myListener) {
        this.listener = listener1
    }


    override fun getItemCount(): Int {
        return levelList.size
    }


    inner class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        var title: TextView = view.tvLevelName
        var clLevels: ConstraintLayout = view.clLevel
        var rcv: RecyclerView = view.rcvChild
        var fabAddItem: ImageView = view.fabAddItem
        var expand: ImageView = view.ivExpand


        init {
            fabAddItem.setOnClickListener {
                listener?.itemClicked(adapterPosition)

            }


        }
    }
}