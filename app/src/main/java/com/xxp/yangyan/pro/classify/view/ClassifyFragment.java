package com.xxp.yangyan.pro.classify.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xxp.yangyan.R;
import com.xxp.yangyan.mvp.view.MvpLceView;
import com.xxp.yangyan.mvp.view.impl.MvpLceListView;
import com.xxp.yangyan.pro.App;
import com.xxp.yangyan.pro.adapter.ClassifyAdapter;
import com.xxp.yangyan.pro.base.BaseRecyclerViewFragment;
import com.xxp.yangyan.pro.entity.ClassifyInfo;
import com.xxp.yangyan.pro.entity.ClassifyInfoData;
import com.xxp.yangyan.pro.classify.presenter.Presenter;
import com.xxp.yangyan.pro.imageList.view.ImageLIstActivity;
import com.xxp.yangyan.pro.listener.BaseOnScrollListener;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 最新美女
 * 女神笑了
 * 性感美女
 * 少女萝莉
 * 美乳香臀
 * 丝袜美腿
 * 性感特写
 * 欧美女神
 * 日韩东亚
 * 女神合集
 */

public class ClassifyFragment extends BaseRecyclerViewFragment<Presenter, ClassifyInfo> implements MvpLceListView<ClassifyInfoData> {


    private final String TAG = "ClassifyFragment";
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.swipe_category)
    SwipeRefreshLayout swipeClassify;
    private List<ClassifyInfo> mClassifyInfo;
    private ClassifyAdapter mClassifyAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        recylerViewClickListener();
    }

    private void initData() {
        mClassifyInfo = getList();
        mClassifyAdapter = (ClassifyAdapter) getAdapetr();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(App.getmContext());
    }

    //初始化RecylerView
    private void recylerViewClickListener() {
        //设置每个item的点击事件
        ((ClassifyAdapter) getAdapetr()).setOnItemClick(new ClassifyAdapter.OnItemClick() {
            @Override
            public void onClickListener(int position) {
                Intent intent = new Intent(UIUtils.getContext(), ImageLIstActivity.class);
                intent.putExtra(ImageLIstActivity.KEY_TYPE, mClassifyInfo.get(position).getLink());
                startActivity(intent);
            }
        });
    }


    @Override
    protected RecyclerView getRecylerView() {
        return rvCategory;
    }

    @Override
    protected List<ClassifyInfo> getList() {
        return mClassifyInfo = mClassifyInfo == null ? new ArrayList<ClassifyInfo>() : mClassifyInfo;
    }

    @Override
    protected RecyclerView.Adapter getAdapetr() {
        return mClassifyAdapter = mClassifyAdapter == null ? new ClassifyAdapter(getContext(), getList(), this) : mClassifyAdapter;
    }

    //加载布局文件
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void loadData() {
        presenter.loadData();
    }

    @Override
    protected SwipeRefreshLayout getRefreshLayout() {
        return swipeClassify;
    }

    @Override
    protected Presenter bindPresenter() {
        return new Presenter();
    }


    @Override
    public void showLoading(boolean type) {

    }

    @Override
    public void showContent() {
        swipeClassify.setRefreshing(false);
    }

    @Override
    public void showError(Throwable throwable) {
        loadError();
    }

    @Override
    public void showData(ClassifyInfoData data) {
        getBaseOnScrollListener().showImageListToView(data.getResult());
    }

    @Override
    public void dataIsEnd() {
        getBaseOnScrollListener().setEnd(true);
    }


}
