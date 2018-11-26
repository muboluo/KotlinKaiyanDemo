package com.tt.lvruheng.test.kt

import com.tt.lvruheng.eyepetizer.ui.WatchActivity
import com.tt.lvruheng.eyepetizer.utils.newIntent
import com.tt.lvruheng.eyepetizer.utils.str
import com.tt.lvruheng.test.jv.JavaTransferKotlin

/**
 * Created by henry on 2018/10/25.
 */
open class KotlinTest {

    var name: String? = null
    //编译器默认生成java 类型的 private 类型变量，并提供 get set 方法
    var grade: String = "1"

    //生成 protected 类型变量， 不生成 get set 方法
    @JvmField
    protected var score: Int? = null

    //生成 private 类型变量，不生成 get set
    private var gender: Boolean = true

    //生成 private 类型的变量
    protected var hobby: String? = null

    fun Int.foo() {}

    companion object {

        var test: Int? = null

        // KotlinTest.Companion.getHEIGHT()
        val HEIGHT: String = "HEIGHT"

        //KotlinTest.WEIGHT
        @JvmField
        val WEIGHT: String = "WEIGHT"

        @JvmStatic
        fun staticTest() {
        }
    }

    fun ktMethodTest() {

        //这里要注意一下，是否为空
        val jtk: JavaTransferKotlin = JavaTransferKotlin()
        val field1 = jtk.test?.field1

        val operatorTest: Int = 10


    }

    fun testIF(x: Int): Int {

        return if (x > 10) {
            1
        } else {
            2
        }
    }

    fun testJava() {

        val javaTransferKotlin = JavaTransferKotlin()
        //这里，要注意一下，空安全的问题。这里如果不注意，同样会引发空安全的问题。
        javaTransferKotlin.test = JavaTransferKotlin.FiledTest()

        javaTransferKotlin.test.field1 = ""
        javaTransferKotlin.test?.field1 = ""

    }

    fun testJavaClass() {

        //不需要再使用这里 ::class 了。这里在新版本里面已经支持了。
        val javaClass = KotlinTest.javaClass

    }

    fun testTransferedMeaning() {

        val test = JavaTransferKotlin()

        //转义器自动转义
        test.`is`()
    }

    //包级方法和属性的引用
    fun testTopMethodAndAttribute() {

        str.length

        WatchActivity().newIntent<WatchActivity>()
    }

    infix fun Int.addasdf(x: Int): Int {
        return this + x
    }

    fun testZhongZhui() {

        //中缀表示法

        val add = 2.addasdf(3)
        val i = 2 addasdf 3
        print("the value is $add  $i")
    }


}