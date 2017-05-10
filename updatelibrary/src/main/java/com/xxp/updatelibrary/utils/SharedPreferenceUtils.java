package com.xxp.updatelibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xxp.updatelibrary.CheckUpdater;

/**
 * Created by 钟大爷 on 2017/3/16.
 */

public class SharedPreferenceUtils {
    private static volatile SharedPreferenceUtils mSharedPreferenceUtils = null;
    public static final String CONSTRAINT_KEY = "CONSTRAINT_KEY";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private SharedPreferenceUtils() {
        sp = CheckUpdater.getInstance().getmContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SharedPreferenceUtils getInstance() {
        if (null == mSharedPreferenceUtils) {
            synchronized (SharedPreferenceUtils.class) {
                if (null == mSharedPreferenceUtils) {
                    mSharedPreferenceUtils = new SharedPreferenceUtils();
                }
            }
        }
        return mSharedPreferenceUtils;
    }

    public void putBoolean(String key,Boolean value){
        editor.putBoolean(key,value);
        editor.apply();
    }

    public boolean getBoolean(String key,Boolean def){
        return sp.getBoolean(key,def);
    }
}
