package com.doordash.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.doordash.demo.ui.dashboard.DashboardFragment
import com.doordash.demo.ui.home.HomeFragment
import com.doordash.demo.ui.notifications.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var currentFragment: Fragment? = null
    private var onNavigationItemSelectedListener : BottomNavigationView.OnNavigationItemSelectedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)

        setNavigationListener()
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        bottomNavigationView.selectedItemId = R.id.navigation_home

    }

    private fun getFragmentTag(id:Int): String {
        when(id){
            R.id.navigation_home -> return HomeFragment::class.java.simpleName
            R.id.navigation_dashboard -> return DashboardFragment::class.java.simpleName
            R.id.navigation_notifications -> return NotificationsFragment::class.java.simpleName
        }
        return ""
    }

    private fun loadFragment(id: Int) {
        val transaction = supportFragmentManager.beginTransaction()

        var tag = getFragmentTag(id)

        var fragment = supportFragmentManager.findFragmentByTag(tag)

        // if the fragment has not yet been added to the container, add it first
        if (fragment == null) {
            fragment = getFragment(id)
            fragment?.let { transaction.add(R.id.container, it, tag) }
        }

        if (currentFragment != null) {
            currentFragment?.let {
                transaction.hide(it)
            }
        }

        currentFragment = fragment

        fragment?.let { transaction.show(it) }
        transaction.commit()
    }

    private fun getFragment(id: Int): Fragment? {
        when(id) {
            R.id.navigation_home -> return HomeFragment()
            R.id.navigation_dashboard -> return DashboardFragment()
            R.id.navigation_notifications -> return NotificationsFragment()
        }
        return null
    }

    private fun setNavigationListener() {
        onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                if (currentFragment?.tag == getFragmentTag(item.itemId)) {
                    return@OnNavigationItemSelectedListener false
                }

                when (item.itemId) {
                    R.id.navigation_home -> {
                        loadFragment(R.id.navigation_home)
                        supportActionBar?.title = getString(R.string.title_home)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_dashboard -> {
                        loadFragment(R.id.navigation_dashboard)
                        supportActionBar?.title = getString(R.string.title_dashboard)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_notifications -> {
                        loadFragment(R.id.navigation_notifications)
                        supportActionBar?.title = getString(R.string.title_notifications)
                        return@OnNavigationItemSelectedListener true
                    }
                }

                false
            }

    }

}
