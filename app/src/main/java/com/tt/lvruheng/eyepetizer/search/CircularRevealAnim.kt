package com.tt.lvruheng.eyepetizer.search

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.DecelerateInterpolator


//动画操作类
/**
 * 添加动画结束接口，放大接口和缩小接口
 * 放大方法
 * 缩小方法
 * 放大、缩小具体实现方法
 *
 *      根据版本判断是否需要动画
 *      计算动画开始位置
 *      计算最大半径
 *      设置开始位置和结束位置
 *      设置动画并开始方法
 *
 * 低版本不需要动画方法
 *
 */

class CircularRevealAnim {

    private var startView: View? = null
    private var animView: View? = null
    private var showOrNot: Boolean = false


    companion object {

        val ANIM_DURATION = 300
    }

    var animListener: AnimListener? = null

    @SuppressLint("NewApi")
    fun showView(startView: View, animView: View) {

        this.animView = animView
        this.startView = startView
        actionShowOrHideView(true)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun actionShowOrHideView(showOrNot: Boolean) {

        this.showOrNot = showOrNot

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            showOrHideAnimateView()
            return
        }
        val animStartPosition = getAnimStartPosition()

        setAnimation(animStartPosition.first, animStartPosition.second, animStartPosition.third)
    }

    private fun showOrHideAnimateView() {

        animListener?.let {

            if (showOrNot) {
                animView?.visibility = View.VISIBLE
                it.onShowAnimEnd()
            } else {
                animView?.visibility = View.GONE
                it.onHideAnimEnd()
            }
        }
    }

    private fun getAnimStartPosition(): Triple<Int, Int, Float> {

        var startX = 0
        var startY = 0
        val startLocationArray = IntArray(2)
        startView?.let {

            it.getLocationOnScreen(startLocationArray)

            startX = startLocationArray[0] + it.width / 2
            startY = startLocationArray[1] + it.height / 2

        }

        val animLocationArray = IntArray(2)
        var animX = 0
        var animY = 0

        animView?.let {

            it.getLocationOnScreen(animLocationArray)
            animX = animLocationArray[0] + it.width / 2
            animY = animLocationArray[1] + it.height / 2
        }

        val radiusX = if (startX > animX) startX - animLocationArray[0] else animView!!.width - startX
        val radiusY = if (startY > animY) startY - animLocationArray[1] else animView!!.height - startY

        val radius = Math.sqrt((radiusX * radiusX + radiusY * radiusY).toDouble()).toFloat()

        return Triple(startX, startY, radius)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setAnimation(startX: Int, startY: Int, radius: Float) {

        animView?.visibility = View.VISIBLE

        var startRadius = 0.0f
        var endRadius = 0.0f
        if (showOrNot) endRadius = radius else startRadius = radius
        val anim = ViewAnimationUtils.createCircularReveal(animView, startX, startY, startRadius, endRadius)

        anim.duration = ANIM_DURATION.toLong()
        anim.interpolator = DecelerateInterpolator()
        anim.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)

                showOrHideAnimateView()
            }
        })
        anim.start()
    }

    @SuppressLint("NewApi")
    fun hideView(startView: View, animView: View) {

        this.startView = startView
        this.animView = animView
        actionShowOrHideView(false)
    }

    interface AnimListener {

        fun onShowAnimEnd()
        fun onHideAnimEnd()
    }
}