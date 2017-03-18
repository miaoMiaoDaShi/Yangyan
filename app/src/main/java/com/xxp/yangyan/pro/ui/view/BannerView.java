package com.xxp.yangyan.pro.ui.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.adapter.BannerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 钟大爷 on 2017/2/23.
 */

public class BannerView extends RelativeLayout {

    private final String TAG = "BannerView";
    private Activity activity;


    private android.support.v4.view.ViewPager viewPager;
    //轮播图的数量
    private final int viewPagerCount = 5;
    //指示器的小圆点数组
    private Point[] mPointArray;
    //开始自动滑动
    private final int START_SCROLL = 0x11;

    private OnPagerChange onPagerChange;

    private final int DEFAULT_EMPTY_COLOR = Color.CYAN;
    private final int DEFAULT_FULL_COLOR = Color.WHITE;
    private final int DEFAULT_SCROLL_TIME = 5000;

    private int loading_image;
    //自动轮播的时间
    private int scroll_time;
    //小圆点选中的颜色
    private int point_empty_color;

    //小圆点没选中的颜色
    private int point_full_color;


    private View activityView;

    //停止制动滑动
    private boolean stop = false;
    private List<View> bannerView;
    private LinearLayout pointGroup;

    public List<View> getBannerView() {
        return bannerView;
    }

    public void setBannerView(List<View> bannerView) {
        this.bannerView = bannerView;
    }

    private Context mContext;

    private BannerScroll mScroller;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == START_SCROLL) {
                //开始轮播
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
    };

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG, "BannerView: " + context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        point_empty_color = typedArray.getColor(R.styleable.BannerView_point_empty_color, DEFAULT_EMPTY_COLOR);
        point_full_color = typedArray.getColor(R.styleable.BannerView_point_full_color, DEFAULT_FULL_COLOR);
        scroll_time = typedArray.getInt(R.styleable.BannerView_auto_scroll_time, DEFAULT_SCROLL_TIME);
        loading_image = typedArray.getInt(R.styleable.BannerView_loading_image, R.drawable.bg_loading);
    }

    private void autoScroll() {
        mScroller = new BannerScroll(getContext());
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(viewPager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //自动轮播
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!stop) {
                    mHandler.sendEmptyMessage(START_SCROLL);
                }
            }
        };
        timer.schedule(timerTask, 0, scroll_time);
    }


    private void initView() {
        initViewPager();
        initPoint();
        initBannerView();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
    }

    //设置切换滑动的时间
    public void setScrollDuration(int duration){
        mScroller.setmDuration(duration);
    }
    private void initBannerView() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(viewPager, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(pointGroup, params);
    }

    private void initViewPager() {
        viewPager = new ViewPager(getContext());
        bannerView = new ArrayList<>();
        for (int i = 0; i < viewPagerCount; i++) {
            ImageView image = new ImageView(getContext());
            image.setImageResource(loading_image);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            bannerView.add(image);
        }

        viewPager.setAdapter(new BannerAdapter(bannerView));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "onPageSelected: ");
                int newPosition = position % viewPagerCount;
                for (int i = 0; i < viewPagerCount; i++) {
                    mPointArray[newPosition].setShow(true);
                    if (newPosition != i) {
                        mPointArray[i].setShow(false);
                    }
                }

                if (null != onPagerChange) {
                    onPagerChange.onPagerChange(newPosition);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initPoint() {
        pointGroup = new LinearLayout(getContext());
        mPointArray = new Point[viewPagerCount];
        for (int i = 0; i < viewPagerCount; i++) {
            Point point = new Point(getContext());
            point.setLayoutParams(new LinearLayout.LayoutParams(50, 40));
            point.setPadding(10, 0, 10, 0);
            mPointArray[i] = point;
            if (i == 0) {
                point.setShow(true);
            } else {
                point.setShow(false);
            }
            pointGroup.addView(point);
        }


    }


    public interface OnPagerChange {
        void onPagerChange(int position);
    }

    public void setOnPagerChange(OnPagerChange onPagerChange) {
        this.onPagerChange = onPagerChange;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        autoScroll();
    }

    private class Point extends View {

        Paint paint;
        boolean isShow;

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
            invalidate();
        }

        public Point(Context context) {
            super(context);
            initPaint();
        }

        private void initPaint() {
            paint = new Paint();
            paint.setAntiAlias(true);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (isShow) {
                paint.setColor(point_full_color);
            } else {
                paint.setColor(point_empty_color);
            }
            canvas.drawCircle(7, 7, 7, paint);
        }
    }

    Application.ActivityLifecycleCallbacks lifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

            if (BannerView.this.activity.equals(activity)) {
                Log.e(TAG, "onActivityResumed: ");
                stop = false;
            }

        }

        @Override
        public void onActivityPaused(Activity activity) {
            if (BannerView.this.activity.equals(activity)) {
                Log.e(TAG, "onActivityPaused: ");
                stop = true;
            }

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    public Activity getActivity() {
        return activity;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }


    public void setActivity(Activity activity) {
        activity.getApplication().registerActivityLifecycleCallbacks(lifecycleCallbacks);
        this.activity = activity;
    }

}
