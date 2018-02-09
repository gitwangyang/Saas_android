package com.mmbao.saas.activity.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmbao.saas.R;
import com.mmbao.saas.utils.NetworkUtils;

import butterknife.ButterKnife;

/**
 * Created by bajieaichirou on 17/9/6.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /** 是否禁止旋转屏幕 **/
    private boolean isAllowScreenRoate = true;
    /** 是否允许全屏 **/
    private boolean isAllowFullScreen = true;

    private int layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIsAllowScreenRoate();
        initIsAllowFullScreen();

        initContentView();
        initData();
        initView();


    }


    public abstract void initContentView();
    public abstract void initData();

    protected abstract void initView();

    //----------------------------------屏幕旋转------------------------------------------
    /**
     * 屏幕旋转
     */
    private void initIsAllowScreenRoate() {
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * [是否允许屏幕旋转]
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    //----------------------------------全屏显示------------------------------------------
    /**
     * 全屏显示
     */
    private void initIsAllowFullScreen() {
        if (isAllowFullScreen) {
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    /**
     * [是否允许全屏]
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.isAllowFullScreen = allowFullScreen;
    }

    //----------------------------------Activity跳转------------------------------------------
    /**
     * [页面跳转]
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    //----------------------------------自定义Toolbar------------------------------------------
    public Toolbar initToolbar(int toolbarId, int title) {
        AppCompatActivity mAppCompatActivity = this;
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(toolbarId);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(title);
        return toolbar;
    }

    public Toolbar initToolbar(int toolbarId, String title) {
        AppCompatActivity mAppCompatActivity = this;
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(toolbarId);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(title);
        return toolbar;
    }

    public Toolbar initToolbar(int toolbarId, String title, int img) {
        AppCompatActivity mAppCompatActivity = this;
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(toolbarId);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(title);

        ImageView toolbarImg = (ImageView) toolbar.findViewById(R.id.toolbarImg);
        toolbarImg.setImageResource(img);
        toolbarImg.setVisibility(View.VISIBLE);
        toolbarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        return toolbar;
    }

    public Toolbar initToolbar(int toolbarId, String title, int img, final String title2) {
        final AppCompatActivity mAppCompatActivity = this;
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(toolbarId);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(title);
        TextView toolbarTitle2 = (TextView) toolbar.findViewById(R.id.toolbarTitle2);
        toolbarTitle2.setText(title2);
        toolbarTitle2.setVisibility(View.VISIBLE);

        ImageView toolbarImg = (ImageView) toolbar.findViewById(R.id.toolbarImg);
        toolbarImg.setImageResource(img);
        toolbarImg.setVisibility(View.VISIBLE);
        toolbarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return toolbar;
    }

    public Toolbar initToolbar(int toolbarId, String title, int img, final int rightImg) {
        final AppCompatActivity mAppCompatActivity = this;
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(toolbarId);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(title);

        ImageView toolbarImg = (ImageView) toolbar.findViewById(R.id.toolbarImg);
        toolbarImg.setImageResource(img);
        toolbarImg.setVisibility(View.VISIBLE);
        toolbarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        NetworkUtils.checkNetworkState(this);
    }
    public void onPause() {
        super.onPause();
    }


    //----------------------------------重写布局绑定------------------------------------------
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    //----------------------------------Toolbar------------------------------------------
}
