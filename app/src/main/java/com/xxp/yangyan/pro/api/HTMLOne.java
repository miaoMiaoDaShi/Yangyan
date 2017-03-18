package com.xxp.yangyan.pro.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 钟大爷 on 2017/2/4.
 * http://m.xxxiao.com/cat/shaonv
 */

public interface HTMLOne {
    //美女壁纸的
    String MEINV_BASE_URL = "http://m.xxxiao.com/";

    @GET("page/{page}")
    Observable<ResponseBody> getHomePage(@Path("page") int page);

    @GET("cat/{type}/page/{page}")
    Observable<ResponseBody> getTypePage(@Path("type") String type,@Path("page") int page);

    @GET("{id}")
    Observable<ResponseBody> getParticulars(@Path("id") String id);

}
