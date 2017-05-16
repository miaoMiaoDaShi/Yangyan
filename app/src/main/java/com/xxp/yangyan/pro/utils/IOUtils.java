package com.xxp.yangyan.pro.utils;

import android.Manifest;
import android.os.Environment;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.base.BaseActivity;
import com.xxp.yangyan.pro.listener.RequestPermisListener;

import java.util.List;

/**
 * Created by 钟大爷 on 2017/3/4.
 * 与IO有关发的类
 */

public class IOUtils {
    //返回应用的SD卡存储路劲,绝对
    public static String getSDPath(){
        return Environment
                .getExternalStorageDirectory()
                .getAbsolutePath() + "/奇创喵喵/Yangyan/";
    }

    //下载新的壁纸
    public static void downloadNewImage(final String path, final String url) {
        BaseActivity.requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new RequestPermisListener() {
            @Override
            public void onGranted() {
                RxVolley.download(path, url, null, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                    }
                });
            }

            @Override
            public void onDenide(List<String> permissions) {
                ToastUtils.showLongToast(UIUtils.getString(R.string.permission_error_sd));
            }
        });

    }
}
