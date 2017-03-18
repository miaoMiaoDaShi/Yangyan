package com.xxp.yangyan.pro.banner;

import android.app.Activity;
import android.view.View;

import java.util.List;

/**
 * Created by 钟大爷 on 2017/3/18.
 *
 * 使用轮播,必须实现该接口
 */

public interface IBannerPrepare {
    //BannerView的Activity
    Activity getActivity();

    //得到BannerView的内部的List<View> 集合   View 可强转ImageView
    void setBannerViews(List<View> views);
}
