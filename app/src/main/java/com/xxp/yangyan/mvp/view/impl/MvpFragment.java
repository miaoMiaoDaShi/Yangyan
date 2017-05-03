package com.xxp.yangyan.mvp.view.impl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.mvp.view.MvpView;

public abstract class MvpFragment<P extends MvpBasePresenter> extends Fragment implements MvpView {

    protected P presenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //绑定视图
        presenter = bindPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    //绑定
    public abstract P bindPresenter();

    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除绑定
        if (presenter != null) {
            presenter.detachView();
        }
    }

}
