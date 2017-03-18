package com.xxp.yangyan.pro.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.xxp.yangyan.pro.App;

/**
 * Created by 钟大爷 on 2017/2/15.
 */

public class APPInfo {
    public static int getVersionCode(){
        PackageInfo info ;
        PackageManager packageManager = App.getmContext().getPackageManager();
        try {
            info = packageManager.getPackageInfo(App.getmContext().getPackageName(),0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName(){
        PackageInfo info ;
        PackageManager packageManager = App.getmContext().getPackageManager();
        try {
            info = packageManager.getPackageInfo(App.getmContext().getPackageName(),0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
