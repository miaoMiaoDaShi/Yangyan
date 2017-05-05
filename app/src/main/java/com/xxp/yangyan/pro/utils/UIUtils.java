package com.xxp.yangyan.pro.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.xxp.yangyan.pro.App;


/**
 * Created by 钟大爷 on 2017/3/3.
 * UI工具类
 */

public class UIUtils {
    //////////////上下文///////////////
    public static Context getContext() {
        return App.getmContext();
    }

    //////////////Handler///////////////
    public static Handler getHandler() {
        return App.getmHandler();
    }

    //////////////获取线程主id///////////////
    public static int getMainThreadId() {
        return App.getmMainThreadId();
    }

    //////////////根据id获取String///////////////
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    //////////////根据id取数组///////////////
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    //////////////根据id获取Drawable///////////////
    public static Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(getContext(),id);
    }

    //////////////根据id得到颜色///////////////
    public static int getColor(int id) {
        return ContextCompat.getColor(getContext(),id);
    }

    //////////////根据id取尺寸///////////////
    public static float getDimen(int id) {
        return getContext().getResources().getDimension(id);
    }

    //////////////dp2px///////////////
    public static float dip2Px(float dp) {
        return TypedValue.
                applyDimension(TypedValue.
                        COMPLEX_UNIT_DIP, dp, getContext()
                        .getResources()
                        .getDisplayMetrics());
    }

    //////////////px2dp///////////////
    public static float px2Dip(float px) {
        return px/getContext().getResources().getDisplayMetrics().density;
    }

    //////////////加载布局文件///////////////
    public static View getInflaterView(int id){
        return View.inflate(getContext(),id,null);
    }

    //////////////是否在主线程///////////////
    public static boolean isRunOnUIThread(){
        return getMainThreadId() == Thread.currentThread().getId();
    }

    //////////////保证在主线程运行///////////////
    public static void onUIThread(Runnable r){
        if(isRunOnUIThread()){
            r.run();
        } else {
            getHandler().post(r);
        }
    }
    //////////////获取屏幕宽///////////////
    public static int[] getWidthAndHeight(){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return new int[]{displayMetrics.widthPixels,displayMetrics.heightPixels};
    }

    ////////////Drawable转换为Bitmap
    public static Bitmap DrawableToBitmap(Drawable drawable){
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
       return  ((BitmapDrawable)drawable).getBitmap();
    }

    public static int[] getScreenSize(){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return new int[]{displayMetrics.widthPixels,displayMetrics.heightPixels};
    }
}
