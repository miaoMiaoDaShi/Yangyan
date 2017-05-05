package com.xxp.yangyan.pro.api;

import com.xxp.yangyan.pro.entity.ImageInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 网页数据的解析从HTMLString 到ImageInfo的集合对象,解析器:jsoup
 */

public class AnalysisHTML {
    //获取主页的指定内容
    public static List<ImageInfo> HomePageToList(String content){
        List<ImageInfo> images = new ArrayList<>();
        try {
            Document document = Jsoup.parse(content);
            Elements elements = document.select("div.post-thumb");
            for (Element element : elements) {
                String catalogue = element.select("header").select("div").select("span").text();
                String title = element.select("header").select("h2").select("a").text();
                String link = element.select("a").attr("href");
                String imgUrl = element.select("img").attr("src");

                ImageInfo image = new ImageInfo();
                image.setTitle(title);
                image.setCatalogue(catalogue);
                image.setImgUrl(imgUrl);
                image.setLink(link.replace(XxxiaoApi.MEINV_BASE_URL, ""));

                images.add(image);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }
    public static List<ImageInfo> ParticularsToList(String content, String id){
        List<ImageInfo> images = new ArrayList<>();
        try {
            Document document = Jsoup.parse(content);
            Elements elements = document.select("div#page").select("div#content").select("div.container")
                    .select("div.row").select("div#primary").select("main#main").select("article#post-"+id)
                    .select("div.entry-content").select("div.rgg-imagegrid").select("a");
            for (Element element : elements) {
                String imgUrl = element.attr("href");
                String imgDisplay = element.select("img").attr("src");
                ImageInfo image = new ImageInfo();
                image.setImgUrl(imgUrl);
                images.add(image);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }
}
