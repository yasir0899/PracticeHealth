package com.example.practiceHealth.utils

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceHealth.R
import com.example.practiceHealth.RecyclerViewItemClickListener
import com.example.practiceHealth.SubLevelsDetailsItem
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.delete_dialog_layout.*
import kotlinx.android.synthetic.main.sub_level_item_details_dialog_layout.*
import kotlinx.android.synthetic.main.sub_level_item_details_dialog_layout.view.*

class SubItemDetailsDialog : DialogFragment(), RecyclerViewItemClickListener {
    private var userImageRealPath: String = ""
    private var positionFor: Int = 0
    private var args: Bundle? = null
    private lateinit var list: ArrayList<SubItemDetails>
    private lateinit var adapterU: SubItemDetailsAdapter

    private val p = Paint()
    var adapterPosition = 0

    companion object {

        const val SUB_LEVEL_DATA = "subLevelData"
        const val LEVEL_ID = "levelID"
        const val TOTAL_WEIGHT = "totalWeight"
        private const val REQUEST_GALLERY_CAPTURE = 2000
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sub_level_item_details_dialog_layout, container, false)
        args = arguments ?: Bundle()
        var totalRemaining = args!!.getInt(TOTAL_WEIGHT)
        //  view.tvRemainingWeight.text="$totalRemaining% Remaining"
        var subLevelsDetailsItem =
            Gson().fromJson(args!!.getString(SUB_LEVEL_DATA), SubLevelsDetailsItem::class.java)
        var levelId = args!!.getInt(LEVEL_ID)
        if (subLevelsDetailsItem != null) {
            view.tvSubItemDetailTitle.text = subLevelsDetailsItem.sublevelName
            view.tvSubItemDetailWeight.text = subLevelsDetailsItem.weightage.toString()
        }


