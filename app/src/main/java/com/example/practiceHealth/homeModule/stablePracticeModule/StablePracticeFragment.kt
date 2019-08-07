package com.example.practiceHealth.homeModule.stablePracticeModule


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.practiceHealth.MainActivity
import com.example.practiceHealth.R
import com.example.practiceHealth.adapters.NewPracticesAdapter
import com.example.practiceHealth.homeModule.newPracticeModule.NewPracticesVM
import com.example.practiceHealth.interfaces.RecyclerViewItemClickListener
import com.example.practiceHealth.models.responseModels.NewPracticesResponseModel
import kotlinx.android.synthetic.main.fragment_stable_practice.*

class StablePracticeFragment : Fragment(), RecyclerViewItemClickListener {

    private val newPracticesVM: NewPracticesVM by lazy {
        ViewModelProviders.of(this).get(NewPracticesVM::class.java)
    }
    private lateinit var adapterU: NewPracticesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stable_practice, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as MainActivity).toolBarsCenterTitle(getString(R.string.stable_practice))
        (activity as MainActivity).supportActionBar?.title = ""
        (activity as MainActivity).hideSignOutOption()
        initStablePracticeItems()


    }

    private fun initStablePracticeItems() {
        pbStablePractice.visibility=View.VISIBLE
        newPracticesVM.getNewPractices("2")?.observe(this, Observer<ArrayList<NewPracticesResponseModel>> {

            pbStablePractice.visibility=View.INVISIBLE
            if (it != null) {
                adapterU = NewPracticesAdapter(it, requireContext(), false)
                rcvStablePractice.adapter = adapterU
                adapterU.setOnAdapterClickListener(this)
            }


        })
    }

    override fun onAdapterClickListener(position: Int) {

    }

}
