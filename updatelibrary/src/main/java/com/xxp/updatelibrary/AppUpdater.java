package com.xxp.updatelibrary;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by 钟大爷 on 2017/3/15.
 * 更新组建对象
 * <p>
 * 格式要求
 * {
 * "versionCode":2,
 * "versionName":"1.02",
 * "describe":"新版本新体验!",
 * "constraint":false,
 * "url":"http://a3.pc6.com/cx/72xiaoshiapp.pc6.apk"
 * }
 *
 * bug--使用DownloadManager时,回调进度异常,强制更新完善异常
 * 需求待定:1.下载状态的回调
 *         2.文件存储路径
 *         3.安装成功后,自动删除安装包
 *         4.用户有权跳过此版本
 *         5.当下载任务在后台进行时,下载完成后,是否自动弹出安装界面.
 */

public class AppUpdater {
    //上下文参数
    private final Context context;
    //状态栏显示
    private final boolean showToStatusBar;
    //对话框显示(默认为该放式显示)
    private final boolean showToDialog;
//    //强制更新(用户必须更新此版本后,才能继续使用)
//    private final boolean constraint;
    //更新后自动删除安装包
    private final boolean autoDel;
    //下载方式采用系统的DownloadManager
    private final boolean userDownloadManager;
    //是否使用库默认更新提示对话框
    private final boolean defaultDialog;
    //存储的地址
    private final String filePath;
    //文件的名字
    private final String appName;

    //地址
    private final String url;

    private AppUpdater(Builder builder) {
        context = builder.context;
        showToDialog = builder.showToDialog;
        showToStatusBar = builder.showToStatusBar;
       // constraint = builder.constraint;
        autoDel = builder.autoDel;
        userDownloadManager = builder.userDownloadManager;
        url = builder.url;
        defaultDialog = builder.defaultDialog;
        filePath = builder.filePath;
        appName = builder.appName;
    }

    @Override
    public String toString() {
        return "AppUpdater{" +
                "context=" + context +
                ", showToStatusBar=" + showToStatusBar +
                ", showToDialog=" + showToDialog +
                ", autoDel=" + autoDel +
                ", userDownloadManager=" + userDownloadManager +
                ", defaultDialog=" + defaultDialog +
                ", filePath='" + filePath + '\'' +
                ", appName='" + appName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getFilePath() {
        return filePath;
    }

    public String getAppName() {
        return appName;
    }

    public Context getContext() {
        return context;
    }

    public boolean isDefaultDialog() {
        return defaultDialog;
    }

    public boolean isShowToStatusBar() {
        return showToStatusBar;
    }

    public boolean isShowToDialog() {
        return showToDialog;
    }

//    public boolean isConstraint() {
//        return constraint;
//    }

    public boolean isAutoDel() {
        return autoDel;
    }

    public boolean isUserDownloadManager() {
        return userDownloadManager;
    }

    public String getUrl() {
        return url;
    }

    public static class Builder {
        //上下文参数
        private Context context;
        //状态栏显示
        private boolean showToStatusBar = false;
        //对话框显示(默认为该放式显示)
        private boolean showToDialog = true;
//        //强制更新(用户必须更新此版本后,才能继续使用)
//        private boolean constraint = false;
        //更新后自动删除安装包
        private boolean autoDel = false;
        //下载方式采用系统的DownloadManager
        private boolean userDownloadManager = false;
        //是否使用库默认更新提示对话框
        private boolean defaultDialog = true;
        //存储的地址
        private String filePath = "/Download";
        //文件的名字
        private String appName = "newApp.apk";
        //地址
        private String url;

        public Builder(Context context, String url) {
            this.context = context;
            this.url = url;
        }

//        public Builder setFilePath(String filePath) {
//            this.filePath = filePath;
//            return this;
//        }

        public Builder setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public Builder setShowToStatusBar(boolean showToStatusBar) {
            this.showToStatusBar = showToStatusBar;
            return this;
        }

        public Builder setShowToDialog(boolean showToDialog) {
            this.showToDialog = showToDialog;
            return this;
        }

//        public Builder setConstraint(boolean constraint) {
//            this.constraint = constraint;
//            return this;
//        }

//        public Builder setAutoDel(boolean autoDel) {
//            this.autoDel = autoDel;
//            return this;
//        }

        public Builder setUserDownloadManager(boolean userDownloadManager) {
            this.userDownloadManager = userDownloadManager;
            return this;
        }

//        public Builder setDefaultDialog(boolean defaultDialog) {
//            this.defaultDialog = defaultDialog;
//            return this;
//        }

        public AppUpdater build() {
            //判断
            this.appName = this.appName == null ? getMainAppName() : this.appName;
            return new AppUpdater(this);
        }

        //获取主app的name
        private String getMainAppName() {
            try {
                String appName = "";
                PackageManager packageManager = context.getPackageManager();
                ApplicationInfo info = packageManager.getApplicationInfo(context.getPackageName(), 0);
                appName = (String) packageManager.getApplicationLabel(info);
                return appName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return "newApp";
        }
    }

}
