package com.xxp.yangyan.pro.bean;

/**
 * Created by 钟大爷 on 2017/2/8.
 */

public class SplashInfo extends DataBean {
    /**
     * date : 20170208
     * splashUrl : http://192.168.191.1/yangyan/splash.png
     */

    private String date;
    private String splashUrl;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSplashUrl() {
        return splashUrl;
    }

    public void setSplashUrl(String splashUrl) {
        this.splashUrl = splashUrl;
    }
}
