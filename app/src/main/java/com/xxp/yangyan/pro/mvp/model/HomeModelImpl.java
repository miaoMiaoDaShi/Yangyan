package com.xxp.yangyan.pro.mvp.model;
import com.xxp.yangyan.pro.api.ApiEngine;
import com.xxp.yangyan.pro.bean.HomeInfo;
import com.xxp.yangyan.pro.mvp.contract.HomeContract;

import rx.Observable;

/**
* Created by 钟大爷 on 2017/02/09
*/

public class HomeModelImpl implements HomeContract.Model {


    @Override
    public Observable<HomeInfo> getData() {
        return ApiEngine.getInstance().getMMService().getHomePage();
    }
}