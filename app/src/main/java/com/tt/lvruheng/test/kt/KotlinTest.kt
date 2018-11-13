package com.tt.lvruheng.test.kt

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

    }

    fun testIF(x: Int): Int {

        return if (x > 10) {
            1
        } else {
            2
        }
    }
}