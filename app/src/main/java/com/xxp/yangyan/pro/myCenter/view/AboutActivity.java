package com.xxp.yangyan.pro.myCenter.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xxp.yangyan.R;
import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.pro.base.BaseActivity;
import com.xxp.yangyan.pro.utils.ActivityManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected MvpBasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.ll_about)
    public void onClick() {
        finish();
    }



}
