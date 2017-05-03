package com.xxp.yangyan.pro.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.WindowManager;

import com.tapadoo.alerter.Alerter;
import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.utils.UIUtils;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 数据加载的Activity基类,刷新控件为谷歌的swipeRefreshLayout
 */

public abstract class BaseSwipeActivity<P extends BasePresenter> extends BaseActivity<P>
        implements SwipeRefreshLayout.OnRefreshListener {
    //谷歌的刷新控件的监听器
    private SwipeRefreshLayout.OnRefreshListener listener;

    //谷歌的刷新控件
    private SwipeRefreshLayout swipeRefreshLayout;

    private final String TAG = "BaseSwipeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //谷歌刷新空间的颜色
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


    //加载错误
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
    }


    //数据为空
    protected void loadIsEmpty() {
        swipeRefreshLayout.setRefreshing(false);
        Alerter
                .create(this)
                .setBackgroundColor(R.color.colorPrimary)
                .setDuration(3000)
                .setTitle("阿欧,没有更多数据了!")
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }
}
