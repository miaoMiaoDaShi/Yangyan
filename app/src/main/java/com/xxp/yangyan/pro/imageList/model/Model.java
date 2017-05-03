package com.xxp.yangyan.pro.imageList.model;

import android.text.TextUtils;
import android.util.Log;

import com.xxp.yangyan.mvp.model.MvpModel;
import com.xxp.yangyan.pro.api.ApiEngine;

import rx.Observable;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description :
 */

public class Model implements MvpModel {
    private final String TAG = "ImageListModelImpl";

    public Observable getData(String type, int page) {
        if (TextUtils.isEmpty(type)) {
            Log.e(TAG, "getData: getHomePage");
            return ApiEngine.getInstance().getHContentService().getHomePage(page);

        } else if (!TextUtils.isEmpty(type) && page == 0x11) {
            Log.e(TAG, "getData: getParticulars");
            return ApiEngine.getInstance().getHContentService().getParticulars(type);
        } else {
            Log.e(TAG, "getData: getTypePage");
            return ApiEngine.getInstance().getHContentService().getTypePage(type, page);
        }

    }
}
