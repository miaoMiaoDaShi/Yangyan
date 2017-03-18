package com.xxp.yangyan.pro.mvp.model;
import com.xxp.yangyan.pro.api.ApiEngine;
import com.xxp.yangyan.pro.bean.SplashInfo;
import com.xxp.yangyan.pro.mvp.contract.SplashContract;

import rx.Observable;

/**
* Created by 钟大爷 on 2017/02/09
*/

public class SplashModelImpl implements SplashContract.Model{

    @Override
    public Observable<SplashInfo> getData() {
        return ApiEngine.getInstance().getMMService().getSplashPage();
    }
}