package com.xxp.yangyan.pro.api;

import com.xxp.yangyan.pro.bean.CategoryInfoData;
import com.xxp.yangyan.pro.bean.HomeInfo;
import com.xxp.yangyan.pro.bean.SplashInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by 钟大爷 on 2017/2/4.
 */

public interface MMApi {
    //我的http://xxpbox.cn/app/yangyan/     http://192.168.191.1/
    String MY_BASE_URL = "http://xxpbox.cn/app/yangyan/";

    @GET("homePage")
    Observable<HomeInfo> getHomePage();

    @GET("splashImg")
    Observable<SplashInfo> getSplashPage();

    @GET("categoryInfo")
    Observable<CategoryInfoData> getCategoryPage();
}
