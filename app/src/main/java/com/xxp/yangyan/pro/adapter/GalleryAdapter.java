package com.xxp.yangyan.pro.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 钟大爷 on 2017/2/5.
 * 图片浏览的viewPager的Adapter
 * 这个暂时预备使用   因为图片浏览  有时候还是无线循环比较好
 */

public class GalleryAdapter extends PagerAdapter {
    //view的集合
    private List<View> views;

    public GalleryAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
