package com.example.practiceHealth.homeModule.newPracticeModule


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.practiceHealth.activities.MainActivity
import com.example.practiceHealth.R
import com.example.practiceHealth.adapters.NewPracticesAdapter
import com.example.practiceHealth.homeModule.practiceDetailModule.PracticeDetailsFragment
import com.example.practiceHealth.interfaces.RecyclerViewItemClickListener
import com.example.practiceHealth.models.responseModels.NewPracticesResponseModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_new_practices.*


class NewPracticesFragment : Fragment(), RecyclerViewItemClickListener {
    private val newPracticesVM: NewPracticesVM by lazy {
        ViewModelProviders.of(this).get(NewPracticesVM::class.java)
    }

    private lateinit var adapterU: NewPracticesAdapter
    private var args: Bundle? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_practices, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).toolBarsCenterTitle(getString(R.string.new_practices))
        (activity as MainActivity).supportActionBar?.title = ""
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as MainActivity).hideSignOutOption()
        args = arguments ?: Bundle()


        initNewPracticeItems()

    }


    private fun initNewPracticeItems() {
        pbNewPractices.visibility=View.VISIBLE
        newPracticesVM.getNewPractices("1")?.observe(this, Observer<ArrayList<NewPracticesResponseModel>> {


            pbNewPractices.visibility=View.INVISIBLE
            if (it != null && it.isNotEmpty()) {
                adapterU = NewPracticesAdapter(it, requireContext())
                /*val resId = R.anim.layout_animation_fall_down
                val animation = loadLayoutAnimation(requireContext(), resId)
                rcvNewPractice.layoutAnimation = animation*/
                rcvNewPractice.adapter = adapterU
                adapterU.setOnAdapterClickListener(this)
            }


        })

    }


    override fun onAdapterClickListener(position: Int) {


        val newPracticesResponseModel = NewPracticesResponseModel(
            adapterU.getItem(position).practice, adapterU.getItem(position).practiceStageId,
            adapterU.getItem(position).remaining

        )


        args = Bundle()
        args?.putString(
            PracticeDetailsFragment.PRACTICE, Gson().toJson(newPracticesResponseModel)
        )
        val navController = Navigation.findNavController(
            activity!!,
            R.id.nav_host_fragment
        )

        navController.navigate(R.id.action_newPracticesFragment_to_practiceDetailsFragment, args)
    }


}
