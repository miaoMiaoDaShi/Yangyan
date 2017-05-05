package com.xxp.yangyan.pro.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : Activity的管理类
 */

public class ActivityManager {
    private static List<Activity> activitys = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activitys.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activitys.remove(activity);

    }

    public static Activity getTopActivitys() {
        if (!activitys.isEmpty()) {
            return activitys.get(activitys.size() - 1);
        } else {
            return null;
        }
    }

    public static List<Activity> getActivitys(){
        return activitys;
    }

}
