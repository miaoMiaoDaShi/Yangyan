package com.xxp.yangyan.pro.listener;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/3/3
 * Description : 自己的RecyclerView的辅助类
 */

public abstract class BaseOnScrollListener<T> extends RecyclerView.OnScrollListener {
    //是否加载中.
    private boolean loading = false;

    //当前加载的是第几页
    private int currentPage;
    //默认的第一页,页码
    private final int DEFAULT_PAGE = 1;

    //数据到是否底了...
    private boolean end = false;
    private List<T> lists;

    private final String TAG = "BaseOnScrollListener";

    private RecyclerView recyclerView;


    //该函数的构造函数
    public BaseOnScrollListener() {
        lists = getList();
        initRecyclerView();
    }

    protected abstract List<T> getList();

    //初始化RecylerView的操作
    private void initRecyclerView() {
        recyclerView = getRecylerView();
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(new ScaleInAnimationAdapter(getAdapter()));
        recyclerView.addOnScrollListener(this);
    }

    protected abstract RecyclerView getRecylerView();

    //重置状态
    public void reset() {
        loading = false;
        currentPage = DEFAULT_PAGE;
    }

    //获取布局管理
    public abstract RecyclerView.LayoutManager getLayoutManager();

    //获取谷歌的刷新控件
    public abstract SwipeRefreshLayout getRefreshLayout();

    public boolean canLoadMore(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return false;
        } else {
            return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                    >= recyclerView.computeVerticalScrollRange();
        }
    }

    protected abstract RecyclerView.Adapter getAdapter();


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //当前状态并不是正在加载,且RecylerView满足加载的条件
        if (!isLoading() && canLoadMore(recyclerView) && !end) {
            onLoadMore();
            Log.i("下拉加载", "onScrolled: ");
        }

    }


    //执行刷新
    public void refresh() {
        reset();
        loading = true;
        loadData();
    }

    //将图片显示在RecylerView上
    public void showImageListToView(List<T> lists) {
        Log.d(TAG, "showImageListToView: ");
        if (null != lists) {
            int listCount = lists.size();
            if (currentPage == DEFAULT_PAGE) {
                this.lists.clear();
                this.lists.addAll(lists);
            } else {
                this.lists.addAll(lists);
            }
            int pos = lists.size();
            if (pos > 0) {
                pos--;
                Log.d(TAG, "showImageListToView: adapter" + getAdapter());
                getAdapter().notifyItemChanged(pos, listCount);
                setLoadStatus(false);
            }
        }
    }


    /**
     * 加载更多数据
     */
    public void onLoadMore() {
        currentPage++;
        getRefreshLayout().post(new Runnable() {
            @Override
            public void run() {
                setLoadStatus(true);
                loadData();
            }
        });


    }

    private void setLoadStatus(boolean b) {
        loading = b;
        getRefreshLayout().setRefreshing(b);
    }


    protected abstract void loadData();

    //判断数据是否到底了
    public boolean isEnd() {
        return end;
    }

    //判断是否正在加载
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
