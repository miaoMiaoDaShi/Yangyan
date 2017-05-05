package com.xxp.yangyan.pro.entity;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description :
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
