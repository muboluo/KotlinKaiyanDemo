package com.tt.lvruheng.eyepetizer.mvp.presenter

import android.content.Context
import com.tt.lvruheng.eyepetizer.mvp.contract.HotContract
import com.tt.lvruheng.eyepetizer.mvp.model.HotModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean
import com.tt.lvruheng.eyepetizer.utils.applySchedulers

/**
 * Created by lvruheng on 2017/7/7.
 */
class HotPresenter(context: Context, view: HotContract.View) : HotContract.Presenter {

    private var hotView: HotContract.View? = null
    var context: Context? = null

    private val hotModel by lazy {
        HotModel()
    }

    init {
        this.hotView = view
        this.context = context
    }

    override fun start() {

    }

    override fun requestData(strategy: String) {

        context?.let {

            hotModel.loadData(context!!, strategy)
        }
                ?.applySchedulers()
                ?.subscribe { bean: HotBean ->
                    hotView?.setData(bean)
                }
    }
}