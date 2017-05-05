package com.xxp.yangyan.pro.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by 钟大爷 on 2017/2/4.
 */
@Entity
public class ImageInfo extends DataBean{
    //分类
    private String catalogue;
    //标题
    private String title;
    //图片的链接
    @Id
    private String imgUrl;
    //点击点击
    private String link;


    public ImageInfo() {
    }

    @Generated(hash = 1856442733)
    public ImageInfo(String catalogue, String title, String imgUrl, String link) {
        this.catalogue = catalogue;
        this.title = title;
        this.imgUrl = imgUrl;
        this.link = link;
    }

    public String getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(String catalogue) {
        this.catalogue = catalogue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
