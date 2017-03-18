package com.xxp.yangyan.pro.utils;

import android.widget.Toast;

import com.xxp.yangyan.pro.App;

/**
 * Created by 钟大爷 on 2017/2/8.
 * Toast类
 */

public class ToastUtils {
    public static void showToast(String content){
        Toast.makeText(App.getmContext(),content,Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(String content){
        Toast.makeText(App.getmContext(),content,Toast.LENGTH_LONG).show();
    }
}
