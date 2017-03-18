package com.xxp.miaoskin.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.xxp.miaoskin.observe.SkinObserve;
import com.xxp.miaoskin.observe.SkinObserver;

/**
 * Created by 钟大爷 on 2017/3/3.
 */

public class SkinAppCompat extends AppCompatActivity implements SkinObserver{

    private SkinCompatDelegate mSkinCompatDelegate;
    private Window mWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(),getSkinDelegate());
        super.onCreate(savedInstanceState);
    }

    private SkinCompatDelegate getSkinDelegate() {
        if (mSkinCompatDelegate == null) {
            mSkinCompatDelegate = new SkinCompatDelegate(this, mWindow);
        }
        return mSkinCompatDelegate;

    }

    //当皮肤发生改变是执行此方法
    @Override
    public void skinUpdate(SkinObserve skinObserve) {

    }
}
