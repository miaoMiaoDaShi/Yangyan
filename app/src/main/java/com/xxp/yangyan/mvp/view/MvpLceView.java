package com.xxp.yangyan.mvp.view;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 有关数据加载显示逻辑的view
 */

public interface MvpLceView<M> extends MvpView {
    /**
     * 加载中
     *
     * @param type 真 下拉刷新 ; 假 上拉加载更多
     */
    void showLoading(boolean type);

    /**
     * 显示内容页
     */
    void showContent();

    /**
     * 加载失败
     *
     * @param throwable 失败信息
     */
    void showError(Throwable throwable);

    /**
     * 将数据展示到控件上
     *
     * @param data
     */
    void showData(M data);
}
