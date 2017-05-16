package com.xxp.yangyan.pro.myCenter.view;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xxp.updatelibrary.AppUpdater;
import com.xxp.updatelibrary.CheckUpdater;
import com.xxp.updatelibrary.DownloadEngine;
import com.xxp.updatelibrary.DownloadListener;
import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.App;
import com.xxp.yangyan.pro.adapter.SettingAdapter;
import com.xxp.yangyan.pro.api.MyApi;
import com.xxp.yangyan.pro.base.BaseFragment;
import com.xxp.yangyan.pro.entity.SettingInfo;
import com.xxp.yangyan.pro.imageList.model.Model;
import com.xxp.yangyan.pro.imageList.view.ImageLIstActivity;
import com.xxp.yangyan.pro.myCenter.presenter.Presenter;
import com.xxp.yangyan.pro.utils.GlideCacheUtil;
import com.xxp.yangyan.pro.view.CircleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;


public class MyFragment extends BaseFragment<Presenter> {

    @BindView(R.id.photo)
    CircleImageView photo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_)
    TextView tv;
    @BindView(R.id.rv_my)
    RecyclerView rvMy;

    //设置的每项
    private List<SettingInfo> settings;
    private SettingAdapter adapter;
    private BmobUser bmobUser;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initRecylerView();
        autoLogin();
    }

    private void autoLogin() {
        bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            // 允许用户使用应用
            tvName.setText(bmobUser.getUsername());
            tv.setText(bmobUser.getEmail());
        } else {
            //缓存用户对象为空时， 可打开用户注册界面…

        }
    }

    @OnClick(R.id.photo)
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                if (bmobUser == null) {
                    //startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
        }
    }

    /**
     * fragment可见状态发生改变的时候调用
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        /**
         * 每次显示的时候,刷新缓存的数据大小信息
         */
        if (!hidden) {
            if (adapter != null) {
                adapter.notifyItemChanged(0);
            }

        }
    }

    private void initRecylerView() {
        settings = new ArrayList<>();

        SettingInfo clear = new SettingInfo();
        clear.setImage(R.drawable.ic_clear);
        clear.setTitle("清除缓存");
        clear.setCacheCount(GlideCacheUtil.getInstance().getCacheSize(App.getmContext()));
        settings.add(clear);

        SettingInfo collect = new SettingInfo();
        collect.setImage(R.drawable.ic_collect);
        collect.setTitle("我的收藏");
        settings.add(collect);

//        SettingInfo setting = new SettingInfo();
//        setting.setImage(R.drawable.ic_setting);
//        setting.setTitle("软件设置");
//        settings.add(setting);

        SettingInfo update = new SettingInfo();
        update.setImage(R.drawable.ic_update);
        update.setTitle("检查更新");
        settings.add(update);

        SettingInfo about = new SettingInfo();
        about.setImage(R.drawable.ic_about);
        about.setTitle("关于我");
        settings.add(about);

//        SettingInfo join = new SettingInfo();
//        join.setImage(R.mipmap.ic_qgroup);
//        join.setTitle("点击加群");
//        settings.add(join);


        adapter = new SettingAdapter(settings);
        rvMy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvMy.setAdapter(adapter);
        adapter.setOnItemClick(new SettingAdapter.OnItemClick() {
            @Override
            public void onClickListener(int position) {
                switch (position) {
                    case 0:
                        clearGildeCache();
                        break;
                    case 1:
                        Intent intent = new Intent(getActivity(), ImageLIstActivity.class);
                        intent.putExtra(ImageLIstActivity.KEY_TYPE, Model.TYPE_COLLECT);
                        startActivity(intent);
                        break;
                    case 2:
                        checkUpdate();
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), AboutActivity.class));
                        break;
                    case 4:
                        break;
                }
            }
        });
    }

    private void checkUpdate() {
        AppUpdater appUpdater = new AppUpdater
                .Builder(getContext(), MyApi.MY_BASE_URL)
                .setShowToDialog(true)
                .setUserDownloadManager(false)
                .build();
        CheckUpdater.getInstance().init(appUpdater);
    }

    private void clearGildeCache() {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity(), R.style.dialog);
        builder.setTitle("警告");
        builder.setMessage("清除缓存后,所有浏览缓存(不包括下载)的图片都会被删除");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GlideCacheUtil.getInstance().clearImageDiskCache(getActivity());
                settings.get(0).setCacheCount("0.0B");
                adapter.notifyItemChanged(0);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected Presenter bindPresenter() {
        return null;
    }


}
