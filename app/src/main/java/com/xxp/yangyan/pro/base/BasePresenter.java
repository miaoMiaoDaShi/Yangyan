package com.xxp.yangyan.pro.base;

import android.content.Context;

import com.google.gson.Gson;
import com.xxp.yangyan.mvp.model.MvpModel;
import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.mvp.view.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 钟大爷 on 2017/2/13.
 */

public abstract class BasePresenter<M extends MvpModel, V extends MvpView> extends MvpBasePresenter<V> {
    private Gson gson;
    private M model;

    public BasePresenter() {
        this.gson = new Gson();
        this.model = bindModel();
    }

    /**
     * 绑定目标model
     *
     * @return
     */
    protected abstract M bindModel();

    public Gson getGson() {
        return gson;
    }

    public M getModel() {
        return model;
    }




}
