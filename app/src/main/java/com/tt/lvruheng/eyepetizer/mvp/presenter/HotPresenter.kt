package com.tt.lvruheng.eyepetizer.mvp.presenter

import android.content.Context
import com.tt.lvruheng.eyepetizer.mvp.contract.HotContract
import com.tt.lvruheng.eyepetizer.mvp.model.HotModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean
import com.tt.lvruheng.eyepetizer.utils.applySchedulers

class HotPresenter(context: Context?, hotView: HotContract.View?) : HotContract.Presenter {

    var context: Context? = null

    private var hotView: HotContract.View? = null

    private val hotModel by lazy {
        HotModel()
    }

    init {

        this.hotView = hotView
        this.context = context
    }

    constructor(context: Context?, hotView: HotContract.View?, strategy: String) : this(context, hotView) {


    }

    override fun start() {

    }

    override fun requestData(strategy: String) {

        context?.let {

            hotModel.loadData(it, strategy)
        }
                ?.applySchedulers()
                ?.subscribe { bean: HotBean ->
                    hotView?.setData(bean)
                }
    }
}