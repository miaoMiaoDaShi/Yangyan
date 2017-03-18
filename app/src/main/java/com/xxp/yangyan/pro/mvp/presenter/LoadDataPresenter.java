package com.xxp.yangyan.pro.mvp.presenter;

import com.xxp.yangyan.pro.base.BaseModel;
import com.xxp.yangyan.pro.base.BaseView;
import com.xxp.yangyan.pro.mvp.contract.LoadDataContract;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 钟大爷 on 2017/2/13.
 */

public abstract class LoadDataPresenter<V extends BaseView,M extends BaseModel>
    extends BasePresenterImp<V,M> implements LoadDataContract.presenter<V> {

    private CompositeSubscription subscription;
    @Override
    public void addSubscribe(Subscription subscription) {
        if (this.subscription == null) {
            this.subscription = new CompositeSubscription();
        }
        this.subscription.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if (subscription != null) {
            subscription.clear();
        }
    }

    //加载数据
    public abstract void getData();
}
