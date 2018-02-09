package com.mmbao.saas.activity.login.fragment;

import android.support.v4.app.Fragment;

import com.mmbao.saas.R;
import com.mmbao.saas.fragment.base.BaseFragment;

/**
 * Description:com.mmbao.saas.activity.login类
 * Created by Administrator on 2018/2/3.
 * Maxim:There is no smoke without fire
 */
public class FragmentUser extends BaseFragment {

    @Override
    public int setContentView(int layout) {
        layout = R.layout.fragment_user;
        return layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    public static Fragment getInstance() {
        return null;
    }
}
