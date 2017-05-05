package com.xxp.yangyan.pro.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.xxp.yangyan.pro.App;

import java.io.File;
import java.math.BigDecimal;


/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/3/16
 * Description : Gilded缓存处理类
 */

public class GlideCacheUtil {
    private static volatile GlideCacheUtil glideCacheUtil;

    public static GlideCacheUtil getInstance() {
        if (glideCacheUtil == null) {
            synchronized (GlideCacheUtil.class) {
                if (glideCacheUtil == null) {
                    glideCacheUtil = new GlideCacheUtil();
                }
            }
        }
        return glideCacheUtil;
    }

    /**
     * 清除图片磁盘缓存
     */
    public void clearImageDiskCache(Context context) {
        final ProgressDialog mDialog;
        mDialog = new ProgressDialog(context);
        mDialog.setTitle("请稍后");
        mDialog.setMessage("正在清理缓存!");
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(false);
        mDialog.show();
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(App.getmContext()).clearDiskCache();
                        UIUtils.onUIThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast("清理完成");
                                mDialog.dismiss();
                            }
                        });
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
                mDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */


    private long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    private void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getFormatSize(double size){
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        return "数据太大!";
    }
}
