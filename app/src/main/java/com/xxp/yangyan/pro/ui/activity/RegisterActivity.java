package com.xxp.yangyan.pro.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxp.yangyan.R;
import com.xxp.yangyan.pro.base.BaseActivity;
import com.xxp.yangyan.pro.base.BasePresenter;
import com.xxp.yangyan.pro.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_galleryCount)
    TextView tvGalleryCount;
    @BindView(R.id.txt_name)
    EditText txtName;
    @BindView(R.id.txt_email)
    EditText txtEmail;
    @BindView(R.id.txt_pass)
    EditText txtPass;
    @BindView(R.id.txt_pass_twice)
    EditText txtPassTwice;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.activity_register)
    LinearLayout activityRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @OnClick({R.id.btn_register, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_register:
                startRegister();
                break;
        }
    }

    private void startRegister() {
        BmobUser bmobUser = new BmobUser();
        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String pass_one = txtPass.getText().toString();
        String pass_two = txtPassTwice.getText().toString();

        if(pass_one.equals(pass_two)&&pass_one.length()>5&& !
                TextUtils.isEmpty(name)&&!TextUtils.isEmpty(email)){
            bmobUser.setEmail(email);
            bmobUser.setUsername(name);
            bmobUser.setPassword(pass_one);
            bmobUser.signUp(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {

                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
}
