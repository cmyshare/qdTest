package com.itcast.store.test;

import org.testng.annotations.Test;

import com.baidu.aip.ocr.AipOcr;
import com.itcast.store.util.DownloadPicFromURL;
import com.itcast.store.util.baidusample;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.testng.annotations.BeforeMethod;

import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sound.midi.Soundbank;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver.WindowType;
import org.testng.annotations.AfterMethod;

public class NewTest {
    WebDriver driver;

    @Test
    public void f() {
        //浏览器最大化
        //driver.manage().window().maximize();

        //获取测试目标路径
        driver.get("http://alst.swsm.edu.cn/login.aspx");

        ///////通过元素属性id=su找到百度一下搜索按钮，并对按钮进行点击操作；
        driver.findElement(By.id("userbh")).sendKeys("你的账号");
        driver.findElement(By.id("pas1s")).sendKeys("你的密码");

        //http://alst.swsm.edu.cn/Vcode.ASPX
        String imageurl=driver.findElement(By.id("Image1")).getAttribute("src");// 图片的链接地
        System.out.println("图片链接："+imageurl);

        //WebElement webElement=driver.findElement(By.id("Image1"));// 图片的链接地


        byte[] yzimg = null;
        try {
            yzimg = DownloadPicFromURL.takeScreenshot(driver);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //调用百度接口
        AipOcr client=new AipOcr("24024258", "pPzkrWG8vIbgwREcvIgHTDD1", "QEWj4w6fkGAqYVcS6U7l7izT2rBfW5G8");
        String yzcode=baidusample.sample(client, yzimg);

        JSONObject jsonObject = null;

        JSONArray array=null;

        JSONObject jsonObj1=null;

        try {
            jsonObject = new JSONObject(yzcode);

            array=(JSONArray) jsonObject.get("words_result");

            jsonObj1 = array.getJSONObject(array.length()-1);

            String s1=(String) jsonObj1.get("words");

            System.out.println(s1);

            //输入验证码
            driver.findElement(By.id("vcode")).sendKeys(s1);


//			//JSONArray遍历
//			for(int i=0; i<array.length(); i++) {
//				JSONObject jsonObj = array.getJSONObject(i);
//				System.out.println("迭代输出"+jsonObj.toString());
//			}

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //获取测试目标组件-button 登录
        driver.findElement(By.id("save2")).click();

        List<WebElement> links = driver.findElements(By.cssSelector("span a"));
        for(int i=0; i<links.size();i++){
            WebElement aboutLink = driver.findElement(By.linkText("事务"));
            aboutLink.click();
        }

        WebElement aboutLink = driver.findElement(By.linkText("新冠健康档案（第三版）"));
        aboutLink.click();




        // 存储原始窗口的 ID
        String originalWindow = driver.getWindowHandle();

        // 检查一下，我们还没有打开其他的窗口
        assert driver.getWindowHandles().size() == 1;

        // 点击在新窗口中打开的链接
//            	 driver.findElement(By.linkText("new window")).click();
        WebElement element1 =driver.switchTo().frame("r_3_3").findElement(By.id("twsfzc"));
        element1.click();

//            	 // 等待新窗口或标签页
//            	 wait.until(numberOfWindowsToBe(2));

        // 循环执行，直到找到一个新的窗口句柄
        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {

                driver.switchTo().window(windowHandle).findElement(By.id("MyDataGrid__ctl3_mc")).click();
                break;
            }
        }

        //切回到之前的标签页或窗口
        driver.switchTo().window(originalWindow);

        WebElement element3 =driver.switchTo().frame("r_3_3").findElement(By.id("databc"));
        element3.click();

        System.out.println("签到成功");

    }
    @BeforeMethod
    public void beforeMethod() {
        //获取WebDriver的储存路径
        System.setProperty("webdriver.chrome.driver","F:/专业实战/开发工具/WEB开发/chromedriver.exe");

        driver = new ChromeDriver();
    }

    @AfterMethod
    public void afterMethod() {
        //关闭打开的浏览器
        driver.quit();
    }



}
