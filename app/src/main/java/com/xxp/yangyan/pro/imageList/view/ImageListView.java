package com.xxp.yangyan.pro.imageList.view;

import com.xxp.yangyan.mvp.view.MvpLceView;
import com.xxp.yangyan.mvp.view.MvpView;

import okhttp3.ResponseBody;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description :
 */

public interface ImageListView<M> extends MvpLceView<M> {

    void loadGalleryError(Throwable throwable);

    void loadGallerySuccess(M data);
}
