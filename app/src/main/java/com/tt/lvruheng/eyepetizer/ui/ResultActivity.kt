package com.tt.lvruheng.eyepetizer.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gyf.barlibrary.ImmersionBar
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.FeedAdapter
import com.tt.lvruheng.eyepetizer.mvp.contract.ResultContract
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HotBean
import com.tt.lvruheng.eyepetizer.mvp.presenter.ResultPresenter
import kotlinx.android.synthetic.main.activity_result.*

/**
 * Created by lvruheng on 2017/7/11.
 */
class ResultActivity : AppCompatActivity(), ResultContract.View, SwipeRefreshLayout.OnRefreshListener {

    lateinit var keyWord: String
    lateinit var mPresenter: ResultPresenter
    lateinit var mAdapter: FeedAdapter
    var mIsRefresh: Boolean = false
    var mList = ArrayList<HotBean.ItemListBean.DataBean>()
    var start: Int = 10

    companion object {

        val KEY_WORD: String = "keyWord"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        setContentView(R.layout.activity_result)

        getDataFromLastPage()

        initPresenter()

        setToolbar()

        initRecyclerview()
    }

    private fun getDataFromLastPage() {

        keyWord = intent.getStringExtra(KEY_WORD)
    }

    private fun initPresenter() {

        mPresenter = ResultPresenter(this, this)
        mPresenter.requestData(keyWord, start)
    }

    private fun setToolbar() {

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title = "$keyWord 相关"
            it.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initRecyclerview() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = FeedAdapter(this, mList)
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshListener(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                recyclerView?.let {

                    val manager = it.layoutManager as LinearLayoutManager
                    val lastVisiblePosition = manager.findLastVisibleItemPosition()

                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisiblePosition == mList.size - 1) {

                        start = start.plus(10)
                        mPresenter.requestData(keyWord,start)
                    }
                }
            }
        })
    }

    override fun setData(bean: HotBean) {

        if (mIsRefresh) {

            mIsRefresh = false
            refreshLayout.isRefreshing = false

            if (mList.size > 0) {
                mList.clear()
            }
        }
        bean.itemList?.forEach {

            it.data?.let { it1 -> mList.add(it1) }
        }
    }

    override fun onRefresh() {

        if (mIsRefresh.not()) {

            mIsRefresh = true
            start = 10
            mPresenter.requestData(keyWord, start)
        }
    }
}