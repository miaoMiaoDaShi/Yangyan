package com.xxp.yangyan.pro.classify.presenter;

import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.entity.ClassifyInfoData;
import com.xxp.yangyan.pro.classify.model.Model;
import com.xxp.yangyan.pro.classify.view.ClassifyFragment;

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

public class Presenter extends BasePresenter<Model, ClassifyFragment> {
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
                .subscribe(new Observer<ClassifyInfoData>() {
                    @Override
                    public void onCompleted() {
                        getView().showContent();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getView().showError(throwable);
                    }

                    @Override
                    public void onNext(ClassifyInfoData classifyInfos) {
                        getView().showData(classifyInfos);
                        /**
                         * 分类数据来源于自己的服务器有且只有只有一页
                         * 故.显示数据后应通知view没有更多数据了
                         * 防止用户不停地上拉加载重复的数据
                         */
                        getView().dataIsEnd();
                    }
                });
        addSubscribe(subscription);
    }
}
