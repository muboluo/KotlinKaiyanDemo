package com.tt.lvruheng.eyepetizer.mvp.presenter

import android.content.Context
import com.tt.lvruheng.eyepetizer.mvp.contract.HomeContract
import com.tt.lvruheng.eyepetizer.mvp.model.HomeModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.utils.applySchedulers


/**
 * Created by lvruheng on 2017/7/5.
 */
class HomePresenter(context: Context, view: HomeContract.View) : HomeContract.Presenter {

    var mContext: Context? = null
    var mView: HomeContract.View? = null
    val mModel: HomeModel by lazy {
        HomeModel()
    }

    init {
        mContext = context
        mView = view
    }

    override fun start() {

        requestData()

    }

    override fun requestData() {

        mContext?.let {
            mModel.loadData(it, true, "0")
        }
                ?.applySchedulers()
                ?.subscribe { homebean: HomeBean ->
                    mView?.setData(homebean)
                }
    }

    fun moreData(data: String) {

        mContext?.let {
            mModel.loadData(it, false, data)
        }?.applySchedulers()
                ?.subscribe { homeBean: HomeBean ->
                    mView?.setData(homeBean)
                }

    }


}




