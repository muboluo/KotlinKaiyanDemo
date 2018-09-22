package com.tt.lvruheng.eyepetizer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.tt.lvruheng.eyepetizer.R
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean
import com.tt.lvruheng.eyepetizer.utils.ImageLoadUtils

class FindAdapter(context: Context, list: MutableList<FindBean>) : BaseAdapter() {

    var mContext: Context? = null
    var dataList: MutableList<FindBean>? = null
    var inflater: LayoutInflater? = null


    init {

        mContext = context
        dataList = list
        inflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int, itemView: View?, parent: ViewGroup?): View {


        var view: View
        var holder: FindHolder

        when (itemView == null) {

            true -> {

                view = inflater!!.inflate(R.layout.item_find, parent, false)
                holder = FindHolder(view)
                view.tag = holder
            }
            else -> {

                view = itemView!!
                holder = itemView.tag as FindHolder
            }
        }

        holder.tv_title?.text = dataList?.get(position)!!.name
        ImageLoadUtils.display(mContext!!, holder.iv_photo, dataList?.get(position)?.bgPicture!!)

        return view
    }

    override fun getItem(p0: Int): FindBean? {

        return dataList?.get(p0)
    }

    override fun getItemId(p0: Int): Long {

        return p0.toLong()
    }

    override fun getCount(): Int {

        return when (dataList == null) {

            true ->
                0
            false -> dataList!!.size

        }
    }

    class FindHolder(itemView: View) {

        var tv_title: TextView? = null
        var iv_photo: ImageView? = null

        init {
            tv_title = itemView.findViewById(R.id.tv_title) as TextView?
            iv_photo = itemView.findViewById(R.id.iv_photo) as ImageView?
        }
    }

}