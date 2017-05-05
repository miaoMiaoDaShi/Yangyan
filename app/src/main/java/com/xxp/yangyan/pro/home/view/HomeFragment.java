package com.xxp.yangyan.pro.home.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tapadoo.alerter.Alerter;
import com.xxp.yangyan.R;
import com.xxp.yangyan.mvp.view.MvpLceView;
import com.xxp.yangyan.pro.App;
import com.xxp.yangyan.pro.api.MyApi;
import com.xxp.yangyan.pro.banner.BannerView;
import com.xxp.yangyan.pro.banner.IBannerPrepare;
import com.xxp.yangyan.pro.base.BaseRefreshLayoutFragment;
import com.xxp.yangyan.pro.entity.HomeData;
import com.xxp.yangyan.pro.home.presenter.Presenter;
import com.xxp.yangyan.pro.imageList.view.ImageLIstActivity;
import com.xxp.yangyan.pro.utils.APPInfo;
import com.xxp.yangyan.pro.utils.GlideUtils;
import com.xxp.yangyan.pro.utils.ToastUtils;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 主页
 */

public class HomeFragment extends BaseRefreshLayoutFragment<Presenter>
        implements MvpLceView<HomeData> {
    private final String TAG = "HomeFragment";
    //轮播图
    @BindView(R.id.vp_banner)
    BannerView bannerView;
    @BindView(R.id.iv_push_1)
    ImageView ivPush1;
    @BindView(R.id.iv_push_2)
    ImageView ivPush2;
    @BindView(R.id.iv_push_3)
    ImageView ivPush3;

    //公告
    @BindView(R.id.tv_notice)
    TextView tvNotice;

    @BindView(R.id.home_root)
    RelativeLayout homeRoot;

    private List<ImageView> views;

    private boolean xxp = false;
    //谷歌的刷新空间
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;

    //自动退出,以及显示那个提示框的时长
    private final int SHOW_AND_EXIT_TIME = 30 * 1000;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        loadViewPager();
        if (App.miaomiao == App.xxp - 1996) {
            xxp = true;
            Log.e(TAG, "onViewCreated: " + xxp);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadViewPager() {
        bannerView.setupIbanner(new IBannerPrepare() {
            @Override
            public Activity getActivity() {
                return HomeFragment.this.getActivity();
            }

            @Override
            public void setBannerViews(List<ImageView> views) {
                HomeFragment.this.views = views;
            }
        });

        bannerView.setBannerOnclickListener(new BannerView.OnClickListener() {
            @Override
            public void onClick(int position) {
                ToastUtils.showToast("点击" + position);
            }
        });

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected Presenter bindPresenter() {
        return new Presenter();
    }


    @Override
    public void onRefresh() {
        presenter.loadData();
    }


    private final String mTypes[] = {"shaonv", "rihandongya", "swmt"};

    @OnClick({R.id.iv_push_1, R.id.iv_push_2, R.id.iv_push_3})
    public void onClick(View view) {
        Intent intent = new Intent(UIUtils.getContext(), ImageLIstActivity.class);
        switch (view.getId()) {
            case R.id.iv_push_1:
                intent.putExtra(ImageLIstActivity.KEY_TYPE, mTypes[0]);
                break;
            case R.id.iv_push_2:
                intent.putExtra(ImageLIstActivity.KEY_TYPE, mTypes[1]);
                break;
            case R.id.iv_push_3:
                intent.putExtra(ImageLIstActivity.KEY_TYPE, mTypes[2]);
                break;
        }
        startActivity(intent);
    }


    public void setbannerImage(List<String> urls) {
        //设置banner
        for (int i = 1; i <= views.size(); i++) {
            GlideUtils.loadImageView(this,
                    MyApi.MY_BASE_URL + urls.get(i - 1),
                    views.get(i - 1));
        }
    }

    public void setNotice(String s) {
        //这个是公告
        tvNotice.setText(s);
    }

    public void setPlateImage(List<String> urls) {
        //设置推荐专栏的图片

        List<ImageView> pushs = new ArrayList<>();
        pushs.add(ivPush1);
        pushs.add(ivPush2);
        pushs.add(ivPush3);

        for (int i = 0; i < pushs.size(); i++) {
            GlideUtils.loadImageView(this,
                    MyApi.MY_BASE_URL
                            + urls.get(i),
                    pushs.get(i));
        }
    }


    @Override
    public void showLoading(boolean type) {

    }

    @Override
    public void showContent() {
        getRefreshLayout().setRefreshing(false);
    }

    @Override
    public void showError(Throwable throwable) {
        loadError();
    }

    @Override
    public void showData(HomeData data) {
        String versionName = APPInfo.getVersionName();
        if (!data.getNotUseVersion().contains(versionName) || !xxp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.dialog);
            builder.setTitle("温馨提示:");
            builder.setMessage(String.format("当前版本%s不可用", versionName));
            builder.setCancelable(false);
            builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            builder.show();
        } else {
            setbannerImage(data.getBanner());
            setNotice(data.getNotice());
            setPlateImage(data.getPush());
        }
    }

    @Override
    protected SwipeRefreshLayout getRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    //加载数据出错时调用
    @Override
    protected void loadError() {
        getRefreshLayout().setRefreshing(false);
        Alerter
                .create(getActivity())
                .setBackgroundColor(R.color.colorPrimary)
                .setDuration(SHOW_AND_EXIT_TIME)
                .setText(R.string.load_root_error)
                .setTitle(R.string.warning)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .show();
        autoExit();
    }

    /**
     * 自动退出 时间30秒后
     */
    private void autoExit() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, SHOW_AND_EXIT_TIME);
    }
}
