package com.example.practiceHealth


import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.practiceHealth.models.requestModels.AddSubItemRequestModel
import kotlinx.android.synthetic.main.fragment_setting.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.ItemTouchHelper

import android.graphics.*

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar




class SettingFragment : Fragment(), myListener {
    private val p = Paint()
    private val levelsVM: LevelsVM by lazy {
        ViewModelProviders.of(this).get(LevelsVM::class.java)
    }

    lateinit var levelDetailsList: ArrayList<LevelsDetailsItems>
    lateinit var test: ArrayList<LevelsDetailsItems>
    var isVisibility: Boolean = false
    private lateinit var levelItemsList: ArrayList<LevelsDto>
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
        (activity as MainActivity).supportActionBar?.title = ""
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as MainActivity).toolBarsCenterTitle(getString(R.string.settings))

        (activity as MainActivity).hideSignOutOption()
        enableSwipe()
        initSettingsItems()


    }

    private fun initSettingsItems() {
        /*levelDetailsList = ArrayList()
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
        adapterU.setOnAdapterClickListener(this)*/
        dotsProgressBar.start()
        levelsVM.getLevels()?.observe(this, Observer<ArrayList<LevelsDto>> {

            if (it != null) {
                dotsProgressBar.stop()
                dotsProgressBar.visibility = View.INVISIBLE
                levelItemsList = it
                adapterU = LevelsAdapter(levelItemsList, requireContext(), requireActivity())
                rcvLevel.adapter = adapterU
                adapterU.setOnAdapterClickListener(this)
            }


        })
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
            /* var list = adapterU.getItem(position).subLevelsDetails
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
 */

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
            override fun onItemAdded(item: SubLevelsDetailsItem) {
                levelItemsList[position].subLevelsDetails.add(item)
                val vh = rcvLevel.findViewHolderForAdapterPosition(position) as LevelsAdapter.ViewHolder
                vh.rcv.adapter?.notifyItemInserted(levelItemsList[position].subLevelsDetails.size - 1)
                Log.e("levelItemsList", "$levelItemsList")
                val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                val date = df.format(Calendar.getInstance().time)
                var addSubItemRequestModel = AddSubItemRequestModel(
                    6,
                    date,
                    false,
                    levelItemsList[position].levelId,
                    item.weightage,
                    item.sublevelName
                )
                levelsVM.addSubLevelItem(addSubItemRequestModel)

            }
        })
        levelDialog.show(requireFragmentManager(), "LevelDialog")
    }

    private fun View.animateVisibility(setVisible: Boolean) {
        if (setVisible) expand(this) else collapse(this)
    }

    private fun expand(view: View) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val initialHeight = 0
        val targetHeight = view.measuredHeight

        // Older versions of Android (pre API 21) cancel animations for views with a height of 0.
        //v.getLayoutParams().height = 1;
        view.layoutParams.height = 0
        view.visibility = View.VISIBLE

        animateView(view, initialHeight, targetHeight)
    }

    private fun collapse(view: View) {
        val initialHeight = view.measuredHeight
        val targetHeight = 0

        animateView(view, initialHeight, targetHeight)
    }

    private fun animateView(v: View, initialHeight: Int, targetHeight: Int) {
        val valueAnimator = ValueAnimator.ofInt(initialHeight, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                v.layoutParams.height = targetHeight
            }

            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        valueAnimator.duration = 700
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.start()
    }
    private fun enableSwipe() {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition

                    if (direction == ItemTouchHelper.LEFT) {
                        val deletedModel = levelItemsList.get(position)
                        adapterU.deleteItem(position)
                        // showing snack bar with Undo option
                        val snackbar = Snackbar.make(
                            activity!!.window.getDecorView().getRootView(),
                            " removed from Recyclerview!",
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.setAction("UNDO") {
                            // undo is selected, restore the deleted item
                         //   adapterU.restoreItem(deletedModel, position)
                        }
                        snackbar.setActionTextColor(Color.YELLOW)
                        snackbar.show()
                    } else {
                        val deletedModel =  levelItemsList.get(position)
                        adapterU.deleteItem(position)
                        // showing snack bar with Undo option
                        val snackbar = Snackbar.make(
                            activity!!. getWindow().getDecorView().getRootView(),
                            " removed from Recyclerview!",
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.setAction("UNDO") {
                            // undo is selected, restore the deleted item
                           // adapter.restoreItem(deletedModel, position)
                        }
                        snackbar.setActionTextColor(Color.YELLOW)
                        snackbar.show()
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    val icon: Bitmap
                    val res = context!!.resources
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                        val itemView = viewHolder.itemView
                        val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                        val width = height / 3

                        if (dX > 0) {
                            p.setColor(Color.parseColor("#388E3C"))
                            val background =
                                RectF(itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat())
                            c.drawRect(background, p)

                            icon = BitmapFactory.decodeResource(res, R.drawable.arrow_up)
                            val icon_dest = RectF(
                                itemView.left.toFloat() + width,
                                itemView.top.toFloat() + width,
                                itemView.left.toFloat() + 2 * width,
                                itemView.bottom.toFloat() - width
                            )
                            c.drawBitmap(icon, null, icon_dest, p)
                        } else {
                            p.setColor(Color.parseColor("#D32F2F"))
                            val background = RectF(
                                itemView.right.toFloat() + dX,
                                itemView.top.toFloat(),
                                itemView.right.toFloat(),
                                itemView.bottom.toFloat()
                            )
                            c.drawRect(background, p)

                            icon = BitmapFactory.decodeResource(res, R.drawable.arrow_down)
                            val icon_dest = RectF(
                                itemView.right.toFloat() - 2 * width,
                                itemView.top.toFloat() + width,
                                itemView.right.toFloat() - width,
                                itemView.bottom.toFloat() - width
                            )
                            c.drawBitmap(icon, null, icon_dest, p)
                        }
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rcvLevel)
    }
}
