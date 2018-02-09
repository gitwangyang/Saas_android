package com.mmbao.saas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmbao.saas.R;
import com.mmbao.saas.fragment.base.BaseFragment;

/**
 * Created by bajieaichirou on 17/9/6.
 * 求购
 */
public class FragmentToBuy extends BaseFragment {

    public FragmentToBuy() {
    }


    @Override
    public int setContentView(int layout) {
        layout = R.layout.fragment_buy;
        return layout;

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        initToolbar(R.id.buy_toolbar,getResources().getString(R.string.buy_myself));
    }
}
