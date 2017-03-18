package com.xxp.yangyan.pro.base;

/**
 * Created by 钟大爷 on 2017/2/13.
 */

public interface BasePresenter<V extends BaseView> {
    //绑定view
    void attachView(V view);
    //解绑view
    void detachView();
}
