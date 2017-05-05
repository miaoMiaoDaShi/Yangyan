package com.xxp.yangyan.pro.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;


/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/3/16
 * Description :
 */

public class BannerScroll extends Scroller {

    private int mDuration = 2000;

    public BannerScroll(Context context) {
        this(context, null);
    }

    public BannerScroll(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }
}