package com.example.test


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : Fragment(), myListener {

    lateinit var levelDetailsList: ArrayList<LevelsDetailsItems>
    lateinit var test: ArrayList<LevelsDetailsItems>
    var isVisibility: Boolean = false
    private lateinit var levelItemsList: ArrayList<LevelsItems>
    private lateinit var adapterU: LevelsAdapter
    private var args: Bundle? = null

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        levelItemsList = ArrayList()
        test = ArrayList()
        initSettingsItems()


    }

    private fun initSettingsItems() {
        levelDetailsList = ArrayList()
        levelDetailsList.add(

            LevelsDetailsItems(
                "Credentialing", 23
            )
        )
        levelDetailsList.add(LevelsDetailsItems("EMR", 33))
        levelDetailsList.add(
            LevelsDetailsItems(
                "Credentialing", 23
            )
        )
        levelDetailsList.add(
            LevelsDetailsItems(
                "Credentialing",
                35
            )
        )
        levelDetailsList.add(LevelsDetailsItems("Follow Up", 30))
        levelItemsList.clear()
        levelItemsList.add(
            LevelsItems(
                "Level 1", levelDetailsList
            )
        )


        test.add(LevelsDetailsItems("Credentialing", 333))
        levelItemsList.add(
            LevelsItems(
                "Level 2", test
            )
        )

        levelItemsList.add(
            LevelsItems(
                "Level 3", levelDetailsList.toList() as ArrayList<LevelsDetailsItems>
            )
        )
        levelItemsList.add(
            LevelsItems(
                "Level 4", levelDetailsList.toList() as ArrayList<LevelsDetailsItems>
            )
        )

        levelItemsList.add(
            LevelsItems(
                "Level 5", levelDetailsList.toList() as ArrayList<LevelsDetailsItems>
            )
        )
        adapterU = LevelsAdapter(levelItemsList, requireContext(), requireActivity())
        rcvLevel.adapter = adapterU
        adapterU.setOnAdapterClickListener(this)

    }

    override fun onAdapterPostionViewHolderListner(
        position: Int,
        holder: LevelsAdapter.ViewHolder,
        b: Boolean,
        fromFabButtom: Boolean,
        it: View
    ) {

        Log.e("visible", "$position")
        if (fromFabButtom) {
            var list = adapterU.getItem(position).list
            list.add(LevelsDetailsItems("Last Stage", 40))
            list.add(LevelsDetailsItems("Enrollment", 10))
            val item = LevelsItems(adapterU.getItem(position).levelName, list)
            levelItemsList[position].levelName = item.levelName
            levelItemsList[position].list = item.list
            adapterU.notifyItemInserted(position)
            Log.e("child", "$item")
            Log.e("List", "$levelItemsList")


            // showDialog(position)

            if (position == 1) {

                var list = adapterU.getItem(position).list
                list.add(LevelsDetailsItems("Last Stage", 40))
                list.add(LevelsDetailsItems("Enrollment", 10))
                val item = LevelsItems(adapterU.getItem(position).levelName, list)
                test = list
                adapterU.notifyItemChanged(position)
            }


            /* val dialog= AddStageDialog(position)
             dialog.show(fragmentManager!!,"test")*/
        }

        Log.e("visible", "$position")
        if (!isVisibility) {
            isVisibility = true
            holder.rcv.visibility = View.VISIBLE
            holder.fabAddItem.visibility = View.VISIBLE
            Log.e("visible", "$isVisibility")


        } else {
            isVisibility = false
            holder.rcv.visibility = View.GONE
            holder.fabAddItem.visibility = View.GONE

            Log.e("visible", "$isVisibility")
        }
    }

    override fun itemClicked(position: Int) {
        /* val item = LevelsDetailsItems("abcd", 55)
         levelItemsList[position].list.add(item)
         val vh = rcvLevel.findViewHolderForAdapterPosition(position) as LevelsAdapter.ViewHolder
         vh.rcv.adapter?.notifyItemInserted(levelItemsList[position].list.size - 1)*/

        val levelDialog = LevelDialog()
        levelDialog.setCallBack(object : LevelDialog.ItemAdded {
            override fun onItemAdded(item: LevelsDetailsItems) {
                levelItemsList[position].list.add(item)
                val vh = rcvLevel.findViewHolderForAdapterPosition(position) as LevelsAdapter.ViewHolder
                vh.rcv.adapter?.notifyItemInserted(levelItemsList[position].list.size - 1)
                Log.e("levelItemsList", "$levelItemsList")
            }
        })
        levelDialog.show(requireFragmentManager(), "LevelDialog")
    }



}
