package com.xxp.yangyan.pro.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.adapter.ImageAdapter;
import com.xxp.yangyan.pro.api.AnalysisHTML;
import com.xxp.yangyan.pro.base.BaseSwipeActivity;
import com.xxp.yangyan.pro.bean.ImageInfo;
import com.xxp.yangyan.pro.listener.BaseOnScrollListener;
import com.xxp.yangyan.pro.mvp.contract.ImgListContract;
import com.xxp.yangyan.pro.mvp.presenter.ImgListPresenterImpl;
import com.xxp.yangyan.pro.utils.ActivityManager;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class ImgLIstActivity extends BaseSwipeActivity<ImgListPresenterImpl>
        implements ImgListContract.view {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_images)
    RecyclerView rvImages;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private final String TAG = "BaseSwipeActivity";
    private BaseOnScrollListener listener;
    private ImageAdapter imageAdapter;
    private List<ImageInfo> imageInfos;
    private String type;
    private ProgressDialog mDialog;
    private int currentPosition;

    private boolean isCollect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: 1" );
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: 2" );
        ActivityManager.addActivity(this);
        initData();
        initRecylerView();
    }

    private void initRecylerView() {

        listener = new BaseOnScrollListener<ImageInfo>() {
            @Override
            protected List<ImageInfo> getList() {
                return imageInfos;
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
                if (imageAdapter == null) {
                    imageAdapter = new ImageAdapter(imageInfos, null, ImgLIstActivity.this);
                }
                return imageAdapter;
            }


            @Override
            protected void loadData() {
                if (isCollect) {
                    //是收藏
                    Log.e(TAG, "loadData: 收藏" );
                    presenter.getData();
                } else {
                    Log.e(TAG, "loadData: 不是收藏" );
                    //不是收藏
                    presenter.getData(type, getCurrentPage());
                }
            }
        };

        imageAdapter.setOnItemClick(new ImageAdapter.OnItemClick() {
            @Override
            public void onClickListener(int position) {
                currentPosition = position;
                if(isCollect){
                    startToGallery(imageInfos);
                } else {
                    mDialog = new ProgressDialog(ImgLIstActivity.this);
                    presenter.loadGallery(imageInfos.get(position), mDialog);
                }

            }
        });
    }


    private void initData() {
        type = getIntent().getStringExtra("type");
        if(null == type){
            Log.e(TAG, "initData: getIntent");
            isCollect = true;
        } else {
            tvTitle.setText("");
        }
        Log.e(TAG, "initData: ");
        Log.e(TAG, "setText: ");
        imageInfos = new ArrayList<>();

    }


    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipe;
    }

    @Override
    public void onRefresh() {
        listener.refresh();
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
    protected ImgListPresenterImpl onCreatePresenter() {
        return new ImgListPresenterImpl(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }


    @Override
    public void showResult(ResponseBody data) {
        try {
            listener.showImageListToView(AnalysisHTML.HomePageToList(data.string()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadGalleryError() {
        mDialog.setMessage("阿欧!加载失败");
    }

    @Override
    public void loadGallerySuccess(ResponseBody responseBody) {
        List<ImageInfo> images = new ArrayList<>();
        try {
            images = (AnalysisHTML.ParticularsToList(responseBody.string(),
                    imageInfos.get(currentPosition).getLink()));

            mDialog.dismiss();
            startToGallery(images);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startToGallery(List<ImageInfo> images) {
        Intent intent = new Intent(UIUtils.getContext(), GalleryActivity.class);
        intent.putExtra("gallery", (Serializable) images);
        startActivity(intent);
    }

    @Override
   public void loadLocalDataError() {

    }

    @Override
    public void loadLocalDataSuccess(List<ImageInfo> imageInfos) {
        listener.showImageListToView(imageInfos);
        listener.setEnd(true);
    }



    @Override
    public void loadLocalDataIsEmpty() {
        loadIsEmpty();
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showError(Throwable throwable) {
        if(throwable.toString().contains("404")){
            loadIsEmpty();
            listener.setEnd(true);
        } else {
            loadError();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
}
