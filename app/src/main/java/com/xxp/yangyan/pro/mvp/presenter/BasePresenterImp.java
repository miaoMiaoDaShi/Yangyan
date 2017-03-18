package com.xxp.yangyan.pro.mvp.presenter;

import com.xxp.yangyan.pro.base.BaseModel;
import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.base.BaseView;

/**
 * Created by 钟大爷 on 2017/2/13.
 */

public class BasePresenterImp<V extends BaseView,M extends BaseModel>
        implements BasePresenter<V> {
    protected V view;
    protected M model;


    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (view != null) {
            view = null;
        }
    }

}
