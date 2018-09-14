package com.tt.lvruheng.eyepetizer.ui

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.gyf.barlibrary.ImmersionBar
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.R.color.black
import com.tt.lvruheng.eyepetizer.R.color.gray
import com.tt.lvruheng.eyepetizer.search.SEARCH_TAG
import com.tt.lvruheng.eyepetizer.search.SearchFragment
import com.tt.lvruheng.eyepetizer.ui.fragment.FindFragment
import com.tt.lvruheng.eyepetizer.ui.fragment.HomeFragment
import com.tt.lvruheng.eyepetizer.ui.fragment.HotFragment
import com.tt.lvruheng.eyepetizer.ui.fragment.MineFragment
import com.tt.lvruheng.eyepetizer.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var homeFragment: HomeFragment? = null
    var findFragment: FindFragment? = null
    var hotFragment: HotFragment? = null
    var mineFragment: MineFragment? = null

    var mExitTime: Long = 0

    var toast: Toast? = null
    lateinit var searchFragment: SearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWindowAttributes()

        initToolBar()

        initFragment(savedInstanceState)

        initRadioButton()
    }

    private fun initWindowAttributes() {
        ImmersionBar.with(this)
                .transparentBar()
                .barAlpha(0.3f)
                .fitsSystemWindows(true)
                .init()

        window.attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private fun initToolBar() {

        var today = getToday()
        tv_bar_title.text = today
        tv_bar_title.typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
        iv_search.setOnClickListener {

            if (rb_mine.isChecked) {

            } else {

                searchFragment = SearchFragment()
                searchFragment.show(fragmentManager, SEARCH_TAG)

            }
        }

    }

    private fun getToday(): String {

        var list = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

        var calendar = Calendar.getInstance()
        calendar.time = Date()

        var index = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0) {
            index = 0
        }
        return list[index]
    }

    private fun initFragment(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {

            getFragmentFromCache()
        } else {

            initFragments()
        }
        supportFragmentManager.beginTransaction()
                .show(homeFragment)
                .hide(findFragment)
                .hide(mineFragment)
                .hide(hotFragment)
                .commit()
    }

    private fun getFragmentFromCache() {

        val fragments = supportFragmentManager.fragments
        for (item in fragments) {

            if (item is HomeFragment) {

                homeFragment = item
            } else if (item is FindFragment) {

                findFragment = item
            } else if (item is HotFragment) {

                hotFragment = item
            } else if (item is MineFragment) {

                mineFragment = item
            }
        }
    }

    private fun initFragments() {

        homeFragment = HomeFragment()
        findFragment = FindFragment()
        mineFragment = MineFragment()
        hotFragment = HotFragment()

        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.fl_content, homeFragment)
        beginTransaction.add(R.id.fl_content, findFragment)
        beginTransaction.add(R.id.fl_content, mineFragment)
        beginTransaction.add(R.id.fl_content, hotFragment)
        beginTransaction.commit()
    }

    private fun initRadioButton() {

        rb_home.isChecked = true
        rb_home.setTextColor(resources.getColor(black))

        rb_home.setOnClickListener(this)
        rb_find.setOnClickListener(this)
        rb_mine.setOnClickListener(this)
        rb_hot.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        clearRadioButtonState()

        when (p0?.id) {

            R.id.rb_home -> {

                onCheckHomeRadioButton()
            }
            R.id.rb_find -> {

                onCheckFindRadioButton()
            }
            R.id.rb_mine -> {

                onCheckMineRadioButton()
            }
            R.id.rb_hot -> {

                onCheckHotRadioButton()
            }
        }

    }

    private fun onCheckHomeRadioButton() {

        rb_home.isChecked = true
        rb_home.setTextColor(resources.getColor(black))

        tv_bar_title.text = getToday()
        tv_bar_title.visibility = View.VISIBLE

        iv_search.setImageResource(R.drawable.icon_search)

        supportFragmentManager.beginTransaction()
                .show(homeFragment)
                .hide(findFragment)
                .hide(mineFragment)
                .hide(hotFragment)
                .commit()
    }

    private fun onCheckFindRadioButton() {

        rb_find.isChecked = true
        rb_find.setTextColor(resources.getColor(black))

        tv_bar_title.text = "Discovery"
        tv_bar_title.visibility = View.VISIBLE

        iv_search.setImageResource(R.drawable.icon_search)

        supportFragmentManager.beginTransaction()
                .show(findFragment)
                .hide(homeFragment)
                .hide(mineFragment)
                .hide(hotFragment)
                .commit()
    }

    private fun onCheckMineRadioButton() {

        rb_mine.isChecked = true
        rb_mine.setTextColor(resources.getColor(black))

        tv_bar_title.visibility = View.GONE

        iv_search.setImageResource(R.drawable.icon_search)

        supportFragmentManager.beginTransaction()
                .show(mineFragment)
                .hide(homeFragment)
                .hide(findFragment)
                .hide(hotFragment)
                .commit()
    }

    private fun onCheckHotRadioButton() {

        rb_hot.isChecked = true
        rb_hot.setTextColor(resources.getColor(black))

        tv_bar_title.visibility = View.VISIBLE
        tv_bar_title.text = "Ranking"


        iv_search.setImageResource(R.drawable.icon_search)

        supportFragmentManager.beginTransaction()
                .show(hotFragment)
                .hide(homeFragment)
                .hide(findFragment)
                .hide(mineFragment)
                .commit()
    }

    private fun clearRadioButtonState() {

        rg_root.clearCheck()

        rb_home.setTextColor(resources.getColor(gray))
        rb_find.setTextColor(resources.getColor(gray))
        rb_mine.setTextColor(resources.getColor(gray))
        rb_hot.setTextColor(resources.getColor(gray))
    }

    override fun onPause() {
        super.onPause()
        toast?.cancel()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (System.currentTimeMillis().minus(mExitTime) <= 3000) {

                finish()
                toast?.cancel()
            } else {

                mExitTime = System.currentTimeMillis()
                toast = showToast("再按一次 退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}
