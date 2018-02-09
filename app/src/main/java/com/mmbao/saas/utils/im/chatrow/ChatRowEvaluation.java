package com.mmbao.saas.utils.im.chatrow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.easemob.chat.EMMessage;
import com.king.app.m_easelib.widget.chatrow.EaseChatRow;
import com.mmbao.saas.R;
import com.mmbao.saas.utils.im.ChatFragment;
import com.mmbao.saas.utils.im.IMConstant;
import com.mmbao.saas.utils.im.domain.IMHelper;

import org.json.JSONObject;

/**
 * Author    LeoCheung
 * Version   V1.0
 * Email     leocheung4ever@gmail.com
 * Date      2016-04-04 15:51
 * Description  评价聊天行对象
 * Date          Author          Version          Description
 * ------------------------------------------------------------------
 * 2016/4/4     LeoCheung       1.0              1.0
 * Why & What is modified:
 */
public class ChatRowEvaluation extends EaseChatRow {

    Button btnEval;

    public ChatRowEvaluation(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        if (IMHelper.getInstance().isEvalMessage(message)) {
            inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ? R.layout.em_row_received_satisfaction
                    : R.layout.em_row_sent_satisfaction, this);
        }
    }

    @Override
    protected void onFindViewById() {
        btnEval = (Button) findViewById(R.id.btn_eval);

    }

    @Override
    protected void onUpdateView() {

    }

    @Override
    protected void onSetUpView() {
        try {
            final JSONObject jsonObj = message.getJSONObjectAttribute(IMConstant.WEICHAT_MSG);
            if(jsonObj.has("ctrlType")&&!jsonObj.isNull("ctrlType")){
                btnEval.setEnabled(true);
                btnEval.setText("立即评价");

                btnEval.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Activity)context).startActivityForResult(new Intent(context,SatisfactionActivity.class)
                                .putExtra("msgId", message.getMsgId()), ChatFragment.REQUEST_CODE_EVAL);
                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBubbleClick() {

    }
}
