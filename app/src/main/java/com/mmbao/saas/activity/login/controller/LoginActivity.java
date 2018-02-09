package com.mmbao.saas.activity.login.controller;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmbao.saas.R;
import com.mmbao.saas.activity.base.BaseActivity;
import com.mmbao.saas.activity.base.CallBack;
import com.mmbao.saas.activity.login.adapter.ViewPagerAdapter;
import com.mmbao.saas.activity.login.bean.LoginBean;
import com.mmbao.saas.activity.login.model.LoginModel;
import com.mmbao.saas.activity.login.view.LoginModelImpl;
import com.mmbao.saas.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginModelImpl,CallBack<LoginBean> {

    @Bind(R.id.tabLayout_login)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager_login)
    ViewPager mViewPager;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_forget_psd)
    TextView tvForgetPsd;
    @Bind(R.id.wechat_login)
    LinearLayout wechatLogin;
    @Bind(R.id.qq_login)
    LinearLayout qqLogin;
    private LoginModel loginModel;
    private ViewPagerAdapter mAdapter;
    private List<String> titles;
    private Activity mActivity;
    private String name = "18255503426", password = "mmb123";
    private static final String TAG="LoginActivity";

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initData() {
        mActivity = this;
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.verify_login));
        titles.add(getResources().getString(R.string.user_login));
        loginModel = new LoginModel(this);
    }

    @Override
    protected void initView() {
        initToolbar(R.id.login_toolbar, getResources().getString(R.string.login), R.mipmap.back_white, getResources().getString(R.string.register));
        mTabLayout.setupWithViewPager(mViewPager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mActivity, titles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    /**
     * 网络请求调用
     */
    @Override
    public void login() {
        loginModel.login(this, name, password);
    }

    @OnClick({R.id.btn_login, R.id.tv_forget_psd, R.id.wechat_login, R.id.qq_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if ( NetworkUtils.checkNetworkState(this)){
                    login();
                }else {
                    Toast.makeText(LoginActivity.this, "请检查网络后再试", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_forget_psd:
                break;
            case R.id.wechat_login:
                break;
            case R.id.qq_login:
                break;
        }
    }

    //-------------------------------------数据请求-------------------------------------------------
    @Override
    public void onResponse(LoginBean loginBean) {
        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void timeOut() {
        Toast.makeText(LoginActivity.this, "登录超时", Toast.LENGTH_LONG).show();
    }
}
