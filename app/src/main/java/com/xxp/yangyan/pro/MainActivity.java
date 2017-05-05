package com.xxp.yangyan.pro;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xxp.yangyan.R;
import com.xxp.yangyan.mvp.presenter.impl.MvpBasePresenter;
import com.xxp.yangyan.pro.base.BaseActivity;
import com.xxp.yangyan.pro.classify.view.ClassifyFragment;
import com.xxp.yangyan.pro.entity.UserInfo;
import com.xxp.yangyan.pro.home.view.HomeFragment;
import com.xxp.yangyan.pro.imageList.view.ImageListFragment;
import com.xxp.yangyan.pro.myCenter.view.MyFragment;
import com.xxp.yangyan.pro.utils.ActivityManager;
import com.xxp.yangyan.pro.utils.UIUtils;

import butterknife.BindView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.xxp.yangyan.pro.utils.ToastUtils.showToast;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    private FragmentManager fm;
    private MyOnClick myOnClick;

    private Fragment mContent;

    //主页
    private HomeFragment mFragmentHome;
    //分类
    private ClassifyFragment mCtalkFragment;
    //个人中心
    private MyFragment mMyFragment;
    //图城
    private ImageListFragment mImageListFragment;
    private MyFragment myFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        initView();
        initBmob();
        if (App.isFirst) {
            addUserInfo();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void addUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setCDKEY(getIMEI());
        userInfo.setType(android.os.Build.MODEL);
        userInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private String getIMEI() {
        return ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
    }

    //初始化bmob
    private void initBmob() {
        Bmob.initialize(this, "74d8dc6825c8de37116fc16ca534fbb1");
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                        R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        myOnClick = new MyOnClick();
       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ((RadioButton) radiogroup.getChildAt(0)).setChecked(true);
        for (int i = 0; i < radiogroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radiogroup.getChildAt(i);
            initDrawable(radioButton);
            radioButton.setOnClickListener(myOnClick);

        }

        setDefaultFragment();

    }

    private void setDefaultFragment() {
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mFragmentHome = new HomeFragment();
        transaction.add(R.id.fl_content, mFragmentHome);
        transaction.commit();
        mContent = mFragmentHome;

    }


    //调整默认的下选项栏的图标的的大小
    private void initDrawable(RadioButton radioButton) {
        Drawable topImg = radioButton.getCompoundDrawables()[1];
        topImg.setBounds(0, 0, (int) UIUtils.dip2Px(30), (int) UIUtils.dip2Px(30));
        radioButton.setCompoundDrawables(null, topImg, null, null);
    }

    //界面的切换
    public void switchContent(Fragment to) {
        if (mContent != to) {
            FragmentTransaction transaction = fm.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                // 隐藏当前的fragment，add下一个到Activity中
                transaction.hide(mContent).add(R.id.fl_content, to).commit();
            } else {
                transaction.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mContent = to;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //切换数据来源
            case R.id.nav_source:
                break;
            //开发人员专区
            case R.id.nav_vip:
                toVIPPage();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void toVIPPage() {

    }

    //事件监听类
    class MyOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tab_1:
                    //主页
                    if (null == mFragmentHome) {
                        mFragmentHome = new HomeFragment();
                    }
                    switchContent(mFragmentHome);
                    break;
                case R.id.tab_2:
                    //图城
                    if (null == mImageListFragment) {
                        mImageListFragment = new ImageListFragment();
                    }
                    switchContent(mImageListFragment);
                    break;
                case R.id.tab_3:
                    //分类
                    if (null == mCtalkFragment) {
                        mCtalkFragment = new ClassifyFragment();
                    }
                    switchContent(mCtalkFragment);
                    break;
                case R.id.tab_4:
                    //我的
                    if (null == mMyFragment) {
                        mMyFragment = new MyFragment();
                    }
                    switchContent(mMyFragment);
                    break;

            }
        }
    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast("再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                App.exitApp();
            }


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
}
