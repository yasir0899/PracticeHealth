package com.example.practiceHealth

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.practiceHealth.utils.ToastUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), naigationToFragment {
    private var optionMenu: Menu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        // naigateToListView(null)
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_call -> {
                ToastUtil.showShortToast(this, "clicked")
                val navController = Navigation.findNavController(
                    this,
                    R.id.nav_host_fragment
                )
                navController.popBackStack()
                navController.navigate(R.id.homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                ToastUtil.showShortToast(this, "clicked")
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        optionMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_sign_out -> {
                val navController = Navigation.findNavController(
                    this,
                    R.id.nav_host_fragment
                )
                navController.popBackStack()
                navController.navigate(R.id.signInFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun naigateToListView(bundle: Bundle?) {
        val fr = SettingFragment()
        if (bundle != null) fr.arguments = bundle
        replaceFragment(fr, SettingFragment.TAG)
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {

        var ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, fragment, tag)
        ft.addToBackStack(null)
        ft.commit()
    }

    fun toolBarsCenterTitle(title: String) {
        toolbar_title.text = title
    }

    fun hideSignOutOption(isVisibility: Boolean = false) {

        if (optionMenu != null) {
            val item = optionMenu!!.findItem(R.id.action_sign_out)
            if (item != null) {
                item.isVisible = isVisibility
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
