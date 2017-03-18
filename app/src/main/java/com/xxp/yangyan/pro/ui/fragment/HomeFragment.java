package com.xxp.yangyan.pro.ui.fragment;

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
import com.xxp.yangyan.pro.App;
import com.xxp.yangyan.pro.api.MMApi;
import com.xxp.yangyan.pro.banner.BannerView;
import com.xxp.yangyan.pro.banner.IBannerPrepare;
import com.xxp.yangyan.pro.base.BaseSwipeFragment;
import com.xxp.yangyan.pro.bean.HomeInfo;
import com.xxp.yangyan.pro.mvp.contract.HomeContract;
import com.xxp.yangyan.pro.mvp.presenter.HomePresenterImpl;
import com.xxp.yangyan.pro.ui.activity.ImgLIstActivity;
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
 * Created by 钟大爷 on 2017/2/3.
 */

public class HomeFragment extends BaseSwipeFragment<HomePresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener, HomeContract.View {
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
    SwipeRefreshLayout swipeRefresh;


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
                ToastUtils.showToast("点击"+position);
            }
        });

    }

    @Override
    protected SwipeRefreshLayout getSwipeRefresh() {
        return swipeRefresh;
    }

    //加载首页的数据
    private void initData() {
        //一进入软件  就制动刷新数据
        presenter.getData();
    }




    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenterImpl onCreatePresenter() {
        return new HomePresenterImpl(this);
    }


    @Override
    public void onRefresh() {
        initData();
    }


    private final String type[] = {"shaonv", "rihandongya", "swmt"};
    private final String title[] = {"shaonv", "rihandongya", "swmt"};

    @OnClick({R.id.iv_push_1, R.id.iv_push_2, R.id.iv_push_3})
    public void onClick(View view) {
        Intent intent = new Intent(UIUtils.getContext(), ImgLIstActivity.class);
        switch (view.getId()) {
            case R.id.iv_push_1:
                intent.putExtra("type", type[0]);
                //intent.putExtra("title",title[0]);
                break;
            case R.id.iv_push_2:
                intent.putExtra("type", type[1]);
                break;
            case R.id.iv_push_3:
                intent.putExtra("type", type[2]);
                break;
        }
        startActivity(intent);
    }


    @Override
    public void setbannerImage(List<String> urls) {
        //设置banner
        for (int i = 1; i <= views.size(); i++) {
            GlideUtils.loadImageView(this,
                    MMApi.MY_BASE_URL + urls.get(i - 1),
                    views.get(i - 1));
        }
    }

    @Override
    public void setNotice(String s) {
        //这个是公告
        tvNotice.setText(s);
    }

    @Override
    public void setPlateImage(List<String> urls) {
        //设置推荐专栏的图片

        List<ImageView> pushs = new ArrayList<>();
        pushs.add(ivPush1);
        pushs.add(ivPush2);
        pushs.add(ivPush3);

        for (int i = 0; i < pushs.size(); i++) {
            GlideUtils.loadImageView(this,
                    MMApi.MY_BASE_URL
                            + urls.get(i),
                    pushs.get(i));
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showResult(HomeInfo data) {
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
        }
    }



    @Override
    public void showSuccess() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showError(Throwable throwable) {
        Log.e(TAG, "showError: " + throwable);
        loadError();
    }

    //加载数据出错时调用
    protected void loadError() {
        swipeRefresh.setRefreshing(false);
        Alerter
                .create(getActivity())
                .setBackgroundColor(R.color.colorPrimary)
                .setDuration(30000)
                .setText("获取重要配置信息出错,软件将自动退出")
                .setTitle("警告")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .show();
        autoExit();
    }

    private void autoExit() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 30 * 1000);
    }
}
