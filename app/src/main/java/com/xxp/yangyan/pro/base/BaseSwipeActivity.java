package com.xxp.yangyan.pro.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;
import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.mvp.presenter.LoadDataPresenter;
import com.xxp.yangyan.pro.utils.UIUtils;

/**
 * Created by 钟大爷 on 2017/2/5.
 */

public abstract class BaseSwipeActivity<P extends LoadDataPresenter> extends BaseActivity<P>
        implements SwipeRefreshLayout.OnRefreshListener {
    //谷歌的刷新控件的监听器
    private SwipeRefreshLayout.OnRefreshListener listener;

    //谷歌的刷新控件
    private SwipeRefreshLayout swipeRefreshLayout;

    private final String TAG = "BaseSwipeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: 3");
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: 4" );
        initView();
        initSwipeRefreshLayout();
        loadData();
    }

    private void initView() {
        //将内容显示在状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    protected abstract int getLayoutId();

    private void loadData() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                listener.onRefresh();
            }
        });
    }

    //刷新控件的初始化
    private void initSwipeRefreshLayout() {
        listener = this;
        swipeRefreshLayout = getSwipeRefreshLayout();
        swipeRefreshLayout.setOnRefreshListener(listener);
        setSwipeRefreshLayoutColor();

    }

    //谷歌刷新空间的颜色
    protected void setSwipeRefreshLayoutColor() {
        swipeRefreshLayout.setColorSchemeColors(
                UIUtils.getColor(R.color.red),
                UIUtils.getColor(R.color.red),
                UIUtils.getColor(R.color.red),
                UIUtils.getColor(R.color.red)

        );

    }

    //得到刷新控件的监听器
    protected SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return listener;
    }


    //返回谷歌刷新控件
    public abstract SwipeRefreshLayout getSwipeRefreshLayout();


    protected void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    protected void loadError() {
        swipeRefreshLayout.setRefreshing(false);
        Alerter
                .create(this)
                .setBackgroundColor(R.color.colorPrimary)
                .setDuration(3000)
                .setTitle("加载失败,请点我重试!")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadData();
                    }
                })
                .show();
//        Snackbar.make(swipeRefreshLayout, "加载失败,请下拉重试!", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }).show();
    }


    protected void loadIsEmpty() {
        swipeRefreshLayout.setRefreshing(false);
        Alerter
                .create(this)
                .setBackgroundColor(R.color.colorPrimary)
                .setDuration(3000)
                .setTitle("阿欧,没有更多数据了!")
                .show();
//        Snackbar.make(swipeRefreshLayout, "加载失败,请下拉重试!", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }).show();
    }

    @Override
    protected void onDestroy() {
        presenter.unSubscribe();
        super.onDestroy();
    }
}
