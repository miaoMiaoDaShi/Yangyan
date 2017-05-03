package com.xxp.yangyan.pro.splash.presenter;

import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.bean.SplashInfo;
import com.xxp.yangyan.pro.splash.model.Model;
import com.xxp.yangyan.pro.splash.view.SplashActivity;

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
                        getView().showContent();
                        getView().showData(splashInfo);
                    }
                });
        addSubscribe(subscription);
    }
}
