package com.xxp.yangyan.pro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.App;
import com.xxp.yangyan.pro.adapter.CategoryAdapter;
import com.xxp.yangyan.pro.base.BaseSwipeFragment;
import com.xxp.yangyan.pro.bean.CategoryInfo;
import com.xxp.yangyan.pro.bean.CategoryInfoData;
import com.xxp.yangyan.pro.mvp.contract.CategoryContract;
import com.xxp.yangyan.pro.mvp.presenter.CategoryPresenterImpl;
import com.xxp.yangyan.pro.ui.activity.ImgLIstActivity;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 钟大爷 on 2017/2/3.
 * 段子om 美女图片 宅男女神 养眼套图
 最新美女
 女神笑了
 性感美女
 少女萝莉
 美乳香臀
 丝袜美腿
 性感特写
 欧美女神
 日韩东亚
 女神合集
 */

public class CategoryFragment extends BaseSwipeFragment<CategoryPresenterImpl> implements CategoryContract.view {


    private final String TAG = "CategoryFragment";
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.swipe_category)
    SwipeRefreshLayout swipeCategory;
    private List<CategoryInfo> categories;
    private final String category[] = {"性感美女","少女萝莉","美乳香臀","丝袜美腿","性感特写","日韩东亚"
    ,"女神合集","欧美女神"};

    private final String link[] = {"xinggan","shaonv","mrxt","swmt","xgtx","rihandongya","collection",
            "oumei"};
    private CategoryAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initRecylerView();
    }

    @Override
    protected SwipeRefreshLayout getSwipeRefresh() {
        return swipeCategory;
    }

    private void initData() {
        categories = new ArrayList<>();


    }

    //初始化RecylerView
    private void initRecylerView() {
        rvCategory.setLayoutManager(new LinearLayoutManager(App.getmContext()));
        adapter = new CategoryAdapter(categories,this);
        rvCategory.setAdapter(adapter);

        //设置每个item的点击事件
        adapter.setOnItemClick(new CategoryAdapter.OnItemClick() {
            @Override
            public void onClickListener(int position) {
                Intent intent = new Intent(UIUtils.getContext(), ImgLIstActivity.class);
                intent.putExtra("type",categories.get(position).getLink());
                startActivity(intent);
            }
        });
    }

    //加载布局文件
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    //指定其的presenter
    @Override
    protected CategoryPresenterImpl onCreatePresenter() {
        return new CategoryPresenterImpl(this);
    }

    @Override
    public void onRefresh() {
        presenter.getData();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showResult(CategoryInfoData categoryInfos) {
        Log.e(TAG, "showResult: "+categoryInfos.getResult().size() );
        categories.clear();
        categories.addAll(categoryInfos.getResult());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showSuccess() {
        swipeCategory.setRefreshing(false);
        Log.e(TAG, "showSuccess: " );
    }

    @Override
    public void showError(Throwable throwable) {
        loadError();
        Log.e(TAG, "showError: "+throwable );
    }
}
