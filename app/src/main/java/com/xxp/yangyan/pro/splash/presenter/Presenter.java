package com.xxp.yangyan.pro.splash.presenter;

import android.Manifest;
import android.util.Log;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.App;
import com.xxp.yangyan.pro.base.BaseActivity;
import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.entity.SplashInfo;
import com.xxp.yangyan.pro.listener.RequestPermisListener;
import com.xxp.yangyan.pro.splash.model.Model;
import com.xxp.yangyan.pro.splash.view.SplashActivity;
import com.xxp.yangyan.pro.utils.IOUtils;
import com.xxp.yangyan.pro.utils.JudgeUtils;
import com.xxp.yangyan.pro.utils.ToastUtils;
import com.xxp.yangyan.pro.utils.UIUtils;

import java.util.List;

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

public class Presenter extends BasePresenter<Model, SplashActivity> {
    private static final String TAG = "Presenter";

    public Presenter() {
    }

    @Override
    protected Model bindModel() {
        return new Model();
    }

    public void loadData() {

        Subscription subscription = getModel().getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SplashInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(e);
                    }

                    @Override
                    public void onNext(SplashInfo splashInfo) {
                        Log.i(TAG, "onNext: " + splashInfo.toString());
                        getView().showContent();
                        getView().showData(splashInfo);
                    }
                });
        addSubscribe(subscription);
    }


}
