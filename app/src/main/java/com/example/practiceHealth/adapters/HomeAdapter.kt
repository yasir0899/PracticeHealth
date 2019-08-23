package com.example.practiceHealth.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.practiceHealth.R
import com.example.practiceHealth.models.homeListModel.HomeItems
import com.example.practiceHealth.interfaces.RecyclerViewItemClickListener
import kotlinx.android.synthetic.main.home_list.view.*

class HomeAdapter(
    private val homeList: ArrayList<HomeItems>,
    private val context: Context
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var listener: RecyclerViewItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.home_list,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val home = homeList[position]
        holder.title.text = home.item
        Glide.with(context)
            .load(home.image)
            .into(holder.image)
        holder.clHome.setOnClickListener {

            if (listener != null) {
                listener?.onAdapterClickListener(position)
            }

        }
    }


    var clickedPosition: Int = -1


    fun deleteItem(pos: Int) {
        homeList.removeAt(pos)
        homeList.size

    }

    fun getItem(pos: Int): HomeItems {
        return homeList[pos]
    }

    fun setOnAdapterClickListener(listener1: RecyclerViewItemClickListener) {
        this.listener = listener1
    }


    override fun getItemCount(): Int {
        return homeList.size
    }


    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {


        var title: TextView = view.tvHomeTitle
        var image: ImageView = view.ivHome
        var clHome: ConstraintLayout = view.clHome


    }


}