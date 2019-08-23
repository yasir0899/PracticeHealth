package com.example.practiceHealth.adapters

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practiceHealth.R
import com.example.practiceHealth.interfaces.RecycleViewChildItemClickListener
import com.example.practiceHealth.interfaces.myListener
import com.example.practiceHealth.models.responseModels.LevelsDto
import com.example.practiceHealth.models.responseModels.SubLevelsDetailsItem
import com.example.practiceHealth.utils.SubItemDetailsDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.levels_list.view.*

class LevelsAdapter(
    private val levelList: ArrayList<LevelsDto>,
    private val context: Context,
    private val requireActivity: Activity,
    private var fragmentManager: FragmentManager?
) : RecyclerView.Adapter<LevelsAdapter.ViewHolder>(),
    RecycleViewChildItemClickListener {
    private var listener: myListener? = null
    override fun onItemClicked(position: Int, subLevelsDetailsItem: SubLevelsDetailsItem) {
        var subItemDetailsDialog=SubItemDetailsDialog()
         var  args = Bundle()
        args?.putString(SubItemDetailsDialog.SUB_LEVEL_DATA, Gson().toJson(subLevelsDetailsItem))
        subItemDetailsDialog.arguments=args
        subItemDetailsDialog.show(fragmentManager!!,"subItemDetailsDialog")
    }


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
        holder.clLevels.animation = AnimationUtils.loadAnimation(context,
            R.anim.fade_transition_animation
        )
        val level = levelList[position]
        holder.title.text = level.levelName
        var adapterU =
            LevelsChildAdapter(level.subLevelsDetails, context)
        holder.rcv.adapter = adapterU
        adapterU.onItemClickListener(this)
        /* val resId = R.anim.layout_animation_fall_down
         val animation = AnimationUtils.loadLayoutAnimation(context, resId)
         holder.rcv.layoutAnimation = animation*/

        when (level.levelName) {
            "Level 1" -> {
                Glide.with(context)
                    .load(R.drawable.level_1)
                    .into(holder.ivLevel)
            }
            "Level 2" -> {
                Glide.with(context)
                    .load(R.drawable.level_2)
                    .into(holder.ivLevel)
            }
            "Level 3" -> {
                Glide.with(context)
                    .load(R.drawable.level_3)
                    .into(holder.ivLevel)
            }
            "Level 4" -> {
                Glide.with(context)
                    .load(R.drawable.level_4)
                    .into(holder.ivLevel)
            }
            "Level 5" -> {
                Glide.with(context)
                    .load(R.drawable.level_5)
                    .into(holder.ivLevel)
            }
        }
        holder.clLevels.setOnClickListener {
            if (listener != null) {
                listener?.onAdapterPositionViewHolderListener(position, holder, false, false, it)//fpr fab item
            }

        }

    }


    var clickedPosition: Int = -1


    fun deleteItem(pos: Int) {
        levelList.removeAt(pos)
        levelList.size
        notifyDataSetChanged()
        notifyItemRemoved(pos)

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


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.tvLevelName
        var clLevels: ConstraintLayout = view.clLevel
        var rcv: RecyclerView = view.rcvChild
        var fabAddItem: ImageView = view.fabAddItem
        var ivLevel: ImageView = view.ivLevel
        var expand: ImageView = view.ivExpand


        init {
            fabAddItem.setOnClickListener {
                listener?.itemClicked(adapterPosition)

            }


        }
    }


}