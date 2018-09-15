package com.tt.lvruheng.eyepetizer.ui.fragment

import android.content.Intent
import android.graphics.Typeface
import android.view.View
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.R.layout.mine_fragment
import com.tt.lvruheng.eyepetizer.ui.AdviseActivity
import com.tt.lvruheng.eyepetizer.ui.CacheActivity
import com.tt.lvruheng.eyepetizer.ui.WatchActivity
import kotlinx.android.synthetic.main.mine_fragment.*

/**
 * Created by lvruheng on 2017/7/4.
 */
class MineFragment : BaseFragment(), View.OnClickListener {

    val FONT_ASSET_NAME = "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"

    override fun getLayoutResources(): Int {

        return mine_fragment
    }

    override fun initView() {

        tv_advise.setOnClickListener(this)
        tv_save.setOnClickListener(this)
        tv_watch.setOnClickListener(this)

        tv_advise.typeface = Typeface.createFromAsset(context?.assets, FONT_ASSET_NAME)
        tv_watch.typeface = Typeface.createFromAsset(context?.assets, FONT_ASSET_NAME)
        tv_save.typeface = Typeface.createFromAsset(context?.assets, FONT_ASSET_NAME)
    }

    override fun onClick(p0: View?) {

        when (p0?.id) {

            R.id.tv_advise -> {
                startActivity(Intent(activity, AdviseActivity::class.java))
            }

            R.id.tv_save -> {

                startActivity(Intent(activity, CacheActivity::class.java))
            }
            R.id.tv_watch -> {

                startActivity(Intent(activity, WatchActivity::class.java))
            }
        }

    }
}