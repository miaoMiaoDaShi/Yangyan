package com.xxp.yangyan.pro.utils;

import android.util.Log;

import java.util.Calendar;

import static com.xxp.yangyan.pro.utils.PreferenceUtils.getSP;

/**
 * Created by 钟大爷 on 2017/2/3.
 */

public class JudgeUtils {

    private static final String TAG = "JudgeUtils";

    //是不是第一次进入软件
    public static Boolean isFirst() {
        if (getSP().getBoolean("isFirst", true)) {
            PreferenceUtils.getEditor().putBoolean("isFirst", false);
            PreferenceUtils.commit();
            return true;
        } else return false;
    }


    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = calendar.get(Calendar.MONTH) + 1 + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";


        return year + month + day;
    }

    //判断是SplashImg否有更新
    public static boolean isSplashImgUpdate(String date) {
        String saveDate = getSP().getString("splashImg", "");
        if (saveDate.equals(date)) {
            return false;
        } else {
            PreferenceUtils.getEditor().putString("splashImg", date);
            PreferenceUtils.commit();
            return true;
        }
    }

    //判断home图片是否存在更新
    public static boolean isHomeImgUpdate(String date) {
        String saveDate = getSP().getString("homeImg", "");
        if (saveDate.equals(date)) {
            return false;
        } else {
            PreferenceUtils.getEditor().putString("homeImg", date);
            PreferenceUtils.commit();
            return true;
        }
    }


    //判断home图片是否存在更新
    public static boolean isCategoryImgUpdate(String date) {
        String saveDate = getSP().getString("categoryImg", "");
        if (saveDate.equals(date)) {
            return false;
        } else {
            PreferenceUtils.getEditor().putString("categoryImg", date);
            PreferenceUtils.commit();
            return true;
        }
    }

    //判断网络是否可用
    public static boolean netIsAvailable() {
        try {
            Process process = Runtime.getRuntime().exec("ping www.baidu.com");
            if (process.waitFor() == 0) {
                Log.e(TAG, "");
                return true;
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
