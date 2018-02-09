package com.mmbao.saas.utils.im;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.easemob.chat.EMMessage;
import com.mmbao.saas.R;
import com.mmbao.saas.activity.base.BaseActivity;

public class ContextMenuActivity extends BaseActivity {

    public static final int RESULT_CODE_COPY = 1;
    public static final int RESULT_CODE_DELETE = 2;
    public static final int RESULT_CODE_FORWARD = 3;


    @Override
    public void initContentView() {
        EMMessage message = getIntent().getParcelableExtra("message");

        int type = message.getType().ordinal();
        if (type == EMMessage.Type.TXT.ordinal()) {
            setContentView(R.layout.em_context_menu_for_text);
        } else if (type == EMMessage.Type.LOCATION.ordinal()) {
            setContentView(R.layout.em_context_menu_for_location);
        } else if (type == EMMessage.Type.IMAGE.ordinal()) {
            setContentView(R.layout.em_context_menu_for_image);
        } else if (type == EMMessage.Type.VOICE.ordinal()) {
            setContentView(R.layout.em_context_menu_for_voice);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public void copy(View view) {
        setResult(RESULT_CODE_COPY);
        finish();
    }

    public void delete(View view) {
        setResult(RESULT_CODE_DELETE);
        finish();
    }

    public void forward(View view) {
        setResult(RESULT_CODE_FORWARD);
        finish();
    }
}
