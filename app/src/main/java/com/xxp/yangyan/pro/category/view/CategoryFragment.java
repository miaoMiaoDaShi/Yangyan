package com.xxp.yangyan.pro.category.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xxp.yangyan.R;
import com.xxp.yangyan.mvp.view.MvpLceView;
import com.xxp.yangyan.pro.App;
import com.xxp.yangyan.pro.adapter.CategoryAdapter;
import com.xxp.yangyan.pro.base.BaseSwipeFragment;
import com.xxp.yangyan.pro.bean.CategoryInfo;
import com.xxp.yangyan.pro.bean.CategoryInfoData;
import com.xxp.yangyan.pro.category.presenter.Presenter;
import com.xxp.yangyan.pro.imageList.view.ImgLIstActivity;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;

/**
 * Created by 钟大爷 on 2017/2/3.
 * 段子om 美女图片 宅男女神 养眼套图
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

public class CategoryFragment extends BaseSwipeFragment<Presenter> implements MvpLceView<CategoryInfoData> {


    private final String TAG = "CategoryFragment";
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.swipe_category)
    SwipeRefreshLayout swipeCategory;
    private List<CategoryInfo> categories;
    private final String category[] = {"性感美女", "少女萝莉", "美乳香臀", "丝袜美腿", "性感特写", "日韩东亚"
            , "女神合集", "欧美女神"};

    private final String link[] = {"xinggan", "shaonv", "mrxt", "swmt", "xgtx", "rihandongya", "collection",
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
        adapter = new CategoryAdapter(getContext(),categories, this);
        rvCategory.setAdapter(adapter);
        rvCategory.setItemAnimator(new ScaleInBottomAnimator());

        //设置每个item的点击事件
        adapter.setOnItemClick(new CategoryAdapter.OnItemClick() {
            @Override
            public void onClickListener(int position) {
                Intent intent = new Intent(UIUtils.getContext(), ImgLIstActivity.class);
                intent.putExtra("type", categories.get(position).getLink());
                startActivity(intent);
            }
        });
    }

    //加载布局文件
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected Presenter bindPresenter() {
        return new Presenter();
    }


    @Override
    public void onRefresh() {
        presenter.loadData();
    }


    @Override
    public void showLoading(boolean type) {

    }

    @Override
    public void showContent() {
        swipeCategory.setRefreshing(false);
    }

    @Override
    public void showError(Throwable throwable) {
        loadError();
    }

    @Override
    public void showData(CategoryInfoData data) {
        categories.clear();
        categories.addAll(data.getResult());
        adapter.notifyDataSetChanged();
    }


}
