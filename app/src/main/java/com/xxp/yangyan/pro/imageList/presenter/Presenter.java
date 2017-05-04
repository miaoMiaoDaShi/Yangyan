package com.xxp.yangyan.pro.imageList.presenter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;

import com.xxp.yangyan.pro.api.AnalysisHTML;
import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.bean.ImageInfo;
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
 * Description :
 */

public class Presenter extends BasePresenter<Model, ImageListView> {
    private final String TAG = "Presenter";

    public Presenter() {
    }

    @Override
    protected Model bindModel() {
        return new Model();
    }

    public void loadData(final String type, final int page) {

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

    public void loadGallery(ImageInfo imageInfo, ProgressDialog dialog) {
        dialog.setTitle("请稍后");
        dialog.setMessage("编号: " + imageInfo.getLink() + "套图获取中..");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                unSubscribe();
            }
        });
        dialog.show();
        loadData(Model.TYPE_PARTICULARS, Integer.parseInt(imageInfo.getLink()));
    }


}
