package com.xxp.yangyan.pro.listener;

import java.util.List;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/5/2
 * Description : 请求权限的监听
 */

public interface RequestPermisListener {
    void onGranted();
    void onDenide(List<String> permissions);
}
