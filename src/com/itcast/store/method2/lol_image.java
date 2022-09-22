//package com.itcast.store.test;
//
//
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.*;
//import java.net.URL;
//
///**
// * 爬取王者荣耀英雄图片
// */
//
//public class LOL {
//    public static void main(String[] args) throws IOException {
//        //Http请求
//        Connection conn = Jsoup.connect("https://pvp.qq.com/web201605/herolist.shtml");
//        //网页文档
//        Document doc = conn.get();
//        //System.out.println(doc.toString());
//        Element elementUL = doc.selectFirst("[class=herolist clearfix]");
//        //System.out.println(elementUL);
//        Elements elementsLi = elementUL.select("li");
//        //循环遍历 取li
//        for (Element elementLi : elementsLi) {
//            Element elementA = elementLi.selectFirst("a");
//            String pickName = elementA.text();
//            System.out.println(pickName);
//            //爬虫继续前往下一个结点爬取
//            String attrHref = elementA.attr("href");
//            String path = "https://pvp.qq.com/web201605/" + attrHref;
//            //进入到path路径下的网页，下载图片
//            getPic(path, pickName);
//        }
//    }
//
//    /**
//     * 下载图片
//     *
//     * @param path
//     * @param pickName
//     */
//    private static void getPic(String path, String pickName) throws IOException {
//        Connection conn = Jsoup.connect(path);
//        Document doc = conn.get();
//        Element elementDiv = doc.selectFirst("[class=zk-con1 zk-con]");
//        String attrStyle = elementDiv.attr("style");
//        //background:url('//game.gtimg.cn/images/yxzj/img201606/skin/hero-info/167/167-bigskin-1.jpg') center 0
//        //截取字符串
//        int start = attrStyle.indexOf("'//") + 3;
//        int end = attrStyle.lastIndexOf("'");
//        //截取后，game.gtimg.cn/images/yxzj/img201606/skin/hero-info/167/167-bigskin-1.jpg
//        //String substring = attrStyle.substring(start, end);
//        URL url = new URL("https://" + attrStyle.substring(start, end));
//        //下载网络图片到本地
//        //IO 输入流 读取
//        BufferedInputStream in = null;
//        BufferedOutputStream out = null;
//        try {
//            in = new BufferedInputStream(url.openStream());
//            out = new BufferedOutputStream(new FileOutputStream(new File("F:\\intellij2020-1\\idea\\qdTest\\images\\" + pickName + ".PNG")));
//            byte[] b = new byte[1024];
//            int len = -1;
//            while ((len = in.read(b, 0, 1024)) != -1) {
//                out.write(b, 0, len);
//                out.flush();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            in.close();
//            out.close();
//        }
//
//    }
//
//}
