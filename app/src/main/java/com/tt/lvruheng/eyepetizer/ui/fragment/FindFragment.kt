package com.tt.lvruheng.eyepetizer.ui.fragment

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.adapter.FindAdapter
import com.tt.lvruheng.eyepetizer.mvp.contract.FindContract
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean
import com.tt.lvruheng.eyepetizer.mvp.presenter.FindPresenter
import com.tt.lvruheng.eyepetizer.ui.FindDetailActivity
import kotlinx.android.synthetic.main.find_fragment.*

/**
 * Created by lvruheng on 2017/7/4.
 */
class FindFragment : BaseFragment(), FindContract.View, AdapterView.OnItemClickListener {

    var findPresenter: FindPresenter? = null
    var findList: MutableList<FindBean> = ArrayList()
    var findAdapter: FindAdapter? = null


    override fun setData(beans: MutableList<FindBean>) {

        findList = beans
        findAdapter?.dataList = beans
        findAdapter?.notifyDataSetChanged()
    }

    override fun getLayoutResources(): Int {

        return R.layout.find_fragment
    }

    override fun initView() {

        findPresenter = FindPresenter(context, this)
        findAdapter = FindAdapter(context, findList)
        gv_find.adapter = findAdapter
        gv_find.onItemClickListener = this

        findPresenter?.start()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val findBean = findList[position]
        val name = findBean.name

        val intent = Intent(context, FindDetailActivity::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }

}