package com.xxp.yangyan.pro.api;

import com.xxp.yangyan.pro.utils.IOUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 钟大爷 on 2017/2/9.
 */

public class ApiEngine {
    private volatile static ApiEngine apiEngine;
    //超时时间,单位秒
    private static final int DEFAULT_TIMEOUT = 10000;

    private Retrofit apiRetrofit;
    private Retrofit stringRetrofit;

    private ApiEngine() {
        File cacheDir = new File(IOUtils.getSDPath()+"/cache");
        Cache cache = new Cache(cacheDir,1024*1024*100);
        OkHttpClient client = new OkHttpClient
                .Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cache(cache)
                .build();

        apiRetrofit = new Retrofit
                .Builder()
                .client(client)
                .baseUrl(MMApi.MY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        stringRetrofit = new Retrofit
                .Builder()
                .client(client)
                .baseUrl(HTMLOne.MEINV_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


    }

    public static ApiEngine getInstance(){
        if (apiEngine==null) {
            synchronized (ApiEngine.class){
                if (apiEngine==null){
                    apiEngine = new ApiEngine();
                }
            }
        }
        return apiEngine;
    }


    public MMApi getMMService(){
        return apiRetrofit.create(MMApi.class);
    }


    public HTMLOne getHContentService(){
        return stringRetrofit.create(HTMLOne.class);
    }
}
