package com.xxp.yangyan.pro.mvp.contract;

import com.xxp.yangyan.pro.base.BaseModel;
import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.base.BaseView;

import rx.Observable;
import rx.Subscription;

/**
 * Created by 钟大爷 on 2017/2/13.
 * 有的结果可能就是一个bean  为了避免麻烦 ..暂时不拟定对数据结果的接收处理方法
 */

public interface LoadDataContract {
    interface view<D> extends BaseView{
        //加载中
        void showLoading();

        //停止加载
        void stopLoading();

        void showResult(D d);

        //加载成功
        void showSuccess();

        //加载失败
        void showError(Throwable throwable);
    }

    interface model<D> extends BaseModel{
        Observable<D> getData();

    }

    interface presenter<V extends BaseView> extends BasePresenter<V> {
        //订阅
        void addSubscribe(Subscription subscription);
        //不订阅
        void unSubscribe();
    }
}
