package com.tt.lvruheng.eyepetizer.mvp.model

import android.content.Context
import com.tt.lvruheng.eyepetizer.mvp.model.bean.FindBean
import com.tt.lvruheng.eyepetizer.network.ApiService
import com.tt.lvruheng.eyepetizer.network.RetrofitClient
import io.reactivex.Observable

class FindModel {

    fun loadData(context: Context): Observable<MutableList<FindBean>>? {

        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val client = retrofitClient.create(ApiService::class.java)
        return client?.getFindData()
    }
}