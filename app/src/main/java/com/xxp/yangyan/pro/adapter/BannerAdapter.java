package com.xxp.yangyan.pro.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

//轮播图的适配器类
    public class BannerAdapter extends PagerAdapter {
        List<View> viewList;

        public BannerAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        @Override
        public int getCount() {
            if (viewList != null) {
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int newPosition = position % viewList.size();
            container.addView(viewList.get(newPosition));
            return viewList.get(newPosition);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            int newPosition = position % viewList.size();
            container.removeView(viewList.get(newPosition));
        }
    }