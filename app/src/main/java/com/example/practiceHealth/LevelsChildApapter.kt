package com.example.practiceHealth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.levels_child_list.view.*

class LevelsChildApapter(private var list: ArrayList<SubLevelsDetailsItem>, private val context: Context) : RecyclerView.Adapter<LevelsChildApapter.ViewHolder>() {

    private var listener: RecyclerViewItemClickListener? = null
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

        val level = list[position]
        //holder.title.text = level.levelName

        holder.tvChild.text = level.sublevelName
        holder.tvWeight.text = level.weightage.toString()
    }


    var clickedPosition: Int = -1


    fun deleteItem(pos: Int) {
        list.removeAt(pos)
        list.size

    }

    fun getItem(pos: Int): SubLevelsDetailsItem {
        return list[pos]
    }

    fun setOnAdapterClickListener(listener1: RecyclerViewItemClickListener) {
        this.listener = listener1
    }


    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {


        var tvChild: TextView = view.tvChild
        var tvWeight: TextView = view.tvWeight
        var clChild: ConstraintLayout =view.clChild


    }

    fun addItem(item: SubLevelsDetailsItem) {
        list.add(item)
        notifyItemInserted(list.size)
    }

}