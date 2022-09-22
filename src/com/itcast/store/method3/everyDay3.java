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
import org.openqa.selenium.interactions.Actions;

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

import static com.itcast.store.method3.SkipCertificateValidation.ignoreSsl;

/**
 * @author cmy
 * @version 3.0
 * @date 2022/1/22 21:40
 * @description 打卡功能类
 */

public class everyDay3 {
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
            ignoreSsl(); //绕过https
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //添加请求头
            con.setRequestProperty("authority", "sso.vpn.ccibe.edu.cn");
            con.setRequestProperty("method", "GET");
            con.setRequestProperty("path", "/cas/login?service=https://my.vpn.ccibe.edu.cn/api/login");
            con.setRequestProperty("scheme", "https");
            con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            con.setRequestProperty("accept-encoding", "gzip, deflate, br");
            con.setRequestProperty("accept-language", "zh-CN,zh;q=0.9");
            con.setRequestProperty("cache-control", "max-age=0");
            con.setRequestProperty("cookie", "UM_distinctid=17e66837ef4126-0533f7dde565b3-f791b31-100200-17e66837ef5718;"+cooks+"");
            con.setRequestProperty("sec-ch-ua", "\" Not;A Brand\";v=\"99\", \"Google Chrome\";v=\"97\", \"Chromium\";v=\"97\"");
            con.setRequestProperty("sec-ch-ua-mobile", "?0");
            con.setRequestProperty("sec-ch-ua-platform", "Windows");
            con.setRequestProperty("sec-fetch-dest", "document");
            con.setRequestProperty("sec-fetch-mode", "navigate");
            con.setRequestProperty("sec-fetch-site", "none");
            con.setRequestProperty("sec-fetch-user", "?1");
            con.setRequestProperty("sec-fetch-user", "1");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");

            con.setRequestMethod("GET");
            con.setConnectTimeout(5 * 1000);
            InputStream inStream = con.getInputStream();// 通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据

//            //二进制转化为图片 存储
//            try (FileOutputStream fileOutputStream = new FileOutputStream(new File("F:\\intellij2020-1\\idea\\qdTest\\images\\code.jpg"));) {
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
        System.setProperty("webdriver.chrome.driver","F:\\专业实战\\开发工具\\WEB开发\\chromedriver.exe");

        //linux环境
        //System.setProperty("webdriver.chrome.driver","/usr/cmy/chromedriver");

        ChromeOptions chromeOptions = new ChromeOptions();
        // 禁用沙箱
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--headless");

        driver = new ChromeDriver(chromeOptions);
        System.out.println("开始打卡"+df.format(new Date()));

        //获取测试目标路径 http://my.ccibe.edu.cn/
        driver.get("http://my.ccibe.edu.cn/");

        //通过元素属性id=su找到百度一下搜索按钮，并对按钮进行点击操作；
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);

        //http://alst.swsm.edu.cn/Vcode.ASPX
        String imageurl=driver.findElement(By.id("captcha_img")).getAttribute("src");// 图片的链接地
        //System.out.println("图片链接："+imageurl);

//        //输出cooike
//        for(Cookie ck : driver.manage().getCookies())
//        {
//            System.out.println((ck.getName()+";"+ck.getValue()+";"+ck.getDomain()+";"+ck.getPath()+";"+ck.getExpiry()+";"+ck.isSecure()));
//
//        }
//        System.out.println("aaaa"+driver.manage().getCookies());
//        System.out.println("aaaa"+driver.manage().getCookieNamed("UM_distinctid"));
//        System.out.println("aaaa"+driver.manage().getCookieNamed("JSESSIONID"));

        //调用获取验证码方法
        byte[] btImgs=getImageFromNetByUrl(imageurl, String.valueOf(driver.manage().getCookieNamed("JSESSIONID")));
