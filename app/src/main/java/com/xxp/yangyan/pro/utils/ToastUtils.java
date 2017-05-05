package com.xxp.yangyan.pro.utils;

import android.widget.Toast;

import com.xxp.yangyan.pro.App;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description :
 */

public class ToastUtils {
    public static void showToast(String content){
        Toast.makeText(App.getmContext(),content,Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(String content){
        Toast.makeText(App.getmContext(),content,Toast.LENGTH_LONG).show();
    }
}
