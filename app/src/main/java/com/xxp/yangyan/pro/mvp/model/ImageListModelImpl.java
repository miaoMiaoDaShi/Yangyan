package com.xxp.yangyan.pro.mvp.model;

import android.text.TextUtils;
import android.util.Log;

import com.xxp.yangyan.pro.App;
import com.xxp.yangyan.pro.api.ApiEngine;
import com.xxp.yangyan.pro.bean.ImageInfo;
import com.xxp.yangyan.pro.mvp.contract.ImgListContract;

import java.util.List;

import rx.Observable;

/**
 * Created by 钟大爷 on 2017/2/13.
 */

public class ImageListModelImpl implements ImgListContract.Model {
    private final String TAG = "ImageListModelImpl";
    @Override
    public Observable getData(String type, int page) {
        if(TextUtils.isEmpty(type)){
            Log.e(TAG, "getData: getHomePage" );
            return ApiEngine.getInstance().getHContentService().getHomePage(page);

        } else if(!TextUtils.isEmpty(type)&&page==0x11){
            Log.e(TAG, "getData: getParticulars" );
            return ApiEngine.getInstance().getHContentService().getParticulars(type);
        } else {
            Log.e(TAG, "getData: getTypePage" );
            return ApiEngine.getInstance().getHContentService().getTypePage(type, page);
        }

    }

    @Override
    public Observable getData() {
        return null;
    }


    @Override
    public List<ImageInfo> getLocalData() {
        return App.getDaoSession().getImageInfoDao().loadAll();
    }
}
