package com.xxp.yangyan.mvp.view.impl;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.mvp.view.MvpView;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description :将我们的MVP架构集成到我们的Activity MvpActivity---是MVP框架的
 */

public abstract class MvpActivity<P extends MvpBasePresenter> extends AppCompatActivity implements MvpView {
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((presenter = bindPresenter()) != null) {
            presenter.attachView(this);
        }
    }


    /**
     * 绑定目标presenter
     *
     * @return
     */
    protected abstract P bindPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁的时候---解除绑定
        if (presenter != null) {
            presenter.detachView();
        }

    }
}
