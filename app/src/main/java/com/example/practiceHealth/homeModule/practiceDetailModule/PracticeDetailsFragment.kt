package com.example.practiceHealth.homeModule.practiceDetailModule


import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
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
import com.example.practiceHealth.R
import com.example.practiceHealth.activities.MainActivity
import com.example.practiceHealth.adapters.PracticeDetailsAdapter
import com.example.practiceHealth.interfaces.RecyclerViewItemPositionViewHolderClickListener
import com.example.practiceHealth.models.requestModels.PracticeStageLevelRequestModel
import com.example.practiceHealth.models.responseModels.DescriptionModel
import com.example.practiceHealth.models.responseModels.NewPracticesResponseModel
import com.example.practiceHealth.models.responseModels.PracticeDetailsResponseModel
import com.example.practiceHealth.utils.NewRealPath
import com.example.practiceHealth.utils.ShowAttachmentDialog
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.details_dialog_layout.*
import kotlinx.android.synthetic.main.fragment_practice_details.*
import kotlinx.android.synthetic.main.sub_level_item_details_dialog_layout.*


class PracticeDetailsFragment : Fragment(), RecyclerViewItemPositionViewHolderClickListener {
    var isVisibility: Boolean = false
    private var userImageRealPath: String = ""
    private var positionFor: Int = 0
    private var practiceStageId: String? = ""
    private val practiceDetailsVM: PracticeDetailsVM by lazy {
        ViewModelProviders.of(this).get(PracticeDetailsVM::class.java)
    }

    private lateinit var practiceDetailsItemsList: ArrayList<PracticeDetailsResponseModel>
    private lateinit var adapterU: PracticeDetailsAdapter
    private var args: Bundle? = null

    companion object {

        const val PRACTICE = "practice"
        private const val REQUEST_GALLERY_CAPTURE = 2000
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
        var newPracticeItems =
            Gson().fromJson(args!!.getString(PRACTICE), NewPracticesResponseModel::class.java)
        tvPracticeName.text = newPracticeItems.practice
        tvPracticeName.animation =
            AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation)
        practiceStageId = newPracticeItems.practiceStageId
        practiceDetailsItemsList = ArrayList()

        initPracticeDetailsItems(practiceStageId)
    }

    private fun initPracticeDetailsItems(practiceId: String?) {
        // pbPracticeDetails.visibility = View.VISIBLE
        var list = ArrayList<DescriptionModel>()
        list.add(DescriptionModel(false, "test"))
        list.add(DescriptionModel(true, "test"))
        list.add(DescriptionModel(false, "test"))
        list.add(DescriptionModel(true, "test"))
        list.add(DescriptionModel(false, "test"))


        practiceDetailsItemsList.add(
            PracticeDetailsResponseModel(
                1,
                "",
                0,
                "test1",
                1,
                "",
                "hello", list
            )
        )
        practiceDetailsItemsList.add(
            PracticeDetailsResponseModel(
                1,
                "",
                0,
                "test2",
                1,
                "",
                "hello1", list
            )
        )
        practiceDetailsItemsList.add(
            PracticeDetailsResponseModel(
                1,
                "",
                0,
                "test3",
                1,
                "",
                "hello2", list
            )
        )
        practiceDetailsItemsList.add(
            PracticeDetailsResponseModel(
                1,
                "",
                0,
                "test4",
                1,
                "",
                "hello3", list
            )
        )
        practiceDetailsItemsList.add(
            PracticeDetailsResponseModel(
                1,
                "",
                0,
                "test5",
                1,
                "",
                "hello4", list
            )
        )
        adapterU = PracticeDetailsAdapter(practiceDetailsItemsList, requireContext())
        rcvPracticeDetails.adapter = adapterU
        adapterU.setOnAdapterClickListener(this)
        /*  practiceDetailsVM.getPracticesDetails(practiceId!!)

              ?.observe(this, Observer<ArrayList<PracticeDetailsResponseModel>> {
                  pbPracticeDetails.visibility = View.INVISIBLE

                  if (it != null && it.size !== 0) {
                      practiceDetailsItemsList = it
                      practiceDetailsItemsList.add(PracticeDetailsResponseModel(1,"",0,"test1",1))
                      practiceDetailsItemsList.add(PracticeDetailsResponseModel(1,"",0,"test2",1))
                      practiceDetailsItemsList.add(PracticeDetailsResponseModel(1,"",0,"test3",1))
                      practiceDetailsItemsList.add(PracticeDetailsResponseModel(1,"",0,"test4",1))
                      practiceDetailsItemsList.add(PracticeDetailsResponseModel(1,"",0,"test5",1))
                      adapterU = PracticeDetailsAdapter(practiceDetailsItemsList, requireContext())
                      rcvPracticeDetails.adapter = adapterU
                      adapterU.setOnAdapterClickListener(this)
                  }

              })*/

    }

    /* override fun onAdapterClickListener(position: Int) {


         showDialog(position)

     }*/
    override fun onAdapterPositionViewHolderListener(
        position: Int,
        holder: PracticeDetailsAdapter.ViewHolder,
        b: Boolean,
        fromCheckBox: Boolean,
        fromCheckBoxText: Boolean,
        fromAddNote: Boolean,
        fromAddAttach: Boolean,
        fromShowAttachment: Boolean
    ) {
        var isCheck = if (b) {
            1


        } else {
            0
        }
        when {
            fromCheckBox -> {

                val list = practiceDetailsItemsList[position].list
                if (b) {
                    list?.forEach { it.isCheck = true }
                    adapterU.notifyItemChanged(position)
                } else {
                    list?.forEach { it.isCheck = false }
                    adapterU.notifyItemChanged(position)
                }
                val item =
                    PracticeDetailsResponseModel(
                        adapterU.getItem(position).practiceStageLevelId,
                        adapterU.getItem(position).notes,
                        isCheck,
                        adapterU.getItem(position).levelName,
                        adapterU.getItem(position).stageLevelId,
                        adapterU.getItem(position).path,
                        adapterU.getItem(position).description,
                        adapterU.getItem(position).list

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


            }

            fromCheckBoxText -> {
                if (!isVisibility) {
                    isVisibility = true
                    val vh =
                        rcvPracticeDetails.findViewHolderForAdapterPosition(position) as PracticeDetailsAdapter.ViewHolder
                    vh.rcvPracticeDetailsDescription.visibility = View.VISIBLE
                } else {
                    isVisibility = false
                    val vh =
                        rcvPracticeDetails.findViewHolderForAdapterPosition(position) as PracticeDetailsAdapter.ViewHolder
                    vh.rcvPracticeDetailsDescription.visibility = View.GONE
                }


            }
            fromAddAttach -> requestPermission(position)
            fromShowAttachment -> {
                var dialog = ShowAttachmentDialog()
                args = Bundle()

                args?.putString(
                    ShowAttachmentDialog.SUB_LEVEL_DATA,
                    Gson().toJson(practiceDetailsItemsList[position])
                )
                dialog.arguments = args
                dialog.show(requireFragmentManager(), "Dialog")


            }
            fromAddNote -> showDialog(requireActivity(), position, holder, b)
            else -> {


            }
        }
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
                                    NewRealPath.getRealPath(requireContext(), mImageUri)
                                        .toString()
                                practiceDetailsItemsList[positionFor].path = userImageRealPath

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
