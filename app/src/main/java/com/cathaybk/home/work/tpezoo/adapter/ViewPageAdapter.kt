package com.cathaybk.home.work.tpezoo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class ViewPageAdapter(fActivity: FragmentActivity) : FragmentStateAdapter(fActivity) {
    private var fragmentList = ArrayList<Fragment>()
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addFragment(fragment: Fragment){
        fragmentList.add(fragment)
    }

}