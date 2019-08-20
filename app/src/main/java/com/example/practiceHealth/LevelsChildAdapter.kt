package com.example.practiceHealth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.levels_child_list.view.*



class LevelsChildAdapter(private var list: ArrayList<SubLevelsDetailsItem>, private val context: Context) : RecyclerView.Adapter<LevelsChildAdapter.ViewHolder>() {
    private var totalWeightage: Int=0
    var clickedPosition: Int = -1
    private var listener: RecycleViewChildItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.levels_child_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.clChild.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation)
        val level = list[position]
        //holder.title.text = level.levelName
        totalWeightage += level.weightage
        holder.tvChild.text = level.sublevelName
        holder.tvWeight.text = level.weightage.toString()
       // listener?.onItemClicked(totalWeightage)
        val intent = Intent("TotalWeight")
        //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
        intent.putExtra("totalWeightage", totalWeightage)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        holder.clChild.setOnClickListener {

            listener!!.onItemClicked(position,level)

        }
    }





    fun deleteItem(pos: Int) {
        list.removeAt(pos)
        list.size
       notifyDataSetChanged()
      notifyItemRemoved(pos)

    }



    fun getItem(pos: Int): SubLevelsDetailsItem {
        return list[pos]
    }

    fun onItemClickListener(listener: RecycleViewChildItemClickListener) {
        this.listener = listener
    }


    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var tvChild: TextView = view.tvChild
        var tvWeight: TextView = view.tvWeight
        var clChild: ConstraintLayout =view.clChild



    }

    fun addItem(item: SubLevelsDetailsItem) {
        list.add(item)
        notifyItemInserted(list.size)
    }

}