//        System.out.println("二进制数据："+btImgs);

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
            System.out.println("验证码:"+s1);

            //输入验证码
            driver.findElement(By.id("captcha")).sendKeys(s1);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //获取测试目标组件-button 登录
        driver.findElement(new By.ByClassName("inputBtn")).click();
        System.out.println("登录成功");

        //登录成功过后，进入工学系统。
        List<WebElement> elements1 = driver.findElements(By.cssSelector("button span"));
        for (WebElement element : elements1) {
            //System.out.println("Paragraph text1:" + element.getText());
            if (element.getText().equals("推荐服务")){
                Actions action = new Actions(driver);
                action.moveToElement(element).click().perform(); //鼠标移动到某个元素上
//                System.out.println("推荐服务");
                break;
            }
        }


        //点击学工系统
        List<WebElement> elements2 = driver.findElements(By.tagName("p"));
        for (WebElement element : elements2) {
            //System.out.println("Paragraph text2:" + element.getText());
            if (element.getText().equals("学工系统")){
                // 创建actions对象
                Actions action = new Actions(driver);
                action.moveToElement(element).click().perform(); //鼠标移动到某个元素上
//                System.out.println("点击学工系统，跳转到奥兰系统");
                break;
            }
        }

        //处理系统渲染速度慢问题 1
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        System.out.println("新窗口1"+driver.getCurrentUrl());     //打印是否为新窗口

        //获取学工系统页面的窗口权柄，给driver
        String FirstHandle = driver.getWindowHandle();     //首先得到最先的窗口 权柄
        for(String winHandle : driver.getWindowHandles()) {    //得到浏览器所有窗口的权柄为Set集合，遍历
            if (winHandle.equals(FirstHandle)) {				//如果为 最先的窗口 权柄跳出
                continue;
            }
            driver.switchTo().window(winHandle);             //如果不为 最先的窗口 权柄，将 新窗口的操作权柄  给 driver
//            System.out.println("新窗口2"+driver.getCurrentUrl());     //打印是否为新窗口
//            System.out.println(winHandle);
            break;
        }

        //开启奥兰系统打卡操作
        List<WebElement> links = driver.findElements(By.cssSelector("span a"));
        for(int i=0; i<links.size();i++){
            WebElement aboutLink = driver.findElement(By.linkText("事务"));
            aboutLink.click();
        }

        //处理系统渲染速度慢问题 2
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement aboutLink = driver.findElement(By.linkText("新冠健康档案（第三版）"));
        aboutLink.click();

//        System.out.println("学工地址1"+driver.getCurrentUrl());
        String SecondHandle=driver.getWindowHandle(); //学工窗口

        // 点击在新窗口中打开的链接  打开体温窗口
        WebElement element1 =driver.switchTo().frame("r_3_3").findElement(By.id("twsfzc"));
        element1.click();

        //处理系统渲染速度慢问题 3
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 循环执行，直到找到一个新的窗口句柄
        for (String windowHandle : driver.getWindowHandles()) {
            //处理系统渲染速度慢问题 4
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("新地址"+windowHandle);
            if(!SecondHandle.contentEquals(windowHandle) && !FirstHandle.contentEquals(windowHandle)) {
//                System.out.println("学工体温是否驱动号"+windowHandle);
                driver.switchTo().window(windowHandle).findElement(By.id("MyDataGrid__ctl3_mc")).click();
                break;
            }
        }

//        System.out.println("学工"+SecondHandle);
        //切回到之前的标签页或窗口
        driver.switchTo().window(SecondHandle);
//        System.out.println("学工地址"+driver.getCurrentUrl());
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
                String User[][] = {{"20180784008", "671177Abc"},{"500226200106130321", "130321"},{"20180784004", "19409X"},{"20190584036", "273548"}};
                //String User[][] = {{"20180784008", "671177Abc"}};
                for (int i=0;i<User.length;i++){
                    //System.out.println(User[i][0]+User[i][1]);
                    while (true){
                        try {
                            login(User[i][0],User[i][1]);
                            System.out.println("用户——>"+User[i][0]);
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
