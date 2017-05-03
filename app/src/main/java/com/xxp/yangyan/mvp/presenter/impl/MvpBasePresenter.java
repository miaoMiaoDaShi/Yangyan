package com.xxp.yangyan.mvp.presenter.impl;

import com.xxp.yangyan.mvp.presenter.MvpPresenter;
import com.xxp.yangyan.mvp.view.MvpView;

import java.lang.ref.WeakReference;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description :
 */

public abstract class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private WeakReference<V> view;
    private CompositeSubscription subscription;


    @Override
    public void attachView(V view) {
        this.view = new WeakReference<V>(view);
    }

    @Override
    public void detachView() {
        if (view != null) {
            view.clear();
            view = null;
        }
        unSubscribe();
    }

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

    public CompositeSubscription getSubscription() {
        return subscription;
    }

    public V getView() {
        return view == null ? null : view.get();
    }
}
