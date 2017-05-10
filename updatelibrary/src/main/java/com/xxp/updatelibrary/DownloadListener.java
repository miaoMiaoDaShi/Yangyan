package com.xxp.updatelibrary;

import java.io.File;

/**
 * Created by 钟大爷 on 2017/3/15.
 * 下载监听
 */

public interface DownloadListener {
    //下载失败
    void onError(Throwable throwable);
    //下载中
    void onDownloading(int pro);
    //下载成功
    void onSuccess(File file);
}
