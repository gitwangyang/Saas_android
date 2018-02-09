package com.mmbao.saas.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmbao.saas.R;
import com.mmbao.saas.activity.base.BaseActivity;
import com.mmbao.saas.fragment.FragmentClassify;
import com.mmbao.saas.fragment.FragmentHome;
import com.mmbao.saas.fragment.FragmentMy;
import com.mmbao.saas.fragment.FragmentPurchase;
import com.mmbao.saas.fragment.FragmentToBuy;
import com.mmbao.saas.utils.LogcatUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_content)
    FrameLayout mLayout;

    @Bind(R.id.main_tab_home)
    LinearLayout mHomeTab;

    @Bind(R.id.main_tab_home_img)
    ImageView mHomeImg;

    @Bind(R.id.main_tab_home_txt)
    TextView mHomeTxt;

    @Bind(R.id.main_tab_buy)
    LinearLayout mBuyTab;

    @Bind(R.id.main_tab_buy_img)
    ImageView mBuyImg;

    @Bind(R.id.main_tab_buy_txt)
    TextView mBuyTxt;

    @Bind(R.id.main_tab_classfy)
    LinearLayout mClassfyTab;

    @Bind(R.id.main_tab_classfy_img)
    ImageView mClassfyImg;

    @Bind(R.id.main_tab_classfy_txt)
    TextView mClassfyTxt;

    @Bind(R.id.main_tab_cart)
    LinearLayout mCartTab;

    @Bind(R.id.main_tab_cart_img)
    ImageView mCartImg;

    @Bind(R.id.main_tab_cart_txt)
    TextView mCartTxt;

    @Bind(R.id.main_tab_my)
    LinearLayout mMyTab;

    @Bind(R.id.main_tab_my_img)
    ImageView mMyImg;

    @Bind(R.id.main_tab_my_txt)
    TextView mMyTxt;

    private FragmentHome homeFrag;
    private FragmentToBuy buyFrag;
    private FragmentClassify classfyFrag;
    private FragmentPurchase purchaseFrag;
    private FragmentMy myFrag;

    private static Boolean isQuit = false;
    private Timer timer = new Timer();
    private TimerTask task;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initData() {
        selectTab(0);
    }

    @Override
    protected void initView() {

    }


    //处理Tab的点击事件
    @OnClick({R.id.main_tab_home, R.id.main_tab_buy, R.id.main_tab_classfy,
            R.id.main_tab_cart, R.id.main_tab_my})
    public void onClick(View v) {
        resetImgs();
        switch (v.getId()) {
            case R.id.main_tab_home:
                selectTab(0);
                break;
            case R.id.main_tab_buy:
                selectTab(1);
                break;
            case R.id.main_tab_classfy:
                selectTab(2);
                break;
            case R.id.main_tab_cart:
                selectTab(3);
                break;
            case R.id.main_tab_my:
                selectTab(4);
                break;
        }

    }

    //进行选中Tab的处理
    private void selectTab(int i) {
        LogcatUtil.d("MainActivity -----selectTab i "  + i );
        //获取FragmentManager对象
        FragmentManager manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        FragmentTransaction transaction = manager.beginTransaction();
        //先隐藏所有的Fragment
        hideFragments(transaction);
//        bundle = new Bundle();
        switch (i) {
            case 0:
                mHomeImg.setImageResource(R.mipmap.tab_home);
                mHomeTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (homeFrag == null) {
                    homeFrag = new FragmentHome();
                    transaction.add(R.id.main_content, homeFrag);
                } else {
                    transaction.show(homeFrag);
                }
                break;
            case 1:
                mBuyImg.setImageResource(R.mipmap.tab_buy);
                mBuyTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (buyFrag == null) {
                    buyFrag = new FragmentToBuy();
                    transaction.add(R.id.main_content, buyFrag);
                } else {
                    transaction.show(buyFrag);
                }
                break;
            case 2:
                mClassfyImg.setImageResource(R.mipmap.tab_classfy);
                mClassfyTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (classfyFrag == null) {
                    classfyFrag = new FragmentClassify();
                    transaction.add(R.id.main_content, classfyFrag);
                } else {
                    transaction.show(classfyFrag);
                }
                break;
            case 3:
                mCartImg.setImageResource(R.mipmap.tab_cart);
                mCartTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (purchaseFrag == null) {
                    purchaseFrag = new FragmentPurchase();
                    transaction.add(R.id.main_content, purchaseFrag);
                } else {
                    transaction.show(purchaseFrag);
                }
                break;

            case 4:
                mMyImg.setImageResource(R.mipmap.tab_p_center);
                mMyTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (myFrag == null) {
                    myFrag = new FragmentMy();
                    transaction.add(R.id.main_content, myFrag);
                } else {
                    transaction.show(myFrag);
                }
                break;
        }
        transaction.commit();
    }

    //将四个的Fragment隐藏
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFrag != null) {
            transaction.hide(homeFrag);
        }
        if (buyFrag != null) {
            transaction.hide(buyFrag);
        }
        if (classfyFrag != null) {
            transaction.hide(classfyFrag);
        }
        if (purchaseFrag != null) {
            transaction.hide(purchaseFrag);
        }
        if (myFrag != null) {
            transaction.hide(myFrag);
        }
    }

    private void resetImgs() {
        mHomeImg.setImageResource(R.mipmap.tab_home_un);
        mBuyImg.setImageResource(R.mipmap.tab_buy_un);
        mClassfyImg.setImageResource(R.mipmap.tab_classfy_un);
        mCartImg.setImageResource(R.mipmap.tab_cart_un);
        mMyImg.setImageResource(R.mipmap.tab_p_center_un);
        mHomeTxt.setTextColor(getResources().getColor(R.color.color_tab_gray));
        mBuyTxt.setTextColor(getResources().getColor(R.color.color_tab_gray));
        mClassfyTxt.setTextColor(getResources().getColor(R.color.color_tab_gray));
        mCartTxt.setTextColor(getResources().getColor(R.color.color_tab_gray));
        mMyTxt.setTextColor(getResources().getColor(R.color.color_tab_gray));
    }

    @Override
    public void onBackPressed() {
        if (isQuit == false) {
            isQuit = true;
            Toast.makeText(getBaseContext(), getResources().getString(R.string.fish_app), Toast.LENGTH_SHORT).show();
            task = new TimerTask() {
                @Override
                public void run() {
                    isQuit = false;
                }
            };
            timer.schedule(task, 2000);
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
