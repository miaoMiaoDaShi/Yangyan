package com.xxp.yangyan.pro.entity;

import java.util.List;

/**
 * Created by 钟大爷 on 2017/2/3.
 */

public class HomeData extends DataBean {

    /**
     * banner : ["image/banner_1_20170507.png","image/banner_2_20170507.png","image/banner_3_20170507.png","image/banner_4_20170507.png","image/banner_5_20170507.png"]
     * banner_link : ["http://m.xxxiao.com/133334","http://m.xxxiao.com/120213","http://m.xxxiao.com/133334","http://m.xxxiao.com/133334","http://m.xxxiao.com/133334"]
     * bannerLink : ["image//web/banner/banner_1.html","image//web/banner/banner_1.html","image//web/banner/banner_1.html","image//web/banner/banner_1.html"]
     * push : ["image/push_1_20170507.png","image/push_2_20170507.png","image/push_3_20170507.png"]
     * dongman : ["image/dongman_1_20170507.png","image/dongman_2_20170507.png","image/dongman_3_20170507.png","image/dongman_4_20170507.png"]
     * notice : 请大家及时反馈,使用中遇到的问题!如果你有什么意见或建议欢迎评论！
     * notUseVersion : 1.0
     */

    private String notice;
    private String notUseVersion;
    private List<String> banner;
    private List<String> banner_link;
    private List<String> bannerLink;
    private List<String> push;
    private List<String> dongman;

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getNotUseVersion() {
        return notUseVersion;
    }

    public void setNotUseVersion(String notUseVersion) {
        this.notUseVersion = notUseVersion;
    }

    public List<String> getBanner() {
        return banner;
    }

    public void setBanner(List<String> banner) {
        this.banner = banner;
    }

    public List<String> getBanner_link() {
        return banner_link;
    }

    public void setBanner_link(List<String> banner_link) {
        this.banner_link = banner_link;
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

    public List<String> getDongman() {
        return dongman;
    }

    public void setDongman(List<String> dongman) {
        this.dongman = dongman;
    }

    @Override
    public String toString() {
        return "HomeData{" +
                "notice='" + notice + '\'' +
                ", notUseVersion='" + notUseVersion + '\'' +
                ", banner=" + banner +
                ", banner_link=" + banner_link +
                ", bannerLink=" + bannerLink +
                ", push=" + push +
                ", dongman=" + dongman +
                '}';
    }
}
