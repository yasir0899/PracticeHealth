package com.example.practiceHealth


import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceHealth.models.requestModels.AddSubItemRequestModel
import com.example.practiceHealth.utils.ToastUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_setting.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SettingFragment : Fragment(), myListener {
    private val p = Paint()
    var adapterPosition = 0
    private var args: Bundle? = null

    private val levelsVM: LevelsVM by lazy {
        ViewModelProviders.of(this).get(LevelsVM::class.java)
    }

    lateinit var test: ArrayList<LevelsDetailsItems>
    var isVisibility: Boolean = false
    private lateinit var levelItemsList: ArrayList<LevelsDto>
    private lateinit var adapterU: LevelsAdapter


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
        args = arguments ?: Bundle()
        levelItemsList = ArrayList()
        test = ArrayList()
        (activity as MainActivity).supportActionBar?.title = ""
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as MainActivity).toolBarsCenterTitle(getString(R.string.settings))
        (activity as MainActivity).hideSignOutOption()
        initSettingsItems()


    }

    private fun initSettingsItems() {
      //  dotsProgressBar.start()
       pbLevels.visibility=View.VISIBLE
        levelsVM.getLevels()?.observe(this, Observer<ArrayList<LevelsDto>> {

            if (it != null) {
                //dotsProgressBar.stop()
                dotsProgressBar.visibility = View.INVISIBLE
                pbLevels.visibility = View.INVISIBLE
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

        enableSwipe(position, holder)
        adapterPosition = position
        Log.e("Level ID", "${adapterU.getItem(position).levelId}")


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
        val levelDialog = LevelDialog()
        args = Bundle()
        args!!.putInt(LevelDialog.LEVEL_ID, levelItemsList[position].levelId)
        levelDialog.arguments = args
        levelDialog.setCallBack(object : LevelDialog.ItemAdded {
            override fun onCancel() {

            }

            override fun onItemUpdated(item: SubLevelsDetailsItem) {
            }

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

    private fun enableSwipe(position: Int, holder: LevelsAdapter.ViewHolder) {

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
                    val adapterPosition = viewHolder.adapterPosition
                    Log.e("adapterPositionpostion","$adapterPosition : $position")
                    if (direction == ItemTouchHelper.LEFT) {
                        val deletedModel = levelItemsList[position].subLevelsDetails[adapterPosition]
                        Log.e("deletedModel","$deletedModel")
                        // adapterU.deleteItem(position)
                        levelItemsList[position].subLevelsDetails.removeAt(adapterPosition)
                        levelItemsList[position].subLevelsDetails.size
                        holder.rcv.adapter!!.notifyItemRemoved(adapterPosition)
                        holder.rcv.adapter!!.notifyDataSetChanged()
                        ToastUtil.showShortToast(requireContext(), "deleted")
                    } else {
                        val subLevelsDetailsItem = levelItemsList[position].subLevelsDetails[adapterPosition]
                        Log.e("subLevelsDetailsItem","$subLevelsDetailsItem")
                        ToastUtil.showShortToast(requireContext(), "edit")
                        args = Bundle()
                        args?.putString(LevelDialog.SUB_LEVEL_DATA, Gson().toJson(subLevelsDetailsItem))
                        val levelDialog = LevelDialog()
                        levelDialog.arguments = args
                        levelDialog.setCallBack(object : LevelDialog.ItemAdded {
                            override fun onCancel() {
                                val vh = rcvLevel.findViewHolderForAdapterPosition(position) as LevelsAdapter.ViewHolder
                                vh.rcv.adapter?.notifyItemChanged(adapterPosition)
                            }

                            override fun onItemUpdated(item: SubLevelsDetailsItem) {
                                levelItemsList[position].subLevelsDetails[adapterPosition] = item
                                val vh = rcvLevel.findViewHolderForAdapterPosition(position) as LevelsAdapter.ViewHolder
                                vh.rcv.adapter?.notifyItemChanged(adapterPosition)
                            }

                            override fun onItemAdded(item: SubLevelsDetailsItem) {


                            }
                        })
                        levelDialog.show(requireFragmentManager(), "LevelDialog")
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
                            p.color = Color.parseColor("#388E3C")
                            val background =
                                RectF(itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat())
                            c.drawRect(background, p)

                            icon = BitmapFactory.decodeResource(res, R.drawable.edit)
                            val icon_dest = RectF(
                                itemView.left.toFloat() + width,
                                itemView.top.toFloat() + width,
                                itemView.left.toFloat() + 2 * width,
                                itemView.bottom.toFloat() - width
                            )
                            c.drawBitmap(icon, null, icon_dest, p)
                        } else {
                            p.color = Color.parseColor("#D32F2F")
                            val background = RectF(
                                itemView.right.toFloat() + dX,
                                itemView.top.toFloat(),
                                itemView.right.toFloat(),
                                itemView.bottom.toFloat()
                            )
                            c.drawRect(background, p)

                            icon = BitmapFactory.decodeResource(res, R.drawable.delete)
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
        val vh = rcvLevel.findViewHolderForAdapterPosition(position) as LevelsAdapter.ViewHolder
        itemTouchHelper.attachToRecyclerView(holder.rcv)
    }
}
