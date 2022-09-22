package com.itcast.store.method3;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author cmy
 * @version 1.0
 * @date 2022/1/22 21:40
 * @description 百度文字识别接口类
 */

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


//        //图片二进制数组
//        JSONObject res = client.basicGeneral(file, options);
//        //System.out.println("输出内容："+res.toString());

        //高精度图片二进制数组
        JSONObject res = client.basicAccurateGeneral(file, options);
//        System.out.println(res.toString(2));
        return  res.toString();

    }

}
