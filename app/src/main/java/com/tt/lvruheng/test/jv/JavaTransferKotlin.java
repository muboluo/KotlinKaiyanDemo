package com.tt.lvruheng.test.jv;

import android.util.Log;

import com.tt.lvruheng.test.kt.KotlinTest;

/**
 * Created by henry on 2018/10/25.
 * java 调用 kotlin 测试类
 */

public class JavaTransferKotlin {

    private final String TAG = JavaTransferKotlin.class.getSimpleName();

    private FiledTest test;

    public FiledTest getTest() {
        return test;
    }

    public void setTest(FiledTest test) {

        this.test = test;
    }

    public void is() {

        // 测试kotlin is 方法
    }

    public void getKotlinField() {

        KotlinTest kotlinTest = new KotlinTest();
        String grade = kotlinTest.getGrade();
        Log.e(TAG, "getKotlinField: " + grade);

        String height = KotlinTest.Companion.getHEIGHT();

        String weight = KotlinTest.WEIGHT;


        kotlinTest.ktMethodTest();

        KotlinTest.staticTest();//该方法会调用下面这个方法，所以是一样的
        KotlinTest.Companion.staticTest();
    }

    public static class FiledTest {

        public String field1;
    }

}
