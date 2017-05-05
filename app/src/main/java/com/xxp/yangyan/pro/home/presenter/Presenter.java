package com.xxp.yangyan.pro.home.presenter;

import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.entity.HomeData;
import com.xxp.yangyan.pro.home.model.Model;
import com.xxp.yangyan.pro.home.view.HomeFragment;

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

public class Presenter<V> extends BasePresenter<Model, HomeFragment> {
    public Presenter() {
    }

    @Override
    protected Model bindModel() {
        return new Model();
    }

    public void loadData() {
        Subscription subscription = getModel()
                .getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<HomeData>() {
                    @Override
                    public void onCompleted() {
                        getView().showContent();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getView().showError(throwable);
                    }

                    @Override
                    public void onNext(HomeData homeData) {
                        getView().showData(homeData);
                    }
                });
        addSubscribe(subscription);
    }
}
