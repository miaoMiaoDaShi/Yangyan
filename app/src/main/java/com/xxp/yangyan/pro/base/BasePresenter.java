package com.xxp.yangyan.pro.base;

import android.content.Context;

import com.google.gson.Gson;
import com.xxp.yangyan.mvp.model.MvpModel;
import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.mvp.view.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 项目的presenter基类
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
