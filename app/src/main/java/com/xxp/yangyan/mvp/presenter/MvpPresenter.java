package com.xxp.yangyan.mvp.presenter;

import com.xxp.yangyan.mvp.view.MvpView;

import rx.Subscription;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description :
 */

public interface MvpPresenter<V extends MvpView> {
    /**
     * 绑定目标视图
     *
     * @param view 目标视图
     */
    void attachView(V view);

    /**
     * 解绑,避免内存泄露
     */
    void detachView();


    //订阅
    void addSubscribe(Subscription subscription);

    //不订阅
    void unSubscribe();
}
