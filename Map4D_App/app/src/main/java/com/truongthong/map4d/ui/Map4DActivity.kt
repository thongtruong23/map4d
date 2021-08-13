package com.truongthong.map4d.ui

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.truongthong.map4d.R
import kotlinx.android.synthetic.main.activity_map4d.*
import kotlinx.android.synthetic.main.fragment_home.*


class Map4DActivity : AppCompatActivity() {
    val homeFragment = HomeFragment()
    val savedFragment = SavedFragment()
    val optionFragment = OptionFragment()
    val routeLocationFragment = RouteLocationFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map4d)

        makeCurrentFragment(homeFragment)

        clickItemBottomNavigation()

    }

    private fun clickItemBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> makeCurrentFragment(homeFragment)
                R.id.savedFragment -> makeCurrentFragment(savedFragment)
                R.id.optionFragment -> makeCurrentFragment(optionFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }

    }

}