package com.xxp.yangyan.pro.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.mvp.view.MvpView;
import com.xxp.yangyan.pro.utils.UIUtils;

import butterknife.ButterKnife;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 项目的根fragment
 */

public abstract class BaseFragment<P extends MvpBasePresenter> extends Fragment implements MvpView {
    protected P presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container,false);
        ButterKnife.bind(this, view);
        if ((presenter = bindPresenter()) != null) {
            presenter.attachView(this);
        }
        return view;
    }

    protected abstract int getLayoutId();

    /**
     * 绑定目标presenter
     *
     * @return
     */
    protected abstract P bindPresenter();


    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
