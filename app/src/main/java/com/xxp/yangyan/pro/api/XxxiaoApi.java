package com.xxp.yangyan.pro.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : APi接口(第三方)
 */

public interface XxxiaoApi {
    //美女壁纸的
    String MEINV_BASE_URL = "http://m.xxxiao.com/";

    //获得最新的
    @GET("new/page/{page}")
    Observable<ResponseBody> getHomePage(@Path("page") int page);

    //按类别获取
    @GET("cat/{type}/page/{page}")
    Observable<ResponseBody> getTypePage(@Path("type") String type,@Path("page") int page);

    //根据id获取套图
    @GET("{id}")
    Observable<ResponseBody> getParticulars(@Path("id") int id);

}
