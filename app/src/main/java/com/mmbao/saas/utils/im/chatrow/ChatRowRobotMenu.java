package com.mmbao.saas.utils.im.chatrow;

import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.util.DensityUtil;
import com.king.app.m_easelib.utils.EaseSmileUtils;
import com.king.app.m_easelib.widget.chatrow.EaseChatRow;
import com.mmbao.saas.R;
import com.mmbao.saas.utils.im.IMActivity;
import com.mmbao.saas.utils.im.IMConstant;
import com.mmbao.saas.utils.im.domain.IMHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author    LeoCheung
 * Version   V1.0
 * Email     leocheung4ever@gmail.com
 * Date      2016-04-04 15:39
 * Description  机器人聊天行对象
 * Date          Author          Version          Description
 * ------------------------------------------------------------------
 * 2016/4/4     LeoCheung       1.0              1.0
 * Why & What is modified:
 */
public class ChatRowRobotMenu extends EaseChatRow {

    TextView tvTitle;
    LinearLayout tvList;
    private TextView contentView;

    public ChatRowRobotMenu(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        if (IMHelper.getInstance().isRobotMenuMessage(message)) {
            inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ? R.layout.em_row_received_menu
                    : R.layout.ease_row_sent_message, this);
        }
    }

    @Override
    protected void onFindViewById() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvList = (LinearLayout) findViewById(R.id.ll_layout);
        contentView = (TextView) findViewById(R.id.tv_chatcontent);
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSetUpView() {
        if (message.direct == EMMessage.Direct.RECEIVE) {
            try {
                JSONObject jsonObj = message.getJSONObjectAttribute(IMConstant.MESSAGE_ATTR_MSGTYPE);
                if (jsonObj.has("choice")) {
                    JSONObject jsonChoice = jsonObj.getJSONObject("choice");
                    if (jsonChoice.has("title")) {
                        String title = jsonChoice.getString("title");
                        tvTitle.setText(title);
                    }
                    if (jsonChoice.has("items")) {
                        setRobotMenuListMessageLayout(tvList, jsonChoice.getJSONArray("items"));
                    }else if(jsonChoice.has("list")){
                        setRobotMenuMessagesLayout(tvList, jsonChoice.getJSONArray("list"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            TextMessageBody txtBody = (TextMessageBody) message.getBody();
            Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
            // 设置内容
            contentView.setText(span, TextView.BufferType.SPANNABLE);
            handleTextMessage();
        }
    }

    private void handleTextMessage() {

    }

    private void setRobotMenuListMessageLayout(LinearLayout parentView, JSONArray jsonArray) {
        try {
            parentView.removeAllViews();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemJson = jsonArray.getJSONObject(i);
                final String itemStr = itemJson.getString("name");
                final String itemId = itemJson.getString("id");
                final TextView textView = new TextView(context);
                textView.setText(itemStr);
                textView.setTextSize(15);
                textView.setTextColor(getResources().getColorStateList(R.color.gray_tv_buy));
                textView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ((IMActivity) context).sendRobotMessage(itemStr, itemId);
                    }
                });
                LinearLayout.LayoutParams llLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                llLp.bottomMargin = DensityUtil.dip2px(context, 3);
                llLp.topMargin = DensityUtil.dip2px(context, 3);
                parentView.addView(textView, llLp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setRobotMenuMessagesLayout(LinearLayout tvList, JSONArray list) {

    }

    @Override
    protected void onBubbleClick() {

    }
}
