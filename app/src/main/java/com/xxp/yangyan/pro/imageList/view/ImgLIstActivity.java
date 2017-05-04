package com.xxp.yangyan.pro.imageList.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.adapter.ImageAdapter;
import com.xxp.yangyan.pro.api.AnalysisHTML;
import com.xxp.yangyan.pro.base.BaseSwipeActivity;
import com.xxp.yangyan.pro.bean.ImageInfo;
import com.xxp.yangyan.pro.gallery.view.GalleryActivity;
import com.xxp.yangyan.pro.imageList.model.Model;
import com.xxp.yangyan.pro.imageList.presenter.Presenter;
import com.xxp.yangyan.pro.listener.BaseOnScrollListener;
import com.xxp.yangyan.pro.utils.ActivityManager;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class ImgLIstActivity extends BaseSwipeActivity<Presenter>
        implements ImageListView<List<ImageInfo>> {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_images)
    RecyclerView rvImages;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private final String TAG = "BaseSwipeActivity";
    private BaseOnScrollListener mListener;
    private ImageAdapter mImageAdapter;
    private List<ImageInfo> mImageInfos;
    private String mType;
    private ProgressDialog mDialog;
    private int mCurrentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        initData();
        initRecylerView();
    }

    private void initRecylerView() {

        mListener = new BaseOnScrollListener<ImageInfo>() {
            @Override
            protected List<ImageInfo> getList() {
                return mImageInfos;
            }

            @Override
            protected RecyclerView getRecylerView() {
                return rvImages;
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            }

            @Override
            public SwipeRefreshLayout getRefreshLayout() {
                return swipe;
            }

            @Override
            protected RecyclerView.Adapter getAdapter() {
                if (mImageAdapter == null) {
                    mImageAdapter = new ImageAdapter(mImageInfos, null, ImgLIstActivity.this);
                }
                return mImageAdapter;
            }


            @Override
            protected void loadData() {
                presenter.loadData(mType, getCurrentPage());
            }
        };

        mImageAdapter.setOnItemClick(new ImageAdapter.OnItemClick() {
            @Override
            public void onClickListener(int position) {
                mCurrentPosition = position;
                if (TextUtils.equals(Model.TYPE_COLLECT, mType)) {
                    startToGallery(mImageInfos);
                } else {
                    mDialog = new ProgressDialog(ImgLIstActivity.this);
                    presenter.loadGallery(mImageInfos.get(position), mDialog);
                }

            }
        });
    }


    private void initData() {
        mType = getIntent().getStringExtra("type") == null ? Model.TYPE_NEW : getIntent().getStringExtra("type");
        tvTitle.setText(mType);
        mImageInfos = new ArrayList<>();
    }


    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipe;
    }

    @Override
    public void onRefresh() {
        mListener.refresh();
    }


    //返回
    @OnClick(R.id.iv_back)
    public void onBackClick() {
        finish();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_list;
    }


    @Override
    public void loadGalleryError(Throwable throwable) {

    }

    @Override
    public void loadGallerySuccess(List<ImageInfo> images) {
        mDialog.dismiss();
        startToGallery(images);
    }

    private void startToGallery(List<ImageInfo> images) {
        Intent intent = new Intent(UIUtils.getContext(), GalleryActivity.class);
        intent.putExtra("gallery", (Serializable) images);
        startActivity(intent);
    }

    @Override
    public void showLoading(boolean type) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable throwable) {
        if (throwable.toString().contains("404")) {
            loadIsEmpty();
            mListener.setEnd(true);
        } else {
            loadError();
        }

    }

    @Override
    public void showData(List<ImageInfo> data) {
        mListener.showImageListToView(data);
    }

    @Override
    protected Presenter bindPresenter() {
        return new Presenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
}
