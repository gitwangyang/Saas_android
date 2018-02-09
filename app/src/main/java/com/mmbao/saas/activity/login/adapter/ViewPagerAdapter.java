package com.mmbao.saas.activity.login.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mmbao.saas.activity.login.fragment.FragmentPhone;
import com.mmbao.saas.activity.login.fragment.FragmentUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:com.mmbao.saas.activity.login.adapter类
 * Created by Administrator on 2018/2/3.
 * Maxim:There is no smoke without fire
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<String> titles;
    private List<Fragment> mFragment = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager fm, Context context, List<String> titles) {
        super(fm);
        this.context = context;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                mFragment.add(new FragmentPhone());
                break;
            case 1:
                mFragment.add(new FragmentUser());
                break;
        }
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
