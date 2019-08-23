package com.example.practiceHealth.homeModule


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.practiceHealth.activities.MainActivity
import com.example.practiceHealth.R
import com.example.practiceHealth.adapters.HomeAdapter
import com.example.practiceHealth.models.homeListModel.HomeItems
import com.example.practiceHealth.interfaces.RecyclerViewItemClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), RecyclerViewItemClickListener {


    private lateinit var homeItemsList: ArrayList<HomeItems>
    private lateinit var adapterU: HomeAdapter
    private var args: Bundle? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args = arguments ?: Bundle()
        (context as MainActivity).toolBarsCenterTitle("")
        (activity as MainActivity).supportActionBar?.title = getString(
            R.string.home
        )
        (activity as MainActivity).toolbar.visibility = View.VISIBLE
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as MainActivity).hideSignOutOption(true)
        homeItemsList = ArrayList()
        pbHome.visibility = View.VISIBLE
        initHomeItems()
    }

    private fun initHomeItems() {

        homeItemsList.clear()
        homeItemsList.add(
            HomeItems(
                "New Practices",
                R.drawable.new_practice
            )
        )
        homeItemsList.add(
            HomeItems(
                "Stable Practices",
                R.drawable.stable_practice
            )
        )
        homeItemsList.add(
            HomeItems(
                "Follow Up",
                R.drawable.follow_up
            )
        )
        homeItemsList.add(
            HomeItems(
                "Settings",
                R.drawable.setting
            )
        )


        pbHome.visibility = View.GONE
        adapterU = HomeAdapter(homeItemsList, requireContext())
        /* rcvHome.layoutManager = GridLayoutManager(requireContext(),
             activity!!.resources.getDimension(R.dimen.card_width).toInt())*/
        rcvHome.adapter = adapterU
        adapterU.setOnAdapterClickListener(this)

    }

    override fun onAdapterClickListener(position: Int) {

        when {
            adapterU.getItem(position).item == "New Practices" -> {
                args = Bundle()
                val navController = Navigation.findNavController(
                    activity!!,
                    R.id.nav_host_fragment
                )

                  navController.navigate(R.id.action_homeFragment_to_newPracticesFragment, args)


            }
            adapterU.getItem(position).item == "Stable Practices" -> {
                args = Bundle()
                val navController = Navigation.findNavController(
                    activity!!,
                    R.id.nav_host_fragment
                )

                    navController.navigate(R.id.action_homeFragment_to_stablePracticeFragment, args)


            }

            adapterU.getItem(position).item == "Follow Up" -> {
                args = Bundle()
                val navController = Navigation.findNavController(
                    activity!!,
                    R.id.nav_host_fragment
                )

                  navController.navigate(R.id.action_homeFragment_to_followUpFragment, args)


            }

            adapterU.getItem(position).item == "Settings" -> {
                args = Bundle()
                val navController = Navigation.findNavController(
                    activity!!,
                    R.id.nav_host_fragment
                )

                navController.navigate(R.id.action_homeFragment_to_settingFragment, args)


            }

        }


    }
}