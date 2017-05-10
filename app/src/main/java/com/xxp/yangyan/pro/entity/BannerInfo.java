package com.xxp.yangyan.pro.entity;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description :
 */

public class BannerInfo {
    private String content;
    private String imageUrl;
    /**
     * html : false
     */

    private boolean html;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "BannerInfo{" +
                "content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }
}
