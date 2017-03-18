package com.xxp.yangyan.pro.bean;


import cn.bmob.v3.BmobObject;

/**
 * Created by 钟大爷 on 2017/2/17.
 */

public class UserInfo extends BmobObject{
    private String CDKEY;
    private String type;

    public String getCDKEY() {
        return CDKEY;
    }

    public void setCDKEY(String CDKEY) {
        this.CDKEY = CDKEY;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
