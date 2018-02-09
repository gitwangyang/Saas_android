package com.mmbao.saas.utils.im.chatrow;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.exceptions.EaseMobException;
import com.mmbao.saas.R;
import com.mmbao.saas.activity.base.BaseActivity;
import com.mmbao.saas.utils.im.IMConstant;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;

public class SatisfactionActivity extends BaseActivity {

    private String msgId = "";
    private RatingBar ratingBar = null;
    private Button btnSubmit = null;
    private EditText etContent = null;
    private ProgressDialog pd = null;


    @Override
    public void initContentView() {
        msgId = getIntent().getStringExtra("msgId");
        setContentView(R.layout.activity_satisfaction);
    }

    @Override
    public void initData() {
        ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
        btnSubmit = (Button) findViewById(R.id.submit);
        etContent = (EditText) findViewById(R.id.edittext);
    }

    @Override
    public void initView() {

        btnSubmit.setOnClickListener(new MyClickListener());
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating < 1.0f){
                    ratingBar.setRating(1.0f);
                }
            }
        });
    }

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            final EMMessage message = EMChatManager.getInstance().getMessage(msgId);
            try {
                final JSONObject jsonObj = message.getJSONObjectAttribute(IMConstant.WEICHAT_MSG);
                JSONObject jsonArgs = jsonObj.getJSONObject("ctrlArgs");
                final EMMessage sendMessage = EMMessage.createSendMessage(EMMessage.Type.TXT);
                String ratingBarValue = String.valueOf(ratingBar.getSecondaryProgress());
                jsonArgs.put("summary", ratingBarValue);
                jsonArgs.put("detail", etContent.getText().toString());

                JSONObject jsonSend = jsonObj;
                jsonSend.put("ctrlType", "enquiry");
                sendMessage.setAttribute(IMConstant.WEICHAT_MSG, jsonSend);
                sendMessage.setReceipt(message.getFrom());
                sendMessage.addBody(new TextMessageBody(""));
                pd = new ProgressDialog(SatisfactionActivity.this);
                pd.setMessage(getResources().getString(R.string.tip_wating));
                pd.show();

                EMChatManager.getInstance().sendMessage(sendMessage, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss();
                                }
                                EMChatManager.getInstance().getConversation(message.getFrom())
                                        .removeMessage(sendMessage.getMsgId());
                                Toast.makeText(getApplicationContext(), "评价成功", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            }
                        });

                    }

                    @Override
                    public void onProgress(int arg0, String arg1) {

                    }

                    @Override
                    public void onError(int arg0, String arg1) {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss();
                                }
                                Toast.makeText(getApplicationContext(), R.string.tip_request_fail, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (EaseMobException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        // 添加点击Edittext 以外的区域，收起键盘
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }

            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

}
