package com.xxp.yangyan.pro;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.xxp.yangyan.pro.bean.DaoMaster;
import com.xxp.yangyan.pro.bean.DaoSession;
import com.xxp.yangyan.pro.utils.APPInfo;
import com.xxp.yangyan.pro.utils.ActivityManager;
import com.xxp.yangyan.pro.utils.JudgeUtils;

import org.greenrobot.greendao.database.Database;

import java.util.List;

/**
 * Created by 钟大爷 on 2017/2/3.
 */

public class App extends Application {
    public static String QQKEY = "4vpE_9FsVxek-ys2j81KqX5Hw9yx_j7R";
    public static final int xxp  = 1997;
    //第一次进入软件
    public static boolean isFirst = true;
    //上下文
    private static Context mContext;
    //Handler
    private static Handler mHandler;
    //当前线程的Id
    private static int mCurrentThreadId;

    private final String TAG = "App";
    //数据库的名字
    private static final String DB_NAME = "yangyan.db";

    public static int miaomiao;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mCurrentThreadId = (int) Thread.currentThread().getId();
        //判断是不是第一次进入软件
        isFirst = JudgeUtils.isFirst();
        miaomiao = Integer.parseInt(APPInfo.getVersionName().substring(0,1));

        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext,"yangyang.db");
        Database base = helper.getWritableDb();
        DaoMaster master = new DaoMaster(base);
        daoSession = master.newSession();

    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }

    public static Context getmContext() {
        return mContext;
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public static int getmMainThreadId() {
        return mCurrentThreadId;
    }

    public static void exitApp(){
        List<Activity> activities = ActivityManager.getActivitys();
        if(null!=activities){
            if(!activities.isEmpty()){
                for (Activity activity : activities) {
                    activity.finish();
                }
            }
        }
    }
}
