package com.tt.lvruheng.eyepetizer.ui.fragment

import android.app.Fragment
import android.os.Bundle
import com.tt.lvruheng.eyepetizer.R


class HotFragment : BaseFragment() {

    private var fragmentList: ArrayList<Fragment>? = null

    private val STRATEGY = arrayOf("weekly", "monthly", "historical")

    private val TITLE = "strategy"

    override fun getLayoutResources(): Int {

        return R.layout.hot_fragment
    }

    override fun initView() {

        initWeekFragment()
    }

    private fun initWeekFragment() {

        val weekFragment = RankFragment() as Fragment
        val weekBundle = Bundle()
        weekBundle.putString("strategy", STRATEGY[0])
        weekFragment.arguments = weekBundle

        fragmentList?.add(weekFragment)
    }

    private fun initMonthFragment() {

        val monthFragment = RankFragment() as Fragment
        val monthBundle = Bundle()
        monthBundle.putString(TITLE, STRATEGY[1])

    }

}