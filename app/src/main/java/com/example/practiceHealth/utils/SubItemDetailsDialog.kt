package com.example.practiceHealth.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
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
import kotlinx.android.synthetic.main.sub_level_item_details_dialog_layout.*
import kotlinx.android.synthetic.main.sub_level_item_details_dialog_layout.view.*

class SubItemDetailsDialog : DialogFragment(), RecyclerViewItemClickListener {
    private var userImageRealPath: String = ""
    private  var positionFor:Int=0
    private var args: Bundle? = null
    private lateinit var list: ArrayList<SubItemDetails>
    private lateinit var adapterU: SubItemDetailsAdapter

    companion object {

        const val SUB_LEVEL_DATA = "subLevelData"
        const val LEVEL_ID = "levelID"
        const val TOTAL_WEIGHT = "totalWeight"
        private const val REQUEST_GALLERY_CAPTURE = 2000
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.sub_level_item_details_dialog_layout, container, false)
        args = arguments ?: Bundle()
        var totalRemaining = args!!.getInt(TOTAL_WEIGHT)
        //  view.tvRemainingWeight.text="$totalRemaining% Remaining"
        var subLevelsDetailsItem = Gson().fromJson(args!!.getString(SUB_LEVEL_DATA), SubLevelsDetailsItem::class.java)
        var levelId = args!!.getInt(LEVEL_ID)
        if (subLevelsDetailsItem != null) {
            view.tvSubItemDetailTitle.text = subLevelsDetailsItem.sublevelName
            view.tvSubItemDetailWeight.text = subLevelsDetailsItem.weightage.toString()
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
        list.add(SubItemDetails(true, "test 1 2 3 4 5 ",""))

        list.add(SubItemDetails(false, "testing 1212 ",""))
        list.add(SubItemDetails(true, "test is test ",""))
        list.add(SubItemDetails(false, "test",""))
        list.add(SubItemDetails(true, "test",""))
        list.add(SubItemDetails(false, "test",""))

        list.add(SubItemDetails(false, "Last one last stage",""))
        adapterU = SubItemDetailsAdapter(list, requireContext())
        rcvStatusDesc.adapter = adapterU
        adapterU.setOnAdapterClickListener(this)
    }

    override fun onAdapterClickListener(position: Int, fromShowAttachment: Boolean) {
        if (fromShowAttachment) {
           // ToastUtil.showShortToast(requireContext(),"${list[position].attachmentPath}")
            var dialog=ShowAttachmentDialog()
            args=Bundle()

            args?.putString(ShowAttachmentDialog.SUB_LEVEL_DATA, Gson().toJson(list[position]))
            dialog.arguments=args
            dialog.show(requireFragmentManager(),"Dialog")

        } else requestPermission(position)


    }


    private fun requestPermission(postion:Int) {


        Dexter.withActivity(requireActivity())
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
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

                    val snack = Snackbar.make(clSubItemDetailsDialog, "For picking image from gallery  allow storage  permission from settings.", 8000)
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
        positionFor=position

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
                                list[positionFor].attachmentPath=userImageRealPath

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