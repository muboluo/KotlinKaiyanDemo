package com.tt.lvruheng.eyepetizer.ui

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.WatchAdapter
import com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean
import com.tt.lvruheng.eyepetizer.utils.ObjectSaveUtils
import com.tt.lvruheng.eyepetizer.utils.SPUtils
import kotlinx.android.synthetic.main.activity_watch.*

/**
 * Created by lvruheng on 2017/7/11.
 */
class WatchActivity : AppCompatActivity() {

    var mList = ArrayList<VideoBean>()
    lateinit var mAdapter: WatchAdapter
    private val asyncTask = DataAsyncTask()

    var mHandler: Handler = object : Handler() {

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            var list = msg?.data?.getParcelableArrayList<VideoBean>("beans")
            if (list?.size?.compareTo(0) == 0) {
                tv_hint.visibility = View.VISIBLE
            } else {
                tv_hint.visibility = View.GONE
                if (mList.size > 0) {
                    mList.clear()
                }
                list?.let { mList.addAll(it) }
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()

        setContentView(R.layout.activity_watch)

        setToolbar()

        asyncTask.execute()

        initRecyclerView()
    }

    private fun setToolbar() {

        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title = "观看记录"
            setDisplayHomeAsUpEnabled(true)
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private inner class DataAsyncTask : AsyncTask<Void, Void, ArrayList<VideoBean>>() {

        override fun doInBackground(vararg params: Void?): ArrayList<VideoBean>? {
            var list = ArrayList<VideoBean>()
            var count: Int = SPUtils.getInstance(this@WatchActivity, "beans").getInt("count")
            var i = 1
            while (i.compareTo(count) <= 0) {
                var bean: VideoBean = ObjectSaveUtils.getValue(this@WatchActivity, "bean$i") as VideoBean
                list.add(bean)
                i++
            }
            return list
        }

        override fun onPostExecute(result: ArrayList<VideoBean>?) {
            super.onPostExecute(result)

            val message = mHandler.obtainMessage()

            val bundle = Bundle()
            bundle.putParcelableArrayList("beans", result)
            message.data = bundle

            mHandler.sendMessage(message)
        }
    }

    private fun initRecyclerView() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = WatchAdapter(this, mList)
        recyclerView.adapter = mAdapter
    }

    override fun finish() {

        asyncTask.cancel(true)
        super.finish()
    }
}