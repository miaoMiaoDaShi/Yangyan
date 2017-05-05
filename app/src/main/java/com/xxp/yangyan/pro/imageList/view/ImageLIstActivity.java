package com.xxp.yangyan.pro.imageList.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.adapter.ImageAdapter;
import com.xxp.yangyan.pro.base.BaseRecyclerViewActivity;
import com.xxp.yangyan.pro.entity.ImageInfo;
import com.xxp.yangyan.pro.gallery.view.GalleryActivity;
import com.xxp.yangyan.pro.imageList.model.Model;
import com.xxp.yangyan.pro.imageList.presenter.Presenter;
import com.xxp.yangyan.pro.utils.ActivityManager;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ImageLIstActivity extends BaseRecyclerViewActivity<Presenter, ImageInfo>
        implements ImageListView<List<ImageInfo>> {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_images)
    RecyclerView rvImages;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private final String TAG = "ImageLIstActivity";
    private ImageAdapter mImageAdapter;
    private List<ImageInfo> mImageInfos;
    /**
     * 加载的类型
     * 1.加载首页的数据(从网络)
     * 2.加载每个类别的数据((从网络))
     * 3.加载收藏的数据(从数据库)
     * 4.加载套图详情数据
     */
    private String mType;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        initData();
        recylerViewClickListener();
    }

    private void recylerViewClickListener() {
        mImageAdapter.setOnItemClick(new ImageAdapter.OnItemClick() {
            @Override
            public void onClickListener(int position) {
                if (TextUtils.equals(Model.TYPE_COLLECT, mType)) {
                    startToGallery(mImageInfos);
                } else {
                    mDialog = new ProgressDialog(ImageLIstActivity.this);
                    mDialog.setTitle("请稍后");
                    mDialog.setMessage("编号: " + mImageInfos.get(position).getLink() + "套图获取中..");
                    mDialog.setIndeterminate(false);
                    mDialog.setCancelable(false);
                    mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.unSubscribe();
                        }
                    });
                    mDialog.show();
                    presenter.loadData(Model.TYPE_PARTICULARS, Integer.parseInt(mImageInfos.get(position).getLink()));
                }

            }
        });
    }

    @Override
    protected RecyclerView getRecylerView() {
        return rvImages;
    }

    @Override
    protected List<ImageInfo> getList() {
        return mImageInfos == null ? new ArrayList<ImageInfo>() : mImageInfos;
    }

    @Override
    protected RecyclerView.Adapter getAdapetr() {
        return mImageAdapter == null ? new ImageAdapter(getList(), null, this) : mImageAdapter;
    }


    private void initData() {
        mType = getIntent().getStringExtra("type");
        tvTitle.setText(mType);
        mImageInfos = getList();
        mImageAdapter = (ImageAdapter) getAdapetr();
    }


    @Override
    public SwipeRefreshLayout getRefreshLayout() {
        return mSwipeRefreshLayout;
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
    protected void loadData() {
        presenter.loadData(mType, getBaseOnScrollListener().getCurrentPage());
    }


    @Override
    public void loadGalleryError(Throwable throwable) {
        Log.e(TAG, "loadGalleryError: ", throwable);
    }

    @Override
    public void loadGallerySuccess(List<ImageInfo> images) {
        mDialog.dismiss();
        startToGallery(images);
    }

    /**
     * 跳转到画廊界面,也就是套图详情界面
     *
     * @param images 传入的套图中的每张图片的连接的结合
     */
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

    /**
     * 因为是抓取数据嘛,当时有个问题就是如何判断数据到底了,也就是最后一页了,,咳咳 ,后来才想到 ,传入的页数如果
     * 访问返回不就没有数据了....
     *
     * @param throwable 失败信息
     */
    @Override
    public void showError(Throwable throwable) {
        Log.e(TAG, "showError: ", throwable);
        //异常信息中含有404,说明数据到底了
        if (throwable.toString().contains("404")) {
            loadIsEmpty();
            //设置自己的那个RecyclerView滚动监听辅助类,数据到底了,不能再滚动加载更多了
            getBaseOnScrollListener().setEnd(true);
        } else {
            //其他的异常,当然也就是加载错误罗
            loadError();
        }

    }

    @Override
    public void showData(List<ImageInfo> data) {
        //显示数据到RecyclerView上
        getBaseOnScrollListener().showImageListToView(data);
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
