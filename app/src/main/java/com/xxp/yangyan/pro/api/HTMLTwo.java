package com.xxp.yangyan.pro.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 废弃的接口.............
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
