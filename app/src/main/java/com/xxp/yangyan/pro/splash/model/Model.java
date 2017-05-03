package com.xxp.yangyan.pro.splash.model;

import com.xxp.yangyan.mvp.model.MvpModel;
import com.xxp.yangyan.pro.api.ApiEngine;
import com.xxp.yangyan.pro.bean.SplashInfo;

import rx.Observable;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description :
 */

public class Model implements MvpModel {
    public Observable<SplashInfo> getData() {
        return ApiEngine.getInstance().getMMService().getSplashPage();
    }
}
