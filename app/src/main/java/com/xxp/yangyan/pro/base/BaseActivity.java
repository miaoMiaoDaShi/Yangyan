package com.xxp.yangyan.pro.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.mvp.view.impl.MvpActivity;
import com.xxp.yangyan.pro.listener.RequestPermisListener;
import com.xxp.yangyan.pro.utils.ActivityManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 项目的activity的基类
 */

public abstract class BaseActivity<P extends MvpBasePresenter> extends MvpActivity<P> {
    private final String TAG = "BaseActivity";
    private static RequestPermisListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);

    }

    /**
     * 权限的申请
     *
     * @param permis
     * @param listener
     */
    public static void requestPermission(String permis[], RequestPermisListener listener) {
        mListener = listener;
        List<String> permissions = new ArrayList<>();
        Activity activity = ActivityManager.getTopActivitys();
        for (int i = 0; i < permis.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, permis[i]) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(permis[i]);
            }
        }
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), 1);
        } else {
            listener.onGranted();
        }
    }

    /**
     * 权限申请结果的处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> denidePermission = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            denidePermission.add(permission);
                        }
                    }

                    if (denidePermission.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenide(denidePermission);
                    }
                }

                break;
        }
    }

    /**
     * @return 返回视图的布局id
     */
    protected abstract int getLayoutId();
}
