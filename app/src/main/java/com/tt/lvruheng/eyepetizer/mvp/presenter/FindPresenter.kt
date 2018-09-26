package com.tt.lvruheng.eyepetizer.mvp.presenter

import android.content.Context
import com.tt.lvruheng.eyepetizer.mvp.contract.FindContract
import com.tt.lvruheng.eyepetizer.mvp.model.FindModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean
import com.tt.lvruheng.eyepetizer.utils.applySchedulers

class FindPresenter(context: Context, view: FindContract.View) : FindContract.Presenter {

    var context: Context? = null
    var mView: FindContract.View? = null
    val mModle: FindModel by lazy {
        FindModel()

    }

    init {
        this.context = context
        mView = view
    }

    override fun start() {

        requestData()
    }

    override fun requestData() {

        mView?.let {
            mModle.loadData(context!!)
                    ?.applySchedulers()
                    ?.subscribe { beans: MutableList<FindBean> ->
                        mView?.setData(beans)
                    }
        }
    }


}