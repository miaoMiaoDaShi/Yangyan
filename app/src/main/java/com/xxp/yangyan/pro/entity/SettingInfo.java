package com.xxp.yangyan.pro.entity;

/**
 * Created by 钟大爷 on 2017/2/4.
 */

public class SettingInfo {
    private int image;
    private String title;
    private String cacheCount;

    public String getCacheCount() {
        return cacheCount;
    }

    public void setCacheCount(String cacheCount) {
        this.cacheCount = cacheCount;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
