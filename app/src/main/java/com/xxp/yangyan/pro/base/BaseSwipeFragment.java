package com.xxp.yangyan.pro.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.tapadoo.alerter.Alerter;
import com.xxp.yangyan.R;
import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.pro.utils.UIUtils;

/**
 * Created by 钟大爷 on 2017/2/9.
 */

public abstract class BaseSwipeFragment<P extends MvpBasePresenter>
        extends BaseFragment<P> implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefresh;
    //刷新空间的监听
    private SwipeRefreshLayout.OnRefreshListener swRefreshListener;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initSwipeRefresh();
        loadData();
    }


    //谷歌刷新控件的初始化
    private void initSwipeRefresh() {
        swipeRefresh = getSwipeRefresh();
        swRefreshListener = getListener();
        swipeRefresh.setOnRefreshListener(swRefreshListener);

        //设置谷歌刷新控件的颜色
        swipeRefresh.setColorSchemeColors(
                UIUtils.getColor(R.color.red),
                UIUtils.getColor(R.color.red),
                UIUtils.getColor(R.color.red),
                UIUtils.getColor(R.color.red)

        );

    }

    protected abstract SwipeRefreshLayout getSwipeRefresh();

    protected void loadData() {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
                swRefreshListener.onRefresh();
            }
        });
    }


    @Override
    public void onRefresh() {

    }

    //加载数据出错时调用
    protected void loadError() {
        swipeRefresh.setRefreshing(false);
        Alerter
                .create(getActivity())
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

    //返回谷歌刷新控件的监听器
    public SwipeRefreshLayout.OnRefreshListener getListener() {
        return this;
    }
}
