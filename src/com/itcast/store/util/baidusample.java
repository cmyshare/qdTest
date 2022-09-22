package com.itcast.store.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

public class baidusample {

    public static String sample(AipOcr client,byte[] file) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");


//	    // 参数为本地图片路径
//	    String image = "test.jpg";
//	    JSONObject res = client.basicGeneral(image, options);
//	    System.out.println(res.toString(2));
//
//	    // 参数为本地图片二进制数组
//	    byte[] file = readImageFile(image);
//	    JSONObject res = client.basicGeneral(file, options);
//	    System.out.println(res.toString(2));


        // 通用文字识别, 图片参数为远程url图片
//	    JSONObject res = client.basicGeneralUrl(url, options);
//	    System.out.println(res.toString());


        //图片二进制数组  everyDay方法
        //JSONObject res = client.basicGeneral(file, options);
        //System.out.println("输出内容："+res.toString());

        //高精度
        JSONObject res = client.basicAccurateGeneral(file, options);
        System.out.println(res.toString(2));
        return  res.toString();
    }

    public static String sample2(AipOcr client,String imageUrl) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_language", "true");

        // 网络图片文字识别, 图片参数为远程url图片
        JSONObject res = client.webImageUrl(imageUrl, options);
        System.out.println(res.toString(2));

        return  res.toString(2);
    }


    public static void main(String[] args) {
        AipOcr client=new AipOcr("24024258", "pPzkrWG8vIbgwREcvIgHTDD1", "QEWj4w6fkGAqYVcS6U7l7izT2rBfW5G8");
        String yzcode= baidusample.sample2(client, "https://sso.vpn.ccibe.edu.cn/cas/captcha.jpg");
        System.out.println(yzcode);
    }

}
