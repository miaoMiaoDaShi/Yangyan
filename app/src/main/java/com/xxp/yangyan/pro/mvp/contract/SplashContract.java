package com.xxp.yangyan.pro.mvp.contract;

import com.xxp.yangyan.pro.bean.SplashInfo;
import com.xxp.yangyan.pro.mvp.presenter.LoadDataPresenter;

/**
 * Created by 钟大爷 on 2017/2/9.
 */

public interface SplashContract {

    interface View extends LoadDataContract.view<SplashInfo> {
    }

    abstract class Presenter extends LoadDataPresenter<View, Model> {
    }

    interface Model extends LoadDataContract.model<SplashInfo> {
    }


}