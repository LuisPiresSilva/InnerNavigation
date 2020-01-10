package com.example.innernavigation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.innernavigation.R
import com.example.innernavigation.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import inner.navigation.view_pager.NavigationalViewPagerAdapter


class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


//        //Accounts for status bar therefore we increase height
//        ViewCompat.setOnApplyWindowInsetsListener(dataBinding.viewPager) { v, insets ->
//            val params = dataBinding.navView.layoutParams as (ViewGroup.MarginLayoutParams)
//            params.height = actionBarSize().toInt() + insets.systemWindowInsetBottom
//            dataBinding.navView.setPadding(0, 0, 0, insets.systemWindowInsetBottom)
//
//
//            var insets = insets
//            insets = ViewCompat.onApplyWindowInsets(v, insets)
//            var consumed = false
//            var i = 0
//            val count = dataBinding.viewPager.childCount
//            while (i < count) {
//                ViewCompat.dispatchApplyWindowInsets(dataBinding.viewPager.getChildAt(i), insets)
//                if (insets.isConsumed) {
//                    consumed = true
//                }
//                i++
//            }
//            if (consumed) insets.consumeSystemWindowInsets() else insets
//        }


        setUpViewPager()
    }

    @LayoutRes
    fun layoutToInflate(): Int = R.layout.activity_home

    private val dataBinding: ActivityHomeBinding by lazy {
        DataBindingUtil.inflate<ActivityHomeBinding>(LayoutInflater.from(this), layoutToInflate(), null, false)
    }


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home1 ->{
//                dataBinding.viewPager.currentItem = 0

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_home2 -> {
//                dataBinding.viewPager.currentItem = 1

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_home3 -> {
//                dataBinding.viewPager.currentItem = 2

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }



    private lateinit var viewPagerAdapter : NavigationalViewPagerAdapter
    private fun setUpViewPager() {
        dataBinding.viewPager.setSwipeEnabled(false)
        dataBinding.viewPager.offscreenPageLimit = 4


        viewPagerAdapter = NavigationalViewPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
//        viewPagerAdapter.addFragment(MainFragment())
//        viewPagerAdapter.addFragment(ExperiencesFragment())
//        viewPagerAdapter.addFragment(PartnersFragment())
//        viewPagerAdapter.addFragment(ProfileFragment(), true)


        dataBinding.viewPager.adapter = viewPagerAdapter

        dataBinding.navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


    }


}