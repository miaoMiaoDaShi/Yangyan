package com.xxp.yangyan.pro.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xxp.yangyan.pro.utils.UIUtils;

import butterknife.ButterKnife;

/**
 * Created by 钟大爷 on 2017/2/4.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {
    protected P presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = UIUtils.getInflaterView(getLayoutId());
        ButterKnife.bind(this,view);
        return view;
    }

    protected abstract int getLayoutId() ;

    protected abstract P onCreatePresenter();


    @Override
    public void onResume() {
        super.onResume();
        if (onCreatePresenter() != null) {
            presenter = onCreatePresenter();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    protected void showToast(String content) {
        Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
    }

}
