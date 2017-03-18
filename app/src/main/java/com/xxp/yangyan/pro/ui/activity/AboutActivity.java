package com.xxp.yangyan.pro.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.utils.ActivityManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        ActivityManager.addActivity(this);
    }

    @OnClick(R.id.ll_about)
    public void onClick() {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
}
