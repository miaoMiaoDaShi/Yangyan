package com.xxp.yangyan.pro.mvp.contract;

import com.xxp.yangyan.pro.bean.CategoryInfoData;
import com.xxp.yangyan.pro.mvp.presenter.LoadDataPresenter;

/**
 * Created by 钟大爷 on 2017/2/16.
 */

public interface CategoryContract {
    interface view extends LoadDataContract.view<CategoryInfoData>{

    }

    interface model extends LoadDataContract.model<CategoryInfoData>{
    }

    abstract class presenter extends LoadDataPresenter<view,model>{

    }
}
