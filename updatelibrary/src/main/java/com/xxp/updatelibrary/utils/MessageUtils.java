package com.xxp.updatelibrary.utils;

import android.content.Context;
import android.widget.Toast;

import com.xxp.updatelibrary.CheckUpdater;

/**
 * Created by 钟大爷 on 2017/3/15.
 */

public class MessageUtils {
    public static Context getContext() {
        return CheckUpdater.getInstance().getmContext();
    }

    //显示吐司
    public static void showToast(String title) {
        Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();

    }

    //显示吐司
    public static void showLongToast(String title) {
        Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();

    }

}
