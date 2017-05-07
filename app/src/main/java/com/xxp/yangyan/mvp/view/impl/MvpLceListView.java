package com.xxp.yangyan.mvp.view.impl;

import com.xxp.yangyan.mvp.view.MvpLceView;
import com.xxp.yangyan.mvp.view.MvpView;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/7
 * Description : 有关数据加载 列表类控件
 */

public interface MvpLceListView<M> extends MvpLceView<M> {

    /**
     * 没有更多的数据了
     */
    void dataIsEnd();
}
