package com.xxp.yangyan.pro.mvp.model;

import com.xxp.yangyan.pro.api.ApiEngine;
import com.xxp.yangyan.pro.bean.CategoryInfo;
import com.xxp.yangyan.pro.bean.CategoryInfoData;
import com.xxp.yangyan.pro.mvp.contract.CategoryContract;

import java.util.List;

import rx.Observable;

/**
 * Created by 钟大爷 on 2017/2/16.
 */

public class CategoryModelImpl implements CategoryContract.model {
    @Override
    public Observable<CategoryInfoData> getData() {
        return ApiEngine.getInstance().getMMService().getCategoryPage();
    }
}
