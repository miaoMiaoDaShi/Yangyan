package com.xxp.yangyan.pro.listener;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/3/3
 * Description : 自己的RecyclerView的辅助类
 */

public abstract class BaseOnScrollListener<T> extends RecyclerView.OnScrollListener implements View.OnTouchListener {
    //是否加载中.
    private boolean mLoading = false;

    //当前加载的是第几页
    private int mCurrentPage;
    //默认的第一页,页码
    private final int DEFAULT_PAGE = 1;

    //数据到是否底了...
    private boolean mEnd = false;
    private List<T> mList;

    private final String TAG = "BaseOnScrollListener";

    private RecyclerView recyclerView;

    /*
    为了防止数据量少,下拉刷新加载数据的时候触发上拉加载,故设置此token作为标记
     */
    private int mToken;
    private final int TOKEN_PULL_DOWN = 0x11;
    private final int TOKEN_PULL_UP = 0x12;

    public void setList(List<T> list){
        mList = list;
    }
    //该函数的构造函数
    public BaseOnScrollListener() {
        mList = getList();
        initRecyclerView();
    }

    protected abstract List<T> getList();

    //初始化RecylerView的操作
    private void initRecyclerView() {
        recyclerView = getRecylerView();
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(new ScaleInAnimationAdapter(getAdapter()));
        recyclerView.addOnScrollListener(this);
        recyclerView.setOnTouchListener(this);
    }

    protected abstract RecyclerView getRecylerView();

    //重置状态
    public void reset() {
        mLoading = false;
        mCurrentPage = DEFAULT_PAGE;
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
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        //正在被用户拖动
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            mToken = TOKEN_PULL_UP;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //当前状态并不是正在加载,且RecylerView满足加载的条件
        if (!isLoading() && canLoadMore(recyclerView) && !mEnd && mToken == TOKEN_PULL_UP) {
            onLoadMore();
            //没有更多的数据了
        } else if (!isLoading() && canLoadMore(recyclerView) && mEnd && mToken == TOKEN_PULL_UP) {
            onNotMoreData();
        }

    }

    protected abstract void onNotMoreData();


    //执行刷新
    public void refresh() {
        reset();
        mToken = TOKEN_PULL_DOWN;
        mLoading = true;
        loadData();
    }

    //将图片显示在RecylerView上
    public void showImageListToView(List<T> lists) {
        Log.e(TAG, "showImageListToView: 1"+ mList.size() );
        if (null != lists) {
            if (lists.isEmpty() && mCurrentPage == DEFAULT_PAGE) {
                isEmpty();
                mList.clear();
                getAdapter().notifyDataSetChanged();
            } else {
                if (mCurrentPage == DEFAULT_PAGE) {
                    Log.e(TAG, "showImageListToView: 2" );
                    this.mList.clear();
                    Log.e(TAG, "showImageListToView: 3" );
                    this.mList.addAll(lists);
                    Log.e(TAG, "showImageListToView: 4" );
                } else {
                    this.mList.addAll(lists);
                }
            }
            Log.e(TAG, "showImageListToView: 5" );
            int pos = mList.size();
            if (pos > 0) {
                pos--;
                Log.e(TAG, "showImageListToView: 6" );
                getAdapter().notifyItemChanged(pos, 1);
                Log.e(TAG, "showImageListToView: 7" );
                setLoadStatus(false);
            }
        }
    }

    protected abstract void isEmpty();


    /**
     * 加载更多数据
     */
    public void onLoadMore() {
        mCurrentPage++;
        getRefreshLayout().post(new Runnable() {
            @Override
            public void run() {
                setLoadStatus(true);
                loadData();
            }
        });


    }

    private void setLoadStatus(boolean b) {
        mLoading = b;
        getRefreshLayout().setRefreshing(b);
    }


    protected abstract void loadData();

    //判断数据是否到底了
    public boolean isEnd() {
        return mEnd;
    }

    //判断是否正在加载
    public boolean isLoading() {
        return mLoading;
    }

    public void setLoading(boolean loading) {
        this.mLoading = loading;
    }

    public void setEnd(boolean end) {
        this.mEnd = end;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.mCurrentPage = currentPage;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mLoading) {
            return true;
        }
        return false;
    }
}
