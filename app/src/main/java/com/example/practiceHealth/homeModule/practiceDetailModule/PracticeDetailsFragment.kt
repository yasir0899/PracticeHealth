package com.example.practiceHealth.homeModule.practiceDetailModule


import android.app.Activity
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.practiceHealth.MainActivity
import com.example.practiceHealth.R

import com.example.practiceHealth.adapters.PracticeDetailsAdapter
import com.example.practiceHealth.interfaces.RecyclerViewItemPositionViewHolderClickListener
import com.example.practiceHealth.models.requestModels.PracticeStageLevelRequestModel
import com.example.practiceHealth.models.responseModels.NewPracticesResponseModel
import com.example.practiceHealth.models.responseModels.PracticeDetailsResponseModel
import com.example.practiceHealth.utils.ToastUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.details_dialog_layout.*
import kotlinx.android.synthetic.main.fragment_practice_details.*


class PracticeDetailsFragment : Fragment(), RecyclerViewItemPositionViewHolderClickListener {

    private var practiceStageId: String? = ""
    private val practiceDetailsVM: PracticeDetailsVM by lazy {
        ViewModelProviders.of(this).get(PracticeDetailsVM::class.java)
    }

    private lateinit var practiceDetailsItemsList: ArrayList<PracticeDetailsResponseModel>
    private lateinit var adapterU: PracticeDetailsAdapter
    private var args: Bundle? = null

    companion object {

        const val PRACTICE = "practice"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = ""
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as MainActivity).hideSignOutOption()
        args = arguments ?: Bundle()
        var newPracticeItems = Gson().fromJson(args!!.getString(PRACTICE), NewPracticesResponseModel::class.java)
        tvPracticeName.text = newPracticeItems.practice
        tvPracticeName.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation)
        practiceStageId = newPracticeItems.practiceStageId
        practiceDetailsItemsList = ArrayList()

        initPracticeDetailsItems(practiceStageId)
    }

    private fun initPracticeDetailsItems(practiceId: String?) {
        pbPracticeDetails.visibility = View.VISIBLE
        practiceDetailsVM.getPracticesDetails(practiceId!!)
            ?.observe(this, Observer<ArrayList<PracticeDetailsResponseModel>> {
                pbPracticeDetails.visibility = View.INVISIBLE

                if (it != null && it.size !== 0) {
                    practiceDetailsItemsList = it
                    adapterU = PracticeDetailsAdapter(practiceDetailsItemsList, requireContext())
                    rcvPracticeDetails.adapter = adapterU
                    adapterU.setOnAdapterClickListener(this)
                }

            })

    }

    /* override fun onAdapterClickListener(position: Int) {


         showDialog(position)

     }*/
    override fun onAdapterPositionViewHolderListener(
        position: Int,
        holder: PracticeDetailsAdapter.ViewHolder,
        b: Boolean,
        fromCheckBox: Boolean
    ) {
        var isCheck = if (b) {
            1
        } else {
            0
        }
        if (fromCheckBox) {

            val item =
                PracticeDetailsResponseModel(
                    adapterU.getItem(position).practiceStageLevelId,
                    adapterU.getItem(position).notes,
                    isCheck,
                    adapterU.getItem(position).levelName,
                    adapterU.getItem(position).stageLevelId
                )
            //ToastUtil.showShortToast(requireContext(), "$item")
            var obj = PracticeStageLevelRequestModel(
                0,
                item.notes.toString(),
                practiceStageId!!.toInt(),
                0,
                b,
                "",
                item.practiceStageLevelId,
                false,
                "",
                item.stageLevelId
            )
            val g = Gson().toJson(obj)
            Log.e("gson", "$g")
            practiceDetailsVM.updatePracticesDetails(obj).observe(this, Observer<String> {
                // initPracticeDetailsItems(practiceStageId)

            })


        } else {
            showDialog(requireActivity(), position, holder, b)
        }
    }


    private fun showDialog(
        activity: Activity, position: Int,
        holder: PracticeDetailsAdapter.ViewHolder,
        b: Boolean
    ) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.details_dialog_layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        if (adapterU.getItem(position).notes != null) {
            dialog.etAddNote?.setText("${adapterU.getItem(position).notes}")
        }
        val mDialogCancel = dialog.tvCancel
        mDialogCancel.setOnClickListener {

            dialog.dismiss()
        }
        val mDialogOk = dialog.tvSave
        mDialogOk.setOnClickListener {

            val note = dialog.etAddNote.text.toString()
            val item =
                PracticeDetailsResponseModel(
                    adapterU.getItem(position).stageLevelId,
                    note,
                    adapterU.getItem(position).iSComplete,
                    adapterU.getItem(position).levelName,
                    adapterU.getItem(position).stageLevelId
                )
            if (note.isEmpty()) {

                Glide.with(requireContext())
                    .load(R.drawable.add_note)
                    .into(holder.image)

            } else {
                Glide.with(requireContext())
                    .load(R.drawable.note_added)
                    .into(holder.image)


            }
            practiceDetailsItemsList[position] = item
            adapterU.notifyItemChanged(position)
            adapterU.notifyItemChanged(position)

            //Update api

            var isCheck: Boolean = item.iSComplete == 1
            var obj = PracticeStageLevelRequestModel(
                0,
                item.notes.toString(),
                practiceStageId!!.toInt(),
                0,
                isCheck,
                "",
                item.practiceStageLevelId,
                false,
                "",
                item.stageLevelId
            )
            val g = Gson().toJson(obj)
            Log.e("gson", "$g")
            practiceDetailsVM.updatePracticesDetails(obj).observe(this, Observer<String> {
                // initPracticeDetailsItems(practiceStageId)

            })

            dialog.cancel()
        }

        dialog.show()
    }
}
