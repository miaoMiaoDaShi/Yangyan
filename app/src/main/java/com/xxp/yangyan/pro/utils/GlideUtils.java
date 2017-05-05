package com.xxp.yangyan.pro.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by 钟大爷 on 2017/3/3.
 * /**
 * Glide特点
 * 使用简单
 * 可配置度高，自适应程度高
 * 支持常见图片格式 Jpg png gif webp
 * 支持多种数据源  网络、本地、资源、Assets 等
 * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
 * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
 * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
 * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
 */


public class GlideUtils {
    //默认加载
    public static void loadImageView(Activity activity, String url, ImageView iv) {
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
    }

    //默认加载
    public static void loadImageView(Fragment fragment, String url, ImageView iv) {
        Glide.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
    }
}
