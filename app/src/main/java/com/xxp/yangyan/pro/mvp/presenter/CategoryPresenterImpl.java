package com.xxp.yangyan.pro.mvp.presenter;

import com.xxp.yangyan.pro.bean.CategoryInfoData;
import com.xxp.yangyan.pro.mvp.contract.CategoryContract;
import com.xxp.yangyan.pro.mvp.model.CategoryModelImpl;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 钟大爷 on 2017/2/16.
 */

public class CategoryPresenterImpl extends CategoryContract.presenter {
    public CategoryPresenterImpl(CategoryContract.view view) {
        this.view  =view;
        this.model = new CategoryModelImpl();
    }

    @Override
    public void getData() {
        Subscription subscription = model
                .getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CategoryInfoData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        view.showError(throwable);
                    }

                    @Override
                    public void onNext(CategoryInfoData categoryInfos) {
                        view.showSuccess();
                        view.showResult(categoryInfos);
                    }
                });
        addSubscribe(subscription);
    }
}
