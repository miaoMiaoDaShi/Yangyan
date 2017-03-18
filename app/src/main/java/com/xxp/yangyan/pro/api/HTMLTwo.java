package com.xxp.yangyan.pro.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 钟大爷 on 2017/3/4.
 */

public interface HTMLTwo {
    //美女壁纸的
    String MEINV_BASE_URL = "https://www.susu57.com/";

    @GET("page/{page}")
    Observable<ResponseBody> getHomePage(@Path("page") int page);

    @GET("cat/{type}/page/{page}")
    Observable<ResponseBody> getTypePage(@Path("type") String type,@Path("page") int page);

    @GET("{id}")
    Observable<ResponseBody> getParticulars(@Path("id") String id);
}
