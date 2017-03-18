package com.xxp.yangyan.pro.mvp.contract;

import android.app.ProgressDialog;

import com.xxp.yangyan.pro.bean.ImageInfo;
import com.xxp.yangyan.pro.mvp.presenter.LoadDataPresenter;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by 钟大爷 on 2017/2/13.
 */

public interface ImgListContract {
    interface view extends LoadDataContract.view<ResponseBody> {
        void loadGalleryError();
        void loadGallerySuccess(ResponseBody d);
        void loadLocalDataError();
        void loadLocalDataSuccess(List<ImageInfo> imageInfos);

        void loadLocalDataIsEmpty();
    }

    interface Model extends LoadDataContract.model<ResponseBody> {
        List<ImageInfo> getLocalData();
        Observable<ResponseBody> getData(String type, int page);
    }

    abstract class Presenter extends LoadDataPresenter<view,Model> {
        protected abstract void getData(String type, int page);
        protected abstract void loadGallery(ImageInfo imageInfo, ProgressDialog dialog);
    }
}
