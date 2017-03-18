package com.xxp.yangyan.pro.mvp.contract;

import com.xxp.yangyan.pro.bean.HomeInfo;
import com.xxp.yangyan.pro.mvp.presenter.LoadDataPresenter;

import java.util.List;

/**
 * Created by 钟大爷 on 2017/2/9.
 */

public interface HomeContract {

    interface View extends LoadDataContract.view<HomeInfo> {
        //设置banner的图片
        void setbannerImage(List<String> urls);

        //设置公告
        void setNotice(String s);

        //设置推荐板块的图片背景
        void setPlateImage(List<String> urls);
    }

    abstract class Presenter
            extends LoadDataPresenter<View,Model> {
        //
    }

    interface Model extends LoadDataContract.model<HomeInfo> {

    }


}