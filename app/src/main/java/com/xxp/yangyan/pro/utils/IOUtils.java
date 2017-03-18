package com.xxp.yangyan.pro.utils;

import android.os.Environment;

/**
 * Created by 钟大爷 on 2017/2/4.
 * 与IO有关发的类
 */

public class IOUtils {
    //返回应用的SD卡存储路劲,绝对
    public static String getSDPath(){
        return Environment
                .getExternalStorageDirectory()
                .getAbsolutePath() + "/奇创喵喵/Yangyan/";
    }
}
