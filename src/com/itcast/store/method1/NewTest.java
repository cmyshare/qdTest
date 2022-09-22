package com.itcast.store.method1;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.*;
import org.testng.annotations.Test;

import com.baidu.aip.ocr.AipOcr;
import com.itcast.store.util.DownloadPicFromURL;
import com.itcast.store.util.baidusample;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.testng.annotations.BeforeMethod;

import java.awt.Window;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sound.midi.Soundbank;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver.WindowType;
import org.testng.annotations.AfterMethod;

public class NewTest {
    WebDriver driver;

    // 得到图片的二进制数据
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    //请求验证码  传入地址、cookie
    public static byte[] getImageFromNetByUrl(String strUrl,String cooks) {
//        System.out.println(cooks);
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //添加请求头
            conn.setRequestProperty("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Cookie", "UM_distinctid=17e0e2d492d6b6-0097ed4d42e2ec-978153c-100200-17e0e2d492e9a9; "+cooks+"");
            conn.setRequestProperty("Host", "alst.ccibe.edu.cn");
            conn.setRequestProperty("Referer", "http://alst.ccibe.edu.cn/LOGIN.ASPX");
            conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36");

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据

//            //二进制转化为图片 存储
//            try (FileOutputStream fileOutputStream = new FileOutputStream(new File("F:\\intellij2020-1\\idea\\qdTest\\images\\code.Jpeg"));) {
//                fileOutputStream.write(btImg);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Test
    public void f() {
//        String data[][] = {{"500382199711015613", "671177Abc"}, {"20190584006", "983464yh"}};
//        for (int x = 0; x <= data.length - 1; x++) {
//            System.out.println(data[x][0]);
//            System.out.println(data[x][1]);
//        }
        //浏览器最大化
//        driver.manage().window().maximize();

        //获取测试目标路径
        driver.get("http://alst.ccibe.edu.cn/LOGIN.ASPX");


        ///////通过元素属性id=su找到百度一下搜索按钮，并对按钮进行点击操作；
//        System.out.println("6666666"+username1);
        driver.findElement(By.id("userbh")).sendKeys("500382199711015613");
        driver.findElement(By.id("pas1s")).sendKeys("671177Abc");

        //http://alst.swsm.edu.cn/Vcode.ASPX
        String imageurl=driver.findElement(By.id("Image1")).getAttribute("src");// 图片的链接地
        //System.out.println("图片链接："+imageurl);

        //输出cooike
//        for(Cookie ck : driver.manage().getCookies())
//        {
//            System.out.println((ck.getName()+";"+ck.getValue()+";"+ck.getDomain()+";"+ck.getPath()+";"+ck.getExpiry()+";"+ck.isSecure()));
//
//        }
//        System.out.println("aaaa"+driver.manage().getCookies());
//        System.out.println("aaaa"+driver.manage().getCookieNamed("UM_distinctid"));
//        System.out.println("aaaa"+driver.manage().getCookieNamed("ASP.NET_SessionId"));


        //调用获取验证码方法
        byte[] btImgs=getImageFromNetByUrl(imageurl, String.valueOf(driver.manage().getCookieNamed("ASP.NET_SessionId")));
//        System.out.println("二进制数据："+btImgs);

        //WebElement webElement=driver.findElement(By.id("Image1"));// 图片的链接地

//         //老方法截图
//        byte[] yzimg = null;
//        try {
//            yzimg = DownloadPicFromURL.takeScreenshot(driver);  //验证码截图
//            System.out.println(yzimg);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        //调用百度接口
        AipOcr client=new AipOcr("24024258", "pPzkrWG8vIbgwREcvIgHTDD1", "QEWj4w6fkGAqYVcS6U7l7izT2rBfW5G8");
        String yzcode=baidusample.sample(client, btImgs);

        JSONObject jsonObject = null;

        JSONArray array=null;

        JSONObject jsonObj1=null;


        //老json遍历
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
        //获取WebDriver的储存路径 F:/专业实战/开发工具/WEB开发/chromedriver.exe
        System.setProperty("webdriver.chrome.driver","F:/专业实战/开发工具/WEB开发/chromedriver.exe");
        //System.setProperty("webdriver.chrome.driver","/usr/cmy/chromedriver");
        driver = new ChromeDriver();


    }

    @AfterMethod
    public void afterMethod() {
        //关闭打开的浏览器
        driver.quit();
    }



}
