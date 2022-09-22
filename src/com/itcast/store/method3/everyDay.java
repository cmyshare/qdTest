package com.itcast.store.method3;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author cmy
 * @version 2.0
 * @date 2022/1/22 21:40
 * @description 打卡功能类
 */

public class everyDay {
    public static WebDriver driver;

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    //登录类
    public static void login(String username,String password){
        //获取WebDriver的储存路径 F:/专业实战/开发工具/WEB开发/chromedriver.exe
        //System.setProperty("webdriver.chrome.driver","F:/专业实战/开发工具/WEB开发/chromedriver.exe");
        //linux环境
        System.setProperty("webdriver.chrome.driver","/usr/cmy/chromedriver");

        ChromeOptions chromeOptions = new ChromeOptions();
        // 禁用沙箱
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--headless");

        driver = new ChromeDriver(chromeOptions);


        System.out.println("开始打卡"+df.format(new Date()));

        //获取测试目标路径
        driver.get("http://alst.ccibe.edu.cn/LOGIN.ASPX");


        ///////通过元素属性id=su找到百度一下搜索按钮，并对按钮进行点击操作；
//        System.out.println("6666666"+username1);
        driver.findElement(By.id("userbh")).sendKeys(username);
        driver.findElement(By.id("pas1s")).sendKeys(password);

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
        String yzcode= baidusample.sample(client, btImgs);

        JSONObject jsonObject = null;

        JSONArray array=null;

        JSONObject jsonObj1=null;


        //老json遍历
        try {
            jsonObject = new JSONObject(yzcode);

            array=(JSONArray) jsonObject.get("words_result");

            jsonObj1 = array.getJSONObject(array.length()-1);

            String s1=(String) jsonObj1.get("words");

            System.out.println("验证码"+s1);

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


        //登录成功过后，进行打卡的操作
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
        driver.quit();


    }

    //多线程定时调用，可以把Jar放在服务器里，直接运行Jar。
    private static final ScheduledExecutorService executor = new
            ScheduledThreadPoolExecutor(1, Executors.defaultThreadFactory());

    public static void main(String[] args) {

        // 新建一个固定延迟时间的计划任务
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                //加一个循环，多个人登录
                String User[][] = {{"500382199711015613", "671177Abc"},{"500226200106130321", "123456789dzy"},{"20190584036", "211446"},{"20180784004","woaini0819"}};
                for (int i=0;i<User.length;i++){
                    //System.out.println(User[i][0]+User[i][1]);
                    while (true){
                        try {
                            login(User[i][0],User[i][1]);
                            break;

                        } catch (Exception e) {
                            e.printStackTrace();
                            driver.quit();
                            continue;
                        }
                    }
                }
            }
        }, 1, 60*60*24, TimeUnit.SECONDS);  //60*60*24 定时：24小时打开一次
    }


}
