package com.xxp.yangyan.pro.ui.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.App;
import com.xxp.yangyan.pro.adapter.GalleryAdapter;
import com.xxp.yangyan.pro.base.BaseActivity;
import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.bean.ImageInfo;
import com.xxp.yangyan.pro.bean.ImageInfoDao;
import com.xxp.yangyan.pro.listener.RequestPermisListener;
import com.xxp.yangyan.pro.ui.view.ViewPager;
import com.xxp.yangyan.pro.utils.ActivityManager;
import com.xxp.yangyan.pro.utils.IOUtils;
import com.xxp.yangyan.pro.utils.SettingUtils;
import com.xxp.yangyan.pro.utils.ToastUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.xxp.yangyan.pro.utils.ToastUtils.showToast;

public class GalleryActivity extends BaseActivity
        implements ViewPager.OnPageChangeListener {

    //图片浏览的ViewPager
    @BindView(R.id.vp_gallery)
    ViewPager vpGallery;
    //弥补状态栏的View
    @BindView(R.id.top)
    View view;
    //图片浏览的索引,格式xx/xx
    @BindView(R.id.tv_galleryCount)
    TextView tvGalleryCount;
    //收藏
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    //下载
    @BindView(R.id.tv_download)
    TextView tvDownload;
    //设置为壁纸
    @BindView(R.id.tv_setwallpaper)
    TextView tvSetwallpaper;
    //顶部的信息栏
    @BindView(R.id.rl_topBar)
    View topBar;
    //底部的操作区Viewgroup
    @BindView(R.id.gallery_operate)
    View galleryOperate;
    @BindView(R.id.activity_gallery)
    RelativeLayout activityGallery;
    //图片的集合
    private List<ImageInfo> images;
    //view集合
    private List<View> views;
    //当前的position
    private int currentPosi = 0;
    private String TAG = "GalleryActivity";

    private boolean isHide = true;

    private PhotoViewAttacher photoViewAttacher;
    private boolean isCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        initView();
        //对viewPager的数据的加载
        initViewPager();

        hideAnim();

        //是否收藏的界面的初始化
        initCollect();

    }

    private void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gallery;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }


    private void initCollect() {
        List<ImageInfo> imageInfos = new ArrayList<>();
        ImageInfoDao imageInfoDao = App.getDaoSession().getImageInfoDao();
        QueryBuilder<ImageInfo> infoQueryBuilder = imageInfoDao.queryBuilder();
        infoQueryBuilder.where(ImageInfoDao.Properties.ImgUrl.eq(images.get(currentPosi).getImgUrl()));
        imageInfos = infoQueryBuilder.list();
        isCollect = !imageInfos.isEmpty();
        if (isCollect) {
            tvCollect.setText("取消收藏");
        } else {
            tvCollect.setText("收藏");
        }
    }


    //show  anim
    private void showAnim() {
        RelativeLayout.LayoutParams topBarlp = (RelativeLayout.LayoutParams) topBar.getLayoutParams();
        topBarlp.setMargins(0, -topBarlp.height, 0, 0);
        topBar.setLayoutParams(topBarlp);
        ObjectAnimator showTopBarAnim = ObjectAnimator
                .ofFloat(topBar, "translationY", 0, 0, 0, topBar.getLayoutParams().height);
        showTopBarAnim.setDuration(1000);
        showTopBarAnim.start();

        RelativeLayout.LayoutParams galleryOperatelp = (RelativeLayout.LayoutParams) galleryOperate.getLayoutParams();
        galleryOperatelp.setMargins(0, 0, 0, -galleryOperatelp.height);
        galleryOperate.setLayoutParams(galleryOperatelp);

        ObjectAnimator showOperateAnim = ObjectAnimator
                .ofFloat(galleryOperate, "translationY", 0, 0, 0, -galleryOperate.getLayoutParams().height);
        showOperateAnim.setDuration(1000);
        showOperateAnim.start();
        isHide = false;
    }

    //hide anim
    private void hideAnim() {
        RelativeLayout.LayoutParams topBarlp = (RelativeLayout.LayoutParams) topBar.getLayoutParams();
        topBarlp.setMargins(0, 0, 0, 0);
        topBar.setLayoutParams(topBarlp);
        ObjectAnimator hideTopBarAnim = ObjectAnimator
                .ofFloat(topBar, "translationY", 0, 0, 0, -topBar.getLayoutParams().height);
        hideTopBarAnim.setDuration(1000);
        hideTopBarAnim.start();

        RelativeLayout.LayoutParams galleryOperatelp = (RelativeLayout.LayoutParams) galleryOperate.getLayoutParams();
        galleryOperatelp.setMargins(0, 0, 0, 0);
        galleryOperate.setLayoutParams(galleryOperatelp);

        ObjectAnimator hindOperateAnim = ObjectAnimator
                .ofFloat(galleryOperate, "translationY", 0, 0, 0, galleryOperate.getLayoutParams().height);
        hindOperateAnim.setDuration(1000);
        hindOperateAnim.start();
        isHide = true;
    }

    //init Gallery View
    private void initViewPager() {
        images = new ArrayList<>();
        views = new ArrayList<>();
        images = (List<ImageInfo>) getIntent().getSerializableExtra("gallery");
        for (int i = 0; i < images.size(); i++) {

            final ImageView imageView = new ImageView(this);
            Glide.with(this).load(images.get(i).getImgUrl()).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    imageView.setImageDrawable(resource);
                    photoViewAttacher = new PhotoViewAttacher(imageView);

                    photoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                        @Override
                        public void onViewTap(View view, float x, float y) {
                            if (isHide) {
                                Log.e(TAG, "onClick: showAnim");
                                showAnim();
                            } else {
                                Log.e(TAG, "onClick: hideAnim");
                                hideAnim();
                            }
                        }
                    });
                }
            });
            views.add(imageView);
        }
        vpGallery.setAdapter(new GalleryAdapter(views));
        vpGallery.addOnPageChangeListener(this);

        vpGallery.setCurrentItem(currentPosi);
        tvGalleryCount.setText(currentPosi + 1 + "/" + views.size());
    }


    //返回按钮
    @OnClick({R.id.iv_back, R.id.tv_collect, R.id.tv_setwallpaper, R.id.tv_download})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_collect:
                if (!isCollect) {
                    Log.e(TAG, "onClick: 收藏"+currentPosi);
                    doCollect();
                } else {
                    notCollect();
                }
                initCollect();

                break;
            case R.id.tv_download:
                //下载的时候申请权限
                BaseActivity.requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new RequestPermisListener() {
                    @Override
                    public void onGranted() {
                        RxVolley.download(
                                IOUtils.getSDPath() + System.currentTimeMillis() + ".jpg"
                                , images.get(currentPosi).getImgUrl(), new ProgressListener() {
                                    @Override
                                    public void onProgress(long transferredBytes, long totalSize) {

                                    }
                                }, new HttpCallback() {
                                    @Override
                                    public void onSuccess(String t) {
                                        showToast("下载成功!");

                                    }

                                    @Override
                                    public void onFailure(VolleyError error) {
                                        showToast("下载失败!");
                                        //tvDownload.setVisibility(View.VISIBLE);
                                    }
                                });
                    }

                    @Override
                    public void onDenide(List<String> permissions) {
                        ToastUtils.showLongToast("获取访问储存卡权限失败,下载失败");
                    }
                });

                break;
            case R.id.tv_setwallpaper:
                ImageView imageView = (ImageView) views.get(vpGallery.getCurrentItem());
                imageView.setDrawingCacheEnabled(true);
                SettingUtils.setWallpaper(imageView.getDrawingCache());
                break;
        }

    }

    private void notCollect() {
        App.getDaoSession().getImageInfoDao().delete(images.get(currentPosi));
    }

    //收藏当前的图片
    private void doCollect() {
        App.getDaoSession().getImageInfoDao().insert(images.get(currentPosi));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentPosi = position;
        initCollect();
        Log.e(TAG, "onPageSelected: " + position);
        tvGalleryCount.setText(position + 1 + "/" + views.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }


}