        view.fabSubItemDescription.setOnClickListener {
            val dialog = AddSubItemDescriptionDialog()
            args = Bundle()

            dialog.setCallBack(object : AddSubItemDescriptionDialog.ItemAdded {
                override fun onUpdated(text: String) {

                }

                override fun onItemAdded(text: String) {
                    list.add(SubItemDetails(false, text, ""))
                    adapterU.notifyDataSetChanged()

                }

                override fun onCancel() {

                }

            })

            dialog.show(requireFragmentManager(), "AddSubItemDescriptionDialog")
        }
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        dialog?.window?.setLayout((width * 0.8).toInt(), (height * 0.4).toInt())
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()
        initData()

    }

    private fun initData() {
        list.add(SubItemDetails(true, "test 1 2 3 4 5 ", ""))

        list.add(SubItemDetails(false, "testing 1212 ", ""))
        list.add(SubItemDetails(true, "test is test ", ""))
        list.add(SubItemDetails(false, "test", ""))
        list.add(SubItemDetails(true, "test", ""))
        list.add(SubItemDetails(false, "test", ""))

        list.add(SubItemDetails(false, "Last one last stage", ""))
        adapterU = SubItemDetailsAdapter(list, requireContext())
        rcvStatusDesc.adapter = adapterU
        adapterU.setOnAdapterClickListener(this)
    }

    override fun onAdapterClickListener(
        position: Int,
        fromShowAttachment: Boolean,
        statusIsCheck: Boolean,
        fromCheckBox: Boolean
    ) {

        enableSwipe(position)
        /*  if (fromShowAttachment) {
              // ToastUtil.showShortToast(requireContext(),"${list[position].attachmentPath}")
              var dialog = ShowAttachmentDialog()
              args = Bundle()

              args?.putString(ShowAttachmentDialog.SUB_LEVEL_DATA, Gson().toJson(list[position]))
              dialog.arguments = args
              dialog.show(requireFragmentManager(), "Dialog")

          } else if (fromCheckBox) {

              list[position].status = statusIsCheck

          } else requestPermission(position)*/


    }

    private fun enableSwipe(position: Int) {

        val simpleItemTouchCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapterPosition = viewHolder.adapterPosition
                    Log.e("ap&p", "$adapterPosition : $position")
                    if (direction == ItemTouchHelper.LEFT) {
                        val deletedModel = list[position]
                        Log.e("deletedModel", "$deletedModel")
                        // adapterU.deleteItem(position)

                        val dialog = Dialog(requireActivity())
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.delete_dialog_layout)
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                        val mDialogCancel = dialog.tvNo
                        mDialogCancel.setOnClickListener {
                            adapterU.notifyItemChanged(adapterPosition)
                            dialog.dismiss()
                        }
                        val mDialogOk = dialog.tvYes
                        mDialogOk.setOnClickListener {

                            list.removeAt(adapterPosition)
                            list.size
                            adapterU.notifyItemRemoved(adapterPosition)
                            adapterU.notifyDataSetChanged()
                            ToastUtil.showShortToast(requireContext(), "deleted")
                            dialog.cancel()
                        }

                        dialog.show()


                    } else {
                        val subLevelsDetailsItemDescription = list[position]
                        Log.e("subLevelsDetailsItem", "$subLevelsDetailsItemDescription")
                        ToastUtil.showShortToast(requireContext(), "edit")
                        args = Bundle()
                        args?.putString(
                            AddSubItemDescriptionDialog.DESCRIPTION,
                            Gson().toJson(subLevelsDetailsItemDescription)
                        )
                        val addSubItemDescriptionDialog = AddSubItemDescriptionDialog()
                        addSubItemDescriptionDialog.arguments = args
                        addSubItemDescriptionDialog.setCallBack(object :
                            AddSubItemDescriptionDialog.ItemAdded {
                            override fun onUpdated(text: String) {

                                list[adapterPosition].description = text
                                adapterU.notifyItemChanged(adapterPosition)

                            }

                            override fun onItemAdded(text: String) {


                            }

                            override fun onCancel() {
                                adapterU.notifyItemChanged(adapterPosition)
                            }

                        })
                        addSubItemDescriptionDialog.show(
                            requireFragmentManager(),
                            "AddSubItemDescriptionDialog"
                        )
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
                                RectF(
                                    itemView.left.toFloat(),
                                    itemView.top.toFloat(),
                                    dX,
                                    itemView.bottom.toFloat()
                                )
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
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)

        itemTouchHelper.attachToRecyclerView(rcvStatusDesc)
    }

    private fun requestPermission(postion: Int) {


        Dexter.withActivity(requireActivity())
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {


                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report?.areAllPermissionsGranted()!!) {

                        dispatchGalleryPictureIntent(postion)


                    }
                }


                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.cancelPermissionRequest()
                    // this is rationale permission dialog
                    // PermissionDialog().showNow(requireFragmentManager(), "PermissionDialog")
                    /*  val dialog = PermissionDialog()
                      dialog.show(fragmentManager, "Dialog")*/

                    val snack = Snackbar.make(
                        clSubItemDetailsDialog,
                        "For picking image from gallery  allow storage  permission from settings.",
                        8000
                    )
                    snack.setActionTextColor(Color.WHITE)
                    snack.setAction("Setting") {
                        // executed when DISMISS is clicked
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", requireActivity().packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, 1000)
                    }
                    snack.show()
                }

            }).check()
    }

    private fun dispatchGalleryPictureIntent(position: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY_CAPTURE)
        positionFor = position

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                REQUEST_GALLERY_CAPTURE -> {

                    if (requestCode == REQUEST_GALLERY_CAPTURE) {
                        super.onActivityResult(requestCode, resultCode, data)
                        if (resultCode == Activity.RESULT_OK) {
                            //data.getParcelableArrayExtra(name);
                            //If Single image selected then it will fetch from Gallery
                            if (data!!.data != null) {
                                val mImageUri: Uri = data.data!!
                                userImageRealPath =
                                    NewRealPath.getRealPath(requireContext(), mImageUri).toString()
                                list[positionFor].attachmentPath = userImageRealPath

                                adapterU.notifyItemChanged(positionFor)
                                Log.i("mImageUri", "$mImageUri")
                                //   ivUser.setImageURI(mImageUri)

                            }
                        }
                    }
                }

            }
        }
    }
}