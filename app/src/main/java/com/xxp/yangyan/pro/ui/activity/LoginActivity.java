package com.xxp.yangyan.pro.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.base.BaseActivity;
import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.utils.ActivityManager;
import com.xxp.yangyan.pro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 登录界面
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.txt_name)
    EditText txtName;
    @BindView(R.id.txt_password)
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager.addActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }


    @OnClick({R.id.btn_login, R.id.iv_back, R.id.register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.register:
                break;
        }
    }

    private void login() {
        BmobUser user = new BmobUser();
        String name = txtName.getText().toString().trim();
        String password = txtPassword.getText().toString();
        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password)){
            user.setUsername(name);
            user.setPassword(password);

            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("登录中....");
            dialog.setIndeterminate(true);
            dialog.show();
            user.login(new SaveListener<BmobUser>() {
                @Override
                public void onStart() {
                    super.onStart();

                }

                @Override
                public void done(BmobUser o, BmobException e) {
                    if(e==null){
                        ToastUtils.showToast("登录成功");
                        toMyPage(BmobUser.getCurrentUser());
                        dialog.dismiss();
                    } else {
                        Logger.e(e+"");
                        ToastUtils.showToast("登录失败");
                        dialog.dismiss();
                    }
                }

            });
        } else {
            ToastUtils.showToast("任何一项不能为空");
        }
    }

    private void toMyPage(BmobUser currentUser) {
        Intent intent = new Intent();
        intent.putExtra("user",currentUser);
        setResult(0x11,intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
}
