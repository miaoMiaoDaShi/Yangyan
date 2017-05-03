package com.xxp.yangyan.pro.myCenter.view;

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

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.App;
import com.xxp.yangyan.pro.adapter.SettingAdapter;
import com.xxp.yangyan.pro.base.BaseFragment;
import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.bean.SettingInfo;
import com.xxp.yangyan.pro.imageList.view.ImgLIstActivity;
import com.xxp.yangyan.pro.myCenter.presenter.Presenter;
import com.xxp.yangyan.pro.utils.GlideCacheUtil;
import com.xxp.yangyan.pro.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;


/**
 * Created by 钟大爷 on 2017/2/3.
 * 段子
 */

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

    private void initRecylerView() {
        settings = new ArrayList<>();

        SettingInfo clear = new SettingInfo();
        clear.setImage(R.mipmap.ic_clear);
        clear.setTitle("清除缓存");
        clear.setCacheCount(GlideCacheUtil.getInstance().getCacheSize(App.getmContext()));
        settings.add(clear);

        SettingInfo collect = new SettingInfo();
        collect.setImage(R.mipmap.ic_collect);
        collect.setTitle("我的收藏");
        settings.add(collect);

//        SettingInfo setting = new SettingInfo();
//        setting.setImage(R.mipmap.ic_setting);
//        setting.setTitle("软件设置");
//        settings.add(setting);

        SettingInfo update = new SettingInfo();
        update.setImage(R.mipmap.ic_update);
        update.setTitle("检查更新");
        settings.add(update);

        SettingInfo about = new SettingInfo();
        about.setImage(R.mipmap.ic_about);
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
                        startActivity(new Intent(getActivity(), ImgLIstActivity.class));
                        break;
                    case 2:
                        checkUpdate();
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), AboutActivity.class));
                        break;
                    case 4:
                        joinQQGroup(App.QQKEY);
                        break;
                }
            }
        });
    }

    private void checkUpdate() {

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
                settings.get(0).setCacheCount("0.0Byte");
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
    
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

}
