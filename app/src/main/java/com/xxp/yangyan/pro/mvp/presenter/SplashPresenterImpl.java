package com.xxp.yangyan.pro.mvp.presenter;

import com.xxp.yangyan.pro.bean.SplashInfo;
import com.xxp.yangyan.pro.mvp.contract.SplashContract;
import com.xxp.yangyan.pro.mvp.model.SplashModelImpl;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 钟大爷 on 2017/02/09
 */

public class SplashPresenterImpl extends SplashContract.Presenter {

    public SplashPresenterImpl(SplashContract.View view) {
        this.view = view;
        this.model = new SplashModelImpl();
    }

    @Override
    public void getData() {
        Subscription subscription = model.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SplashInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e);
                    }

                    @Override
                    public void onNext(SplashInfo splashInfo) {
                        view.showSuccess();
                        view.showResult(splashInfo);
                    }
                });
        addSubscribe(subscription);
    }
}