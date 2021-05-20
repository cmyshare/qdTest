package com.itcast.store.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;;

public class Testss {
    public WebDriver driver;
    String baseUrl="http://www.baidu.com";//设定访问网站的地址

    @Test
    public void testSearch() {
        //打开baidu首页
        driver.get(baseUrl+"/");
        ///////通过元素属性id=kw找到百度输入框，并输入"Selenium java"；
        driver.findElement(By.id("kw")).sendKeys(new String[]{"Selenium java"});
        ///////通过元素属性id=su找到百度一下搜索按钮，并对按钮进行点击操作；
        driver.findElement(By.id("su")).click();
    }

    @BeforeMethod
    public void beforeMethod() {
        //若无法打开Chrome浏览器，可设定Chrome浏览器的安装路径
        System.setProperty("webdriver.chrome.driver","F:/专业实战/开发工具/WEB开发/chromedriver.exe");
        //打开Chrome浏览器
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void afterMethod() {
        //关闭打开的浏览器
        driver.quit();
    }

}