package com.xxp.yangyan.pro.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.tapadoo.alerter.Alerter;
import com.xxp.yangyan.R;
import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.pro.utils.UIUtils;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/5
 * Description : 含有swipeRefreshLayout的Fragment基类
 */

public abstract class BaseRefreshLayoutFragment<P extends MvpBasePresenter> extends BaseFragment<P> implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "BaseRefreshLayoutFragme";
    //谷歌的刷新控件的监听器
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener;
    //谷歌的刷新控件
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSwipeRefreshLayout();
        loadData();
    }

    //返回谷歌刷新控件
    protected abstract SwipeRefreshLayout getRefreshLayout();

    //刷新控件的初始化
    private void initSwipeRefreshLayout() {
        mOnRefreshListener = this;
        mSwipeRefreshLayout = getRefreshLayout();
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        //谷歌刷新空间的颜色
        mSwipeRefreshLayout.setColorSchemeColors(
                UIUtils.getColor(R.color.red),
                UIUtils.getColor(R.color.red),
                UIUtils.getColor(R.color.red),
                UIUtils.getColor(R.color.red)

        );

    }

    //得到刷新控件的监听器
    protected SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return mOnRefreshListener;
    }

    //加载错误
    protected void loadError() {
        mSwipeRefreshLayout.setRefreshing(false);
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

    //没有更多的数据了
    protected void notMoreData() {
        mSwipeRefreshLayout.setRefreshing(false);
        Alerter
                .create(getActivity())
                .setBackgroundColor(R.color.colorPrimary)
                .setDuration(3000)
                .setTitle("阿欧,没有更多数据了!")
                .show();
    }

    private void loadData() {
        Log.v(TAG, "loadData");
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mOnRefreshListener.onRefresh();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.unSubscribe();
    }

}
