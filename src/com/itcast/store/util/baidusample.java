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
//	    res = client.basicGeneral(file, options);
//	    System.out.println(res.toString(2));


        // 通用文字识别, 图片参数为远程url图片
//	    JSONObject res = client.basicGeneralUrl(url, options);
//	    System.out.println(res.toString());

        JSONObject res = client.basicGeneral(file, options);
        System.out.println("输出内容："+res.toString());


        return  res.toString();
    }

}
