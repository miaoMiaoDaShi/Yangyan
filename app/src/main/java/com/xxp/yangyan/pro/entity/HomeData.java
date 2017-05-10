package com.xxp.yangyan.pro.entity;

import java.util.List;

/**
 * Created by 钟大爷 on 2017/2/3.
 */

public class HomeData extends DataBean {

    /**
     * bannerInfo : [{"content":"http://m.xxxiao.com/133334","imageUrl":"image/banner_1_20170507.png"},{"content":"http://m.xxxiao.com/133334","imageUrl":"image/banner_2_20170507.png"},{"content":"http://m.xxxiao.com/133334","imageUrl":"image/banner_3_20170507.png"},{"content":"http://m.xxxiao.com/133334","imageUrl":"image/banner_4_20170507.png"},{"content":"http://m.xxxiao.com/133334","imageUrl":"image/banner_5_20170507.png"}]
     * dongman : ["image/dongman_1_20170507.png","image/dongman_2_20170507.png","image/dongman_3_20170507.png","image/dongman_4_20170507.png"]
     * notUseVersion : 1.0
     * notice : 请大家及时反馈,使用中遇到的问题!如果你有什么意见或建议欢迎评论！
     * push : ["image/push_1_20170507.png","image/push_2_20170507.png","image/push_3_20170507.png"]
     */

    private String notUseVersion;
    private String notice;
    private List<BannerInfo> bannerInfo;
    private List<String> dongman;
    private List<String> push;

    public String getNotUseVersion() {
        return notUseVersion;
    }

    public void setNotUseVersion(String notUseVersion) {
        this.notUseVersion = notUseVersion;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public List<BannerInfo> getBannerInfo() {
        return bannerInfo;
    }

    public void setBannerInfo(List<BannerInfo> bannerInfo) {
        this.bannerInfo = bannerInfo;
    }

    public List<String> getDongman() {
        return dongman;
    }

    public void setDongman(List<String> dongman) {
        this.dongman = dongman;
    }

    public List<String> getPush() {
        return push;
    }

    public void setPush(List<String> push) {
        this.push = push;
    }

}
