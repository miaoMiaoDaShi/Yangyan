package com.xxp.yangyan.pro.utils;

import android.app.WallpaperManager;
import android.graphics.Bitmap;

import java.io.IOException;

import static com.xxp.yangyan.pro.utils.ToastUtils.showToast;
import static com.xxp.yangyan.pro.utils.UIUtils.getContext;
import static com.xxp.yangyan.pro.utils.UIUtils.onUIThread;

/**
 * Created by 钟大爷 on 2017/3/23.
 */

public class SettingUtils {
    ////////////设置桌面壁纸
    public static void setWallpaper(final Bitmap bitmap){
        showToast("喵喵正在为您设置壁纸");
        new Thread(new Runnable() {
            @Override
            public void run() {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
                try {
                    wallpaperManager.setBitmap(bitmap);
                    onUIThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("设置成功啦!");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
