package com.itcast.store.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class DownloadPicFromURL {
//	 public static void main(String[] args) {
//	        String url = "http://alst.swsm.edu.cn/Vcode.ASPX";
//	        String path="C:/Users/Administrator/Desktop/Vcode.png";
//	        downloadPicture(url,path);
//	    }
//	    //链接url下载图片
//	    private static void downloadPicture(String urlList,String path) {
//	        URL url = null;
//	        try {
//	            url = new URL(urlList);
//	            DataInputStream dataInputStream = new DataInputStream(url.openStream());
//
//	            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
//	            ByteArrayOutputStream output = new ByteArrayOutputStream();
//
//	            byte[] buffer = new byte[1024];
//	            int length;
//
//	            while ((length = dataInputStream.read(buffer)) > 0) {
//	                output.write(buffer, 0, length);
//	            }
//	            fileOutputStream.write(output.toByteArray());
//	            dataInputStream.close();
//	            fileOutputStream.close();
//	        } catch (MalformedURLException e) {
//	            e.printStackTrace();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    }


//	  public static void download(String urlString, String i) throws Exception {
//	       // 构造URL
//	       URL url = new URL(urlString);
//	       // 打开连接
//	       URLConnection con = url.openConnection();
//	       // 输入流
//	       InputStream is = con.getInputStream();
//	       // 1K的数据缓冲
//	       byte[] bs = new byte[4096];
//	       // 读取到的数据长度
//	       int len;
//	       // 输出的文件流 http://localhost:8080/mystore1/img/
//	       String filename = "C:/Users/Administrator/Desktop/" + i + ".png";  //下载路径及下载图片名称
//	       File file = new File(filename);
//	       FileOutputStream os = new FileOutputStream(file, true);
//	       // 开始读取
//	       while ((len = is.read(bs)) != -1) {
//	           os.write(bs, 0, len);
//	       }
//	       System.out.println(i);
//	       // 完毕，关闭所有链接
//	       os.close();
//	       is.close();
//	   }


    //截图
    public static byte[] takeScreenshot(WebDriver driver) throws IOException {
//		 byte[] screenshot = null;
//		 screenshot = ((TakesScreenshot) driver)
//		 .getScreenshotAs(OutputType.BYTES);//得到截图
//		 return screenshot;

        //创建全屏截图
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage image = ImageIO.read(screen);

        int init_width = image.getWidth();
        int init_height = image.getHeight();

        System.out.println(init_width+"速度"+init_height);

        ByteArrayOutputStream outStream =new ByteArrayOutputStream();
        //把outStream里的数据写入内存
        ImageIO.write(image, "JPEG", outStream);


        return outStream.toByteArray();
    }

    public static byte[] createElementImage(WebDriver driver,
                                            WebElement webElement, int x, int y, int width, int heigth)//开始裁剪的位置和截图的宽和高
            throws IOException {
        Dimension size = webElement.getSize();
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(
                takeScreenshot(driver)));

        int init_width = originalImage.getWidth();
        int init_height = originalImage.getHeight();

        System.out.println(init_width+"速度"+init_height);

        BufferedImage croppedImage = originalImage.getSubimage(x, y,
                size.getWidth() + width, size.getHeight() + heigth);//进行裁剪


        ByteArrayOutputStream outStream =new ByteArrayOutputStream();
        //把outStream里的数据写入内存
        ImageIO.write(croppedImage, "JPEG", outStream);

        //返回二进制数组
        return outStream.toByteArray();

        //return croppedImage;
    }



//	public  static byte[] getImageBinary(String path, String imgType) {
//		File f = new File(path);
//		BufferedImage bi;
//		try {
//			bi = ImageIO.read(f);
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ImageIO.write(bi,imgType, baos);  //经测试转换的图片是格式这里就什么格式，否则会失真
//			byte[] bytes = baos.toByteArray();
//
//			return bytes;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//
//}



}
