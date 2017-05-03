package com.xxp.yangyan.pro.category.presenter;

import android.content.Context;

import com.xxp.yangyan.mvp.view.MvpView;
import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.bean.CategoryInfoData;
import com.xxp.yangyan.pro.category.model.Model;
import com.xxp.yangyan.pro.category.view.CategoryFragment;

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

public class Presenter extends BasePresenter<Model, CategoryFragment> {
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
                .subscribe(new Observer<CategoryInfoData>() {
                    @Override
                    public void onCompleted() {
                        getView().showContent();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getView().showError(throwable);
                    }

                    @Override
                    public void onNext(CategoryInfoData categoryInfos) {

                        getView().showData(categoryInfos);
                    }
                });
        addSubscribe(subscription);
    }
}
