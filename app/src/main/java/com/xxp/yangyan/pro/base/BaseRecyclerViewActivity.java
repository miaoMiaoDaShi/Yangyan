package com.xxp.yangyan.pro.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.pro.listener.BaseOnScrollListener;

import java.util.List;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/5
 * Description :
 */

public abstract class BaseRecyclerViewActivity<P extends MvpBasePresenter, T> extends BaseRefreshLayoutActivity<P> {
    //自己写的RecylerView滚动监听数据加载辅助类
    private BaseOnScrollListener mBaseOnScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecylerView();
    }


    @Override
    public void onRefresh() {
        mBaseOnScrollListener.refresh();
    }

    private void initRecylerView() {
        mBaseOnScrollListener = new BaseOnScrollListener<T>() {
            @Override
            protected List<T> getList() {
                return BaseRecyclerViewActivity.this.getList();
            }

            @Override
            protected RecyclerView getRecylerView() {
                return BaseRecyclerViewActivity.this.getRecylerView();
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return BaseRecyclerViewActivity.this.getLayoutManager();
            }

            @Override
            public SwipeRefreshLayout getRefreshLayout() {
                return BaseRecyclerViewActivity.this.getRefreshLayout();
            }

            @Override
            protected RecyclerView.Adapter getAdapter() {
                return BaseRecyclerViewActivity.this.getAdapetr();
            }


            @Override
            protected void loadData() {
                BaseRecyclerViewActivity.this.loadData();
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
