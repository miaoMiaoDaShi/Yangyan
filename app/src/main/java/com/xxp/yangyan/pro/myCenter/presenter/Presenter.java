package com.xxp.yangyan.pro.myCenter.presenter;

import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.bean.CategoryInfoData;
import com.xxp.yangyan.pro.category.model.Model;
import com.xxp.yangyan.pro.category.view.CategoryFragment;
import com.xxp.yangyan.pro.myCenter.view.MyFragment;

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

public class Presenter extends BasePresenter<Model, MyFragment> {
    public Presenter() {
    }

    @Override
    protected Model bindModel() {
        return null;
    }

    public void loadData() {
        Subscription subscription = getModel()
                .getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CategoryInfoData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                    }

                    @Override
                    public void onNext(CategoryInfoData categoryInfos) {
                    }
                });
        addSubscribe(subscription);
    }
}
