package com.xxp.updatelibrary;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.xxp.updatelibrary.entity.UpdateInfo;
import com.xxp.updatelibrary.utils.MessageUtils;
import com.xxp.updatelibrary.utils.SharedPreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by 钟大爷 on 2017/3/15.
 * 该库的入口类
 */

public class CheckUpdater {

    private AppUpdater mAppUpdater;
    private Context mContext;

    private final int INIT_WHAT = 0x11;
    private final int CONSTRAINT_WHAT = 0x12;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INIT_WHAT:
                    if (null != msg.obj) {
                        Log.i("获取的json文件字符", msg.toString());
                        //判断是否需要更新
                        String json = msg.obj.toString();
                        resolverJson(json);
                    }
                    break;
                //强制更新
                case CONSTRAINT_WHAT:
                    System.exit(0);
                    break;
            }
        }
    };

    //私有化构造函数
    private CheckUpdater() {
    }

    //解析json
    private void resolverJson(String json) {
        UpdateInfo updateInfo = new UpdateInfo();
        try {
            JSONObject jb = new JSONObject(json);
            updateInfo.setVersionCode(jb.getInt("versionCode"));
            updateInfo.setVersionName(jb.getString("versionName"));
            updateInfo.setDescribe(jb.getString("describe"));
            updateInfo.setConstraint(jb.getBoolean("constraint"));
            updateInfo.setUrl(jb.getString("url"));
            //强制更新
            checkConstraintUpdate(updateInfo);
            //判断版本
            judgeVersion(updateInfo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //同过后台返回的数据,判断该版本是否需要强制更新
    private void checkConstraintUpdate(UpdateInfo updateInfo) {
        if(updateInfo.isConstraint()){
            SharedPreferenceUtils.getInstance().putBoolean(SharedPreferenceUtils.CONSTRAINT_KEY,true);
        } else {
            SharedPreferenceUtils.getInstance().putBoolean(SharedPreferenceUtils.CONSTRAINT_KEY,false);
        }
    }

    //执行强制更新的后续操作
    private void isConstraintUpdate(){
        int delayedTime = 5*60*1000;
        if(SharedPreferenceUtils.getInstance().getBoolean(SharedPreferenceUtils.CONSTRAINT_KEY,false)){
            mHandler.sendMessageDelayed(mHandler.obtainMessage(CONSTRAINT_WHAT),delayedTime);
            MessageUtils.showLongToast(delayedTime/60/1000+"分后将退出软件,请更新软件");
        }
    }


    //获取实例
    public static CheckUpdater getInstance() {
        return CheckUpdateInstance.checkUpdater;
    }

    //显示更新提示信息
    private void showUpdateMsg(final UpdateInfo updateInfo) {
        AlertDialog alertDialog = new AlertDialog
                .Builder(mContext)
                .setCancelable(false)
                .setTitle("新版本" + updateInfo.getVersionName())
                .setMessage(updateInfo.getDescribe())
                .setNegativeButton("取消", null)
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //用户同意升级了,移除信息
                        mHandler.removeMessages(CONSTRAINT_WHAT);
                        doUpdateApp(updateInfo.getUrl());
                    }
                })
                .show();
    }

    //执行更新
    private void doUpdateApp(String url) {
        //使用系统的DownloadManager进行下载
        if (mAppUpdater.isUserDownloadManager()) {
            IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            mContext.registerReceiver(new CompleteReceiver(), filter);

            DownloadEngine.systemDownload(url, new DownloadListener() {
                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onDownloading(final int pro) {
                    //判断是不是在主线程
                    if(Looper.myLooper()!=Looper.getMainLooper()){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                showProgress(pro);
                            }
                        });
                    } else {
                        showProgress(pro);
                    }
                }

                @Override
                public void onSuccess(File file) {

                }
            });
        } else {
            //库方法下载
            DownloadEngine.Librarydownload(url, new DownloadListener() {
                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onDownloading(final int pro) {
                    //判断是否在主线程
                    if(Looper.myLooper()!=Looper.getMainLooper()){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                showProgress(pro);
                            }
                        });
                    } else {
                        showProgress(pro);
                    }

                }

                @Override
                public void onSuccess(File file) {
                    //执行安装
                    setupApp(file);
                }
            });
        }
    }

    //展示下载进度
    private void showProgress(int pro) {
        //进度在状态栏显示
        if (mAppUpdater.isShowToStatusBar()&&!mAppUpdater.isUserDownloadManager()) {
            showProToStatusBar(pro);
        }
        //进度在对话框显示
        if (mAppUpdater.isShowToDialog()){
            Log.e(TAG,"对话框显示");
            showProToDialog(pro);
        }
    }

    private ProgressDialog proDialog = null;
    //对话框方式显示
    private void showProToDialog(int pro) {
        if(null==proDialog){
            proDialog = new ProgressDialog(mContext);
            proDialog.setTitle("正在下载中!");
            proDialog.setMax(100);
            proDialog.setCancelable(false);
//            proDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    //取消下载
//                    isConstraintUpdate();
//                }
//            });
            proDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "后台下载", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //后台下载,不管有没有开启状态栏显示  为了用体验,开启状态栏显示
                    showProToStatusBar(proDialog.getProgress());
                    MessageUtils.showToast("正在后台下载");
                }
            });
            proDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            proDialog.show();
        }
        if (proDialog.isShowing()){
            if(pro==100){
                proDialog.dismiss();
            }
            proDialog.setProgress(pro);

        } else {
            proDialog.show();
        }



    }

    private NotificationManager manager = null;
    private NotificationCompat.Builder builder;
    //在状态栏显示进度
    private void showProToStatusBar(int pro) {
        if (null==manager){
            manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            builder = (NotificationCompat.Builder) new NotificationCompat.Builder(mContext)
                    .setContentTitle(mAppUpdater.getAppName()+"正在下载..")
                    .setSmallIcon(getMainAppIcon())
                    .setTicker(mAppUpdater.getAppName()+"软件更新中")
                    .setProgress(100,pro,false);
            manager.notify(1,builder.build());
        } else {
            builder.setContentText(pro+"%");
            builder.setProgress(100,pro,false);
            manager.notify(1,builder.build());
        }

    }

    //获取主APP的图标
    private int getMainAppIcon() {
        PackageManager manager = mContext.getPackageManager();
        try {
            ApplicationInfo info = manager.getApplicationInfo(mContext.getPackageName(),0);
            return info.icon;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //安装软件
    private void setupApp(File file) {
        mHandler.removeMessages(CONSTRAINT_WHAT);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(Uri.parse("file://" + file), type);
        mContext.startActivity(intent);
    }


    //判断是否需要更新
    private void judgeVersion(UpdateInfo updateInfo) {
        if (getLocalVersionCode() < updateInfo.getVersionCode()) {
            showUpdateMsg(updateInfo);
        }
    }

    //检测更新 初始化开始类
    public void init(AppUpdater appUpdater) {
        mAppUpdater = appUpdater;
        mContext = mAppUpdater.getContext();
        Log.e(TAG, "init: "+mAppUpdater.toString());
        isConstraintUpdate();
        Thread thread = new Thread() {
            @Override
            public void run() {
                //从服务器获取版本号
                initUpdateFromNet();
            }
        };
        thread.start();
    }

    //从服务器获取更新相关的json文件信息
    private void initUpdateFromNet() {
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        try {
            connection = (HttpURLConnection) new URL(mAppUpdater.getUrl()).openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bufferedReader =
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String len = "";
                StringBuilder content = new StringBuilder();
                while (null != (len = bufferedReader.readLine())) {
                    content.append(len);
                }
                mHandler.sendMessage(mHandler.obtainMessage(INIT_WHAT, content.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    //获取本地的版本号
    public int getLocalVersionCode() {
        PackageManager manager = mAppUpdater.getContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(mAppUpdater.getContext().getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //获取该类的实例
    private static class CheckUpdateInstance {
        private static CheckUpdater checkUpdater = new CheckUpdater();
    }

    public Context getmContext() {
        return mContext;
    }

    public AppUpdater getmAppUpdater() {
        return mAppUpdater;
    }

    //下载完成的监听(系统的DownloadManager)
    private class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //系统的DownloadManager下载完成
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                Log.i(TAG, "onReceive: " + mAppUpdater.getFilePath() + File.separator + mAppUpdater.getAppName());
                setupApp(new File(Environment
                        .getExternalStorageDirectory()
                        .getAbsolutePath()
                        + mAppUpdater
                        .getFilePath() +
                        File.separator
                        + mAppUpdater
                        .getAppName()));
            }
        }
    }


}
