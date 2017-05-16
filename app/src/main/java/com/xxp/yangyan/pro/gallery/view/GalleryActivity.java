package com.xxp.yangyan.pro.gallery.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
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
import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.pro.App;
import com.xxp.yangyan.pro.adapter.GalleryAdapter;
import com.xxp.yangyan.pro.base.BaseActivity;
import com.xxp.yangyan.pro.entity.ImageInfo;
import com.xxp.yangyan.pro.entity.ImageInfoDao;
import com.xxp.yangyan.pro.imageList.model.Model;
import com.xxp.yangyan.pro.listener.RequestPermisListener;
import com.xxp.yangyan.pro.utils.IOUtils;
import com.xxp.yangyan.pro.utils.JudgeUtils;
import com.xxp.yangyan.pro.utils.SettingUtils;
import com.xxp.yangyan.pro.utils.ToastUtils;
import com.xxp.yangyan.pro.view.ViewPager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    //收藏的动画效果
    @BindView(R.id.iv_bg_love)
    ImageView ivBgLove;
    @BindView(R.id.iv_img_love)
    ImageView ivImgLove;
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
    private int mCurrentPosi = 0;
    private String TAG = "GalleryActivity";

    private boolean isHide = true;

    //获取intent传过来的索引值得key
    public static final String KEY_POSITION = "KEY_POSITION";
    //获取intent传过来的图片信息集合得key
    public static final String KEY_IMAGEINFOS = "KEY_IMAGEINFOS";

    private PhotoViewAttacher photoViewAttacher;
    private boolean isCollect;

    //打开此界面的操作来源(收藏界面,从网络加载套图)
    private String mAction;
    private GalleryAdapter mAdapter;

    //打开此画廊界面有两种情况,一种是网络套图浏览,一种是自己收藏的图片浏览
    public static final String ACTION_COLLECT = "ACTION_COLLECT";
    public static final String ACTION_PICTURES = "ACTION_PICTURES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        //对viewPager的数据的加载
        initViewPager();
        hideAnim();
        //是否收藏的界面的初始化
        refreshCollectStatus();

    }

    private void initData() {
        //获得传过来的图片集合
        images = (List<ImageInfo>) getIntent().getSerializableExtra(KEY_IMAGEINFOS);
        mAction = getIntent().getAction();
        //获得前面点击的索引值
        mCurrentPosi = getIntent().getIntExtra(KEY_POSITION, 0);
        views = new ArrayList<>();
    }

    private void initView() {
        ivBgLove.setVisibility(View.GONE);
        ivImgLove.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gallery;
    }


    private void refreshCollectStatus() {
        List<ImageInfo> imageInfos;
        ImageInfoDao imageInfoDao = App.getDaoSession().getImageInfoDao();
        QueryBuilder<ImageInfo> infoQueryBuilder = imageInfoDao.queryBuilder();
        //根据连接从从数据库中查询该图片
        infoQueryBuilder.where(ImageInfoDao.Properties.ImgUrl.eq(images.get(mCurrentPosi).getImgUrl()));
        imageInfos = infoQueryBuilder.list();
        //如果集合不为空,则代表该图片为收藏状态
        isCollect = !imageInfos.isEmpty();
        if (isCollect) {
            ivImgLove.setImageResource(R.drawable.ic_loved);
            ivCollect.setImageResource(R.drawable.ic_loved);
            tvCollect.setText("取消收藏");
        } else {
            ivImgLove.setImageResource(R.drawable.ic_love);
            ivCollect.setImageResource(R.drawable.ic_love);
            tvCollect.setText("收藏");
        }

        //中间的桃心动画
        animatePhotoLike(new View[]{ivBgLove, ivImgLove});
    }

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    Map<View[], AnimatorSet> likeAnimations = new HashMap<>();

    private void animatePhotoLike(final View viweAnim[]) {
        if (!likeAnimations.containsKey(viweAnim)) {
            viweAnim[0].setVisibility(View.VISIBLE);
            viweAnim[1].setVisibility(View.VISIBLE);

            viweAnim[0].setScaleY(0.1f);
            viweAnim[0].setScaleX(0.1f);
            viweAnim[0].setAlpha(1f);
            viweAnim[1].setScaleY(0.1f);
            viweAnim[1].setScaleX(0.1f);

            AnimatorSet animatorSet = new AnimatorSet();
            likeAnimations.put(viweAnim, animatorSet);


            ObjectAnimator bgScaleYAnim = ObjectAnimator.ofFloat(viweAnim[0], "scaleY", 0.1f, 1f);
            bgScaleYAnim.setDuration(200);
            bgScaleYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
            ObjectAnimator bgScaleXAnim = ObjectAnimator.ofFloat(viweAnim[0], "scaleX", 0.1f, 1f);
            bgScaleXAnim.setDuration(200);
            bgScaleXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
            ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(viweAnim[0], "alpha", 1f, 0f);
            bgAlphaAnim.setDuration(200);
            bgAlphaAnim.setStartDelay(150);
            bgAlphaAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(viweAnim[1], "scaleY", 0.1f, 1f);
            imgScaleUpYAnim.setDuration(300);
            imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
            ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(viweAnim[1], "scaleX", 0.1f, 1f);
            imgScaleUpXAnim.setDuration(300);
            imgScaleUpXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(viweAnim[1], "scaleY", 1f, 0f);
            imgScaleDownYAnim.setDuration(300);
            imgScaleDownYAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
            ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(viweAnim[1], "scaleX", 1f, 0f);
            imgScaleDownXAnim.setDuration(300);
            imgScaleDownXAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

            animatorSet.playTogether(bgScaleYAnim, bgScaleXAnim, bgAlphaAnim, imgScaleUpYAnim, imgScaleUpXAnim);
            animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim);

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    viweAnim[0].setVisibility(View.GONE);
                    viweAnim[1].setVisibility(View.GONE);
                }
            });
            animatorSet.start();
        }
    }

    //显示上下的操作条
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

    //隐藏上下的操作条
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

    //画廊的初始化
    private void initViewPager() {
        for (int i = 0; i < images.size(); i++) {

            final ImageView imageView = new ImageView(this);
            Glide.with(this).load(images.get(i).getImgUrl()).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    imageView.setImageDrawable(resource);
                    photoViewAttacher = new PhotoViewAttacher(imageView);

                    photoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            collectJudge();
                            return true;
                        }
                    });
                    photoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                        @Override
                        public void onViewTap(View view, float x, float y) {
                            if (isHide) {
                                showAnim();
                            } else {
                                hideAnim();
                            }
                        }
                    });
                }
            });
            views.add(imageView);
        }
        mAdapter = new GalleryAdapter(views);
        vpGallery.setAdapter(mAdapter);
        vpGallery.addOnPageChangeListener(this);

        vpGallery.setCurrentItem(mCurrentPosi);
        tvGalleryCount.setText(mCurrentPosi + 1 + "/" + views.size());
    }

    //移除和添加收藏的逻辑
    private void collectJudge() {
        if (!isCollect) {
            addToCollect();
        } else {
            removeToCollect();
        }
        // TODO: 2017/5/9
        //视图集合为空了,也没必要再刷新收藏的状态
        if (views.isEmpty()) {
            return;
        }
        //刷新当前图片的收藏状态
        refreshCollectStatus();
    }

    //返回按钮
    @OnClick({R.id.iv_back, R.id.ll_collect, R.id.ll_setwallpaper, R.id.ll_download})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_collect:
                collectJudge();
                break;
            case R.id.ll_download:
                //下载的时候申请权限
                BaseActivity.requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new RequestPermisListener() {
                    @Override
                    public void onGranted() {
                        RxVolley.download(
                                IOUtils.getSDPath() + System.currentTimeMillis() + ".jpg"
                                , images.get(mCurrentPosi).getImgUrl(), new ProgressListener() {
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
            case R.id.ll_setwallpaper:
                ImageView imageView = (ImageView) views.get(vpGallery.getCurrentItem());
                imageView.setDrawingCacheEnabled(true);
                SettingUtils.setWallpaper(imageView.getDrawingCache());
                break;
        }

    }

    //移除当前图片

    /**
     * 取消收藏有两种情况,一种是在浏览套图时,还有一种是浏览收藏的界面时.
     * 基于第一种情况.我们取消收藏的时候,并不会将其从视图中移除,
     * 而第二种情况,我们不仅要将其中数据库移除,而且还应该将次视图移除
     * 基于移除的情况:1.用户取消收藏的是最后一张图片,此时假如集合中的图片一共只有一张,那么当用户将其
     * 取消收藏时,我们应该直接退出此界面,如图片集合的大小大于1张,那么将当前的索引赋值为取消收藏后
     * 的图片集合的大小-1;
     */
    private void removeToCollect() {
        Log.e(TAG, "removeToCollect: ");
        //首先从数据库移除
        App.getDaoSession().getImageInfoDao().delete(images.get(mCurrentPosi));
        //打开该视图的意图为收藏图片浏览功能
        if (TextUtils.equals(ACTION_COLLECT, mAction)) {
            Log.e(TAG, "removeToCollect: 2");
            //从视图集合中移除
            views.remove(mCurrentPosi);
            //从数据集合里移除
            images.remove(mCurrentPosi);
            //如果当前集合为空了,直接退出
            if (views.isEmpty()) {
                Log.e(TAG, "removeToCollect: views.isEmpty()");
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                return;
            }
            //如果是位于最后一张,且当前的总数大于1
            if (mCurrentPosi == views.size() && views.size() > 0) {
                mCurrentPosi = views.size() - 1;
                Log.e(TAG, "removeToCollect: " + mCurrentPosi);
            } else {
                Log.e(TAG, "removeToCollect: else" + mCurrentPosi);
            }

            //刷新界面
            mAdapter.notifyDataSetChanged();
            tvGalleryCount.setText(mCurrentPosi + 1 + "/" + views.size());
        }
    }

    //收藏当前的图片
    private void addToCollect() {
        //为当前的图片设置标题为当前的日期
        images.get(mCurrentPosi).setTitle(JudgeUtils.getDateString());
        //添加当前的图片信息到数据库中
        App.getDaoSession().getImageInfoDao().insert(images.get(mCurrentPosi));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.e(TAG, "onPageSelected: ");
        ToastUtils.showToast("当前index" + position);
        mCurrentPosi = position;
        refreshCollectStatus();
        tvGalleryCount.setText(position + 1 + "/" + views.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected MvpBasePresenter bindPresenter() {
        return null;
    }


    public static void startActivity(Activity activity, String action, List<ImageInfo> imageInfos, int position) {
        Intent intent = new Intent(activity, GalleryActivity.class);
        intent.setAction(action);
        intent.putExtra(KEY_IMAGEINFOS, (Serializable) imageInfos);
        intent.putExtra(KEY_POSITION, position);
        activity.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, int requestCode, String action, List<ImageInfo> imageInfos, int position) {
        Intent intent = new Intent(activity, GalleryActivity.class);
        intent.setAction(action);
        intent.putExtra(KEY_IMAGEINFOS, (Serializable) imageInfos);
        intent.putExtra(KEY_POSITION, position);
        activity.startActivityForResult(intent, requestCode);
    }


}
