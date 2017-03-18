package com.xxp.yangyan.pro.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xxp.yangyan.pro.listener.RequestPermisListener;
import com.xxp.yangyan.pro.utils.ActivityManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 钟大爷 on 2017/2/9.
 * 所有Activity的基类
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P presenter;
    private final String TAG = "BaseActivity";
    private static RequestPermisListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: 5");
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: 6" );
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        if (onCreatePresenter() != null) {
            presenter = onCreatePresenter();
        }

    }

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

    protected abstract int getLayoutId();

    protected abstract P onCreatePresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
