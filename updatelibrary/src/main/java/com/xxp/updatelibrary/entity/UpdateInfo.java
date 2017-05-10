package com.xxp.updatelibrary.entity;

/**
 * Created by 钟大爷 on 2017/3/15.
 */

public class UpdateInfo {
    /**
     * constraint : false
     * describe : 版本更新
     * versionCode : 1
     * versionName : 2.3
     */

    //强制更新
    private boolean constraint;
    //版本描述
    private String describe;
    //版本号
    private int versionCode;
    //版本名
    private String versionName;
    //下载地址
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isConstraint() {
        return constraint;
    }

    public void setConstraint(boolean constraint) {
        this.constraint = constraint;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
