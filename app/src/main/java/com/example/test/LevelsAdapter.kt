package com.example.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.levels_list.view.*

class LevelsAdapter(
    val levelList: ArrayList<LevelsItems>,
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

        val level = levelList[position]
        holder.title.text = level.levelName
        holder.rcv.adapter = LevelsChildApapter(level.list, context)
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

    fun getItem(pos: Int): LevelsItems {
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
        var fabAddItem: Button = view.fabAddItem
        var expand: ImageView = view.ivExpand
        var etAddText: TextInputEditText = view.etAddText
        var etAddWeight: TextInputEditText = view.etAddWeight
        var layoutAddText: TextInputLayout = view.inputLayoutAddText
        var layoutAddWeight: TextInputLayout = view.inputLayoutAddWeight
        var btn: Button = view.btnAdd

        init {
            fabAddItem.setOnClickListener {
                listener?.itemClicked(adapterPosition)

            }


        }
    }
}