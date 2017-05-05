package com.xxp.yangyan.pro.banner;

import android.app.Activity;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/3/16
 * Description : 使用轮播实现的接口
 */

public interface IBannerPrepare {
    //BannerView的Activity
    Activity getActivity();

    //得到BannerView的内部的List<View> 集合   View 可强转ImageView
    void setBannerViews(List<ImageView> views);
}
