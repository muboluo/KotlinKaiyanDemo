package com.tt.lvruheng.eyepetizer.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.HotAdatpter
import kotlinx.android.synthetic.main.hot_fragment.*


class HotFragment : BaseFragment() {

    var fragmentList: ArrayList<Fragment> = ArrayList()

    private val STRATEGY = arrayOf("weekly", "monthly", "historical")

    private var tabs = listOf<String>("周", "月", "历史").toMutableList()

    private val TITLE = "strategy"

    override fun getLayoutResources(): Int {

        return R.layout.hot_fragment
    }

    override fun initView() {

        initWeekFragment()

        initMonthFragment()

        initHistoryFragment()

        initViewPager()
    }

    private fun initWeekFragment() {

        val weekFragment = RankFragment() as Fragment
        val weekBundle = Bundle()
        weekBundle.putString(TITLE, STRATEGY[0])
        weekFragment.arguments = weekBundle

        fragmentList.add(weekFragment)
    }

    private fun initMonthFragment() {

        val monthFragment = RankFragment() as Fragment
        val monthBundle = Bundle()
        monthBundle.putString(TITLE, STRATEGY[1])
        monthFragment.arguments = monthBundle

        fragmentList.add(monthFragment)
    }

    private fun initHistoryFragment() {

        val historyFragment = RankFragment() as Fragment
        val historyBundle = Bundle()
        historyBundle.putString(TITLE, STRATEGY[2])
        historyFragment.arguments = historyBundle

        fragmentList.add(historyFragment)
    }

    private fun initViewPager() {

        vp_content.adapter = HotAdatpter(fragmentManager, fragmentList, tabs)
        tab_layout.setupWithViewPager(vp_content)
    }
}