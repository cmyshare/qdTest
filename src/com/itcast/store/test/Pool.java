package com.itcast.store.test;

import org.testng.TestNG;

import java.text.SimpleDateFormat;
import java.util.Date;


//普通调用，可以放在windows定时任务中，定时执行，执行成功自动关闭。
public class Pool {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void run(){
        System.out.println(df.format(new Date()));
        System.out.println("开始打卡！");
        TestNG testNG = new TestNG();
        Class[] classes = {NewTest.class};
        testNG.setTestClasses(classes);
        testNG.run();
        //捕捉异常，递归调用
        if (testNG.hasFailure()){
            run();
        }
    }
    public static void main(String[] args) {
        Pool.run();
    }
}
