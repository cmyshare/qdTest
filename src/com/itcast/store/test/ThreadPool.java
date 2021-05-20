package com.itcast.store.test;

import org.testng.TestNG;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//多线程定时调用，可以把Jar放在服务器里，直接运行Jar。
public class ThreadPool {
    private static final ScheduledExecutorService executor = new
            ScheduledThreadPoolExecutor(1, Executors.defaultThreadFactory());

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args){
        // 新建一个固定延迟时间的计划任务
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (haveMsgAtCurrentTime()) {
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
            }
        }, 1, 60*60*24, TimeUnit.SECONDS);  //60*60*24 定时：24小时打开一次
    }

    public static boolean haveMsgAtCurrentTime(){
        //查询数据库，有没有当前时间需要发送的消息
        //这里省略实现，直接返回true
        return true;
    }
}