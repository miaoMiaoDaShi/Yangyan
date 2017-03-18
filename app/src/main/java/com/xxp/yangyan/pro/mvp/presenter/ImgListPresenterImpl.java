package com.xxp.yangyan.pro.mvp.presenter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.xxp.yangyan.pro.bean.ImageInfo;
import com.xxp.yangyan.pro.mvp.contract.ImgListContract;
import com.xxp.yangyan.pro.mvp.model.ImageListModelImpl;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 钟大爷 on 2017/2/13.
 */

public class ImgListPresenterImpl extends ImgListContract.Presenter {

    private final String TAG = "ImgListPresenterImpl";

    public ImgListPresenterImpl(ImgListContract.view view) {
        this.view = view;
        this.model = new ImageListModelImpl();
    }


    @Override
    public void getData(String type, final int page) {
        Subscription subscription = model.getData(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        view.stopLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                        //图片加载失败
                        if (page == 0x11) {
                            view.loadGalleryError();
                        } else view.showError(e);

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (page == 0x11) {
                            //显示图片集
                            view.loadGallerySuccess(responseBody);
                        } else {
                            //提示view加载成功
                            view.showSuccess();
                            //显示加载的结果
                            view.showResult(responseBody);
                        }
                    }
                });
        addSubscribe(subscription);
    }

    @Override
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
        getData(imageInfo.getLink(), 0x11);
    }

    @Override
    public void getData() {
        List<ImageInfo> imageInfos = model.getLocalData();
        if (null == imageInfos) {
            view.loadLocalDataError();
        } else if (imageInfos.isEmpty()) {
            view.loadLocalDataIsEmpty();
        } else {
            view.loadLocalDataSuccess(imageInfos);
        }


    }
}
