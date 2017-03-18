package com.xxp.yangyan.pro.bean;

/**
 * Created by 钟大爷 on 2017/2/4.
 */

public class BannerInfo {
    //轮播图显示的图片
    private String imgUrl;
    //轮播图的点击的连接
    private String link;

    public BannerInfo() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
