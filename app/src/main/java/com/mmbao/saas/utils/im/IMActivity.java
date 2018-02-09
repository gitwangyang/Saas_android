package com.mmbao.saas.utils.im;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.mmbao.saas.R;
import com.mmbao.saas.activity.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IMActivity extends BaseActivity {

    private static final int RECORD_CODE_IM = 1;

    @Bind(R.id.container_im) FrameLayout container_im;

    public static IMActivity activityInstance;
    private ChatFragment chatFragment;
    private String toChatUsername;


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_im);
    }

    @Override
    public void initData() {
        activityInstance = this;
    }

    @Override
    protected void initView() {
        chatFragment = new ChatFragment();

        Intent intent = getIntent();
//        intent.putExtra(IMConstant.EXTRA_USER_ID, toChatUsername);
        intent.putExtra(IMConstant.EXTRA_SHOW_USERNICK, true);
        intent.putExtra(IMConstant.EXTRA_SHOW_PHONE, true);

        System.out.println("easemob_im_code = " + intent.getStringExtra(IMConstant.EXTRA_USER_ID));

        //传入参数
        chatFragment.setArguments(intent.getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container_im, chatFragment).commit();
    }


    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    public void sendRobotMessage(String txtContent, String menuId) {
        chatFragment.sendRobotMessage(txtContent, menuId);
    }
}
