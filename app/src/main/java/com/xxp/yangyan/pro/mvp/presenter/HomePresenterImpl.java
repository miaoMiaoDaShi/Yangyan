package com.xxp.yangyan.pro.mvp.presenter;

import com.xxp.yangyan.pro.bean.HomeInfo;
import com.xxp.yangyan.pro.mvp.contract.HomeContract;
import com.xxp.yangyan.pro.mvp.model.HomeModelImpl;
import com.xxp.yangyan.pro.utils.JudgeUtils;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 钟大爷 on 2017/02/09
 */

public class HomePresenterImpl extends
        HomeContract.Presenter {

    public HomePresenterImpl(HomeContract.View view) {
        this.view = view;
        this.model = new HomeModelImpl();
    }


    @Override
    public void getData() {
        Subscription subscription = model.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeInfo>() {
                    @Override
                    public void onCompleted() {
                        view.stopLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e);
                    }

                    @Override
                    public void onNext(HomeInfo homeInfo) {
                        if (JudgeUtils.isHomeImgUpdate(homeInfo.getDate())) {
                            view.setbannerImage(homeInfo.getBanner());
                            view.setPlateImage(homeInfo.getPush());
                        }
                        view.showSuccess();
                        view.showResult(homeInfo);


                        view.setNotice(homeInfo.getNotice());
                    }
                });
        addSubscribe(subscription);
    }
}