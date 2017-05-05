package com.xxp.yangyan.pro.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/3/16
 * Description : 轮播图的适配器类
 */

public class BannerAdapter extends PagerAdapter {
        List<ImageView> viewList;

        public BannerAdapter(List<ImageView> viewList) {
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