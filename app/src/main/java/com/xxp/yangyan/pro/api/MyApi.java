package com.xxp.yangyan.pro.api;

import com.xxp.yangyan.pro.entity.ClassifyInfoData;
import com.xxp.yangyan.pro.entity.HomeData;
import com.xxp.yangyan.pro.entity.SplashInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 自己服务器数据的api
 */

public interface MyApi {
    //我的http://xxpbox.cn/app/yangyan/     http://192.168.191.1/
    String MY_BASE_URL = "http://xxpbox.cn/app/yangyan/";

    //获得软件首页的数据
    @GET("homePage")
    Observable<HomeData> getHomePage();

    //获取启动图信息
    @GET("splashImg")
    Observable<SplashInfo> getSplashPage();

    //获得分类信息
    @GET("categoryInfo")
    Observable<ClassifyInfoData> getCategoryPage();
}
