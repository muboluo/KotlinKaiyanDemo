package com.tt.lvruheng.eyepetizer.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class HotAdatpter(fm: FragmentManager, fragmentList: ArrayList<Fragment>, titleList: MutableList<String>)
    : FragmentPagerAdapter(fm) {

    var fragmentManager: FragmentManager = fm
    var fragmentList: ArrayList<Fragment> = fragmentList
    var titleList: MutableList<String> = titleList


    override fun getItem(position: Int): Fragment {

        return fragmentList[position]
    }

    override fun getCount(): Int {

        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence {

        return titleList[position]
    }

}