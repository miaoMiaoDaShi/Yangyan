package com.xxp.yangyan.pro.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.pro.listener.BaseOnScrollListener;

import java.util.List;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/5
 * Description : 数据加载,RecyclerView的Fragment的基类
 */

public abstract class BaseRecyclerViewFragment<P extends MvpBasePresenter, T> extends BaseRefreshLayoutFragment<P> {
    private static final String TAG = "BaseRecyclerViewFragmen";
    //自己写的RecylerView滚动监听数据加载辅助类
    private BaseOnScrollListener mBaseOnScrollListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecylerView();
    }


    @Override
    public void onRefresh() {
        Log.v(TAG, ": onRefresh");
        mBaseOnScrollListener.refresh();
    }

    private void initRecylerView() {
        mBaseOnScrollListener = new BaseOnScrollListener<T>() {
            @Override
            protected List<T> getList() {
                return BaseRecyclerViewFragment.this.getList();
            }

            @Override
            protected RecyclerView getRecylerView() {
                return BaseRecyclerViewFragment.this.getRecylerView();
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return BaseRecyclerViewFragment.this.getLayoutManager();
            }

            @Override
            public SwipeRefreshLayout getRefreshLayout() {
                return BaseRecyclerViewFragment.this.getRefreshLayout();
            }

            @Override
            protected RecyclerView.Adapter getAdapter() {
                return BaseRecyclerViewFragment.this.getAdapetr();
            }


            @Override
            protected void loadData() {
                BaseRecyclerViewFragment.this.loadData();
            }
        };

    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    protected abstract RecyclerView getRecylerView();

    protected abstract List<T> getList();

    protected abstract RecyclerView.Adapter getAdapetr();


    protected abstract void loadData();


    public BaseOnScrollListener getBaseOnScrollListener() {
        return mBaseOnScrollListener;
    }
}
