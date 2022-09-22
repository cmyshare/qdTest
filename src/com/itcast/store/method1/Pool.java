package com.itcast.store.method1;

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
        //NewTest newTest = new NewTest("500382199711015613","671177Abc");
//        newTest.setUsername("500382199711015613");
//        newTest.setPassword("671177Abc");

//        NewTest newTest = new NewTest();
//        newTest.userpass("500382199711015613","671177Abc");

        //执行打卡功能类
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
