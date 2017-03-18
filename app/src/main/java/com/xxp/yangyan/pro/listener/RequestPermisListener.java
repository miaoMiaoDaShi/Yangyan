package com.xxp.yangyan.pro.listener;

import java.util.List;

/**
 * Created by 钟大爷 on 2017/2/17.
 */

public interface RequestPermisListener {
    void onGranted();
    void onDenide(List<String> permissions);
}
