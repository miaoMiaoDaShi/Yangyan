package com.xxp.yangyan.pro.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xxp.yangyan.pro.App;

/**
 * Created by 钟大爷 on 2017/2/11.
 */

public class PreferenceUtils {
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    //获得sharePreference
    public static SharedPreferences getSP() {
        if (sp == null) {
            sp = App.getmContext()
                    .getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static SharedPreferences.Editor getEditor(){
        if (editor == null) {
            editor = sp.edit();
        }
        return editor;
    }

    public static void commit(){
        if (editor != null) {
            editor.commit();
        }
    }

}
