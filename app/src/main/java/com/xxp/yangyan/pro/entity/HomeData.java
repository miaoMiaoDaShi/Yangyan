package com.xxp.yangyan.pro.entity;

import java.util.List;

/**
 * Created by 钟大爷 on 2017/2/3.
 */

public class HomeData extends DataBean {

    /**
     * banner : ["banner/banner1.jpg","banner/banner2.jpg","banner/banner3.jpg","banner/banner4.jpg"]
     * bannerLink : ["/web/banner/banner1.html","/web/banner/banner1.html","/web/banner/banner1.html","/web/banner/banner1.html"]
     * push : ["image/push1.png","image/push2.png","image/push3.png"]
     * notice : 小小看图软件谢谢大家的支持,我将不断完善此软件,友友们尽情享用吧,啦啦啦啦
     */

    private String notice;
    private List<String> banner;
    private List<String> bannerLink;
    private List<String> push;
    private String notUseVersion;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotUseVersion() {
        return notUseVersion;
    }

    public void setNotUseVersion(String notUseVersion) {
        this.notUseVersion = notUseVersion;
    }

    @Override
    public String toString() {
        return "HomeData{" +
                "notice='" + notice + '\'' +
                ", banner=" + banner +
                ", bannerLink=" + bannerLink +
                ", push=" + push +
                '}';
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public List<String> getBanner() {
        return banner;
    }

    public void setBanner(List<String> banner) {
        this.banner = banner;
    }

    public List<String> getBannerLink() {
        return bannerLink;
    }

    public void setBannerLink(List<String> bannerLink) {
        this.bannerLink = bannerLink;
    }

    public List<String> getPush() {
        return push;
    }

    public void setPush(List<String> push) {
        this.push = push;
    }
}
