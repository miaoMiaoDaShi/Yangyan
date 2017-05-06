package com.xxp.yangyan.pro.imageList.presenter;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;

import com.xxp.yangyan.pro.api.AnalysisHTML;
import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.entity.ImageInfo;
import com.xxp.yangyan.pro.imageList.model.Model;
import com.xxp.yangyan.pro.imageList.view.ImageListView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 所有以格子样式显示图片的presenter
 */

public class Presenter extends BasePresenter<Model, ImageListView> {
    private final String TAG = "Presenter";

    public Presenter() {
    }

    @Override
    protected Model bindModel() {
        return new Model();
    }

    /**
     * 加载数据统一调用此方法
     *
     * @param type 加载的类型,分为3类  1.
     * @param page
     */
    public void loadData(final String type, final int page) {
        Log.i(TAG, "loadData: " + "type :" + type + "page :" + page);
        Observer observer = TextUtils.equals(Model.TYPE_COLLECT, type) ?
                new Observer<List<ImageInfo>>() {
                    @Override
                    public void onCompleted() {
                        //提示view加载完成
                        getView().showContent();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, "onError: " + throwable);
                        //图片加载失败
                        if (TextUtils.equals(Model.TYPE_PARTICULARS, type)) {
                            getView().loadGalleryError(throwable);
                        } else {
                            getView().showError(throwable);
                        }
                    }

                    @Override
                    public void onNext(List<ImageInfo> imageInfos) {
                        Log.i(TAG, "onNext: imageinfo size:  " + imageInfos.size());
                        if (TextUtils.equals(Model.TYPE_PARTICULARS, type)) {
                            //显示图片集
                            getView().loadGallerySuccess(imageInfos);
                        } else {
                            //显示加载的结果
                            getView().showData(imageInfos);
                        }
                    }
                } : new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {
                //提示view加载完成
                getView().showContent();
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, "onError: " + throwable);
                //图片加载失败
                if (TextUtils.equals(Model.TYPE_PARTICULARS, type)) {
                    getView().loadGalleryError(throwable);
                } else {
                    getView().showError(throwable);
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                Log.i(TAG, "onNext: ResponseBody :"+responseBody.toString());
                if (TextUtils.equals(Model.TYPE_PARTICULARS, type)) {
                    //显示图片集
                    try {
                        getView().loadGallerySuccess((AnalysisHTML.ParticularsToList(responseBody.string(),
                                String.valueOf(page))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //显示加载的结果
                    try {
                        getView().showData(AnalysisHTML.HomePageToList(responseBody.string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        Subscription subscription = getModel()
                .getData(type, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);

        addSubscribe(subscription);
    }
}
