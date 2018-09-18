package com.tt.lvruheng.eyepetizer.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.HomeAdatper
import com.tt.lvruheng.eyepetizer.mvp.contract.HomeContract
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.regex.Pattern

class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    var mIsRefresh: Boolean = false
    var mPresenter: HomePresenter? = null
    var mList = ArrayList<HomeBean.IssueListBean.ItemListBean>()
    var mAdapter: HomeAdatper? = null
    var data: String? = null

    override fun getLayoutResources(): Int {

        return R.layout.home_fragment
    }

    override fun initView() {

        mPresenter = HomePresenter(context, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = HomeAdatper(context, mList)
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshListener(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val linearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                val lastPosition = linearLayoutManager.findLastVisibleItemPosition()

                if (lastPosition == mList?.size - 1 && newState == RecyclerView.SCROLL_STATE_IDLE && data != null) {
                    mPresenter?.moreData(data!!)
                }
            }
        })
    }

    override fun onRefresh() {

        if (!mIsRefresh) {

            mIsRefresh = true
            mPresenter?.start()
        }
    }

    override fun setData(bean: HomeBean) {

        val regEx = "[^0-9]"

        val compile = Pattern.compile(regEx)
        val matcher = compile.matcher(bean.nextPageUrl)
        data = matcher.replaceAll("").subSequence(1, matcher.replaceAll("").length - 1).toString()

        if (mIsRefresh) {

            mIsRefresh = false
            refreshLayout.isRefreshing = false
            if (mList.size > 0) {
                mList.clear()
            }
        }

        bean.issueList!!
                .flatMap { it.itemList!! }
                .filter { it.type.equals("video") }
                .forEach { mList.add(it) }

        mAdapter?.notifyDataSetChanged()


    }
}