package com.mmbao.saas.utils.im;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.king.app.m_easelib.ui.EaseChatFragment;
import com.king.app.m_easelib.widget.EaseTitleBar;
import com.king.app.m_easelib.widget.chatrow.EaseChatRow;
import com.king.app.m_easelib.widget.chatrow.EaseCustomChatRowProvider;
import com.mmbao.saas.App;
import com.mmbao.saas.R;
import com.mmbao.saas.utils.LogcatUtil;
import com.mmbao.saas.utils.UserInfo;
import com.mmbao.saas.utils.im.chatrow.ChatRowEvaluation;
import com.mmbao.saas.utils.im.chatrow.ChatRowPictureText;
import com.mmbao.saas.utils.im.chatrow.ChatRowRobotMenu;
import com.mmbao.saas.utils.im.domain.CableMessageEntity;
import com.mmbao.saas.utils.im.domain.IMHelper;
import com.mmbao.saas.utils.im.domain.OrderMessageEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author    LeoCheung
 * Version   V1.0
 * Email     leocheung4ever@gmail.com
 * Date      2016-04-03 18:04
 * Description  聊天界面Fragment
 * Date          Author          Version          Description
 * ------------------------------------------------------------------
 * 2016/4/3     LeoCheung       1.0              1.0
 * Why & What is modified:
 */
public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentListener {

    //Constants 定义发送的类型常量 请求接收码
    private static final int ITEM_FILE = 11; //文件
    private static final int ITEM_SHORT_CUT_MESSAGE = 12; //常用语
    public static final int REQUEST_CODE_CONTEXT_MENU = 14;

    private static final int MESSAGE_TYPE_SENT_PICTURE_TXT = 1;
    private static final int MESSAGE_TYPE_RECV_PICTURE_TXT = 2;
    private static final int MESSAGE_TYPE_SENT_ROBOT_MENU = 3;
    private static final int MESSAGE_TYPE_RECV_ROBOT_MENU = 4;

    // evaluation
    private static final int MESSAGE_TYPE_SENT_EVAL = 5;
    private static final int MESSAGE_TYPE_RECV_EVAL = 6;

    // 转接客服信息
    private static final int MESSAGE_TYPE_SENT_TRANSFER_TO_KEFU = 7;
    private static final int MESSAGE_TYPE_RECV_TRANSFER_TO_KEFU = 8;

    private static final int REQUEST_CODE_SELECT_FILE = 11; //请求码 到选择文件

    public static final int REQUEST_CODE_EVAL = 26;//请求码

    private static final int REQUEST_CODE_SHORTCUT = 27; //请求码 到常用语

    //判断进入聊天界面的入口
    protected int messageToIndex = IMConstant.MESSAGE_TO_DEFAULT;

    private String imGroupName = "";

    protected String currentUserNick;
    private OrderMessageEntity orderMessageEntity;
    private CableMessageEntity cableMessageEntity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //商品信息
        orderMessageEntity = (OrderMessageEntity) fragmentArgs.getSerializable("prtInfo");
        //技能组
        messageToIndex = fragmentArgs.getInt(IMConstant.MESSAGE_TO_INTENT_EXTRA, IMConstant.MESSAGE_TO_DEFAULT);
        imGroupName = fragmentArgs.getString("imGroupName");

        //电缆属性
        cableMessageEntity = (CableMessageEntity) fragmentArgs.getSerializable("cableInfo");

        // TODO: 2016/4/6 获取昵称，显示在侧边栏
        if (UserInfo.getInstance(App.app).isLogin()) {
            currentUserNick = "mmbao_android_" + UserInfo.getInstance(App.app).getMemberid();
        } else {
            currentUserNick = "游客";
        }
        LogcatUtil.e("isLogin = " + UserInfo.getInstance(App.app).isLogin() + "       currentUserNick = " + currentUserNick);
//        if (savedInstanceState == null && orderMessageEntity != null) {
//
//            LogcatUtil.e("imgUrl = " + orderMessageEntity.getImgUrl() + "\n"
//                    + "技能组 = " + messageToIndex + "\n"
//                    + "昵称 = " + currentUserNick + "\n"
//                    + "msgtype = " + orderMessageEntity.getJSONObject());
//
//            EMMessage message = EMMessage.createTxtSendMessage("客服图文混排消息", toChatUsername);
//            message.setAttribute("msgtype", orderMessageEntity.getJSONObject());
//            sendMessage(message);
//        }

        if (savedInstanceState == null){
            if (orderMessageEntity != null) {

                LogcatUtil.e("imgUrl = " + orderMessageEntity.getImgUrl() + "\n"
                        + "技能组 = " + messageToIndex + "\n"
                        + "昵称 = " + currentUserNick + "\n"
                        + "msgtype = " + orderMessageEntity.getJSONObject());

                EMMessage message = EMMessage.createTxtSendMessage("客服图文混排消息", toChatUsername);
                message.setAttribute("msgtype", orderMessageEntity.getJSONObject());
                sendMessage(message);
            }
            if (cableMessageEntity != null){
                EMMessage message = EMMessage.createTxtSendMessage(cableMessageEntity.getMsg(), toChatUsername);
                message.setAttribute("msgtype", cableMessageEntity.getJSONObject());
                sendMessage(message);
            }
        }

        ((EaseTitleBar) getView().findViewById(com.king.app.m_easelib.R.id.title_bar)).setTitle(imGroupName);


        messageList.setShowUserNick(true);
    }

    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
        super.setUpView();
    }

    @Override
    protected void registerExtendMenuItem() {
        //这里不覆盖父类已经注册的Item, Item点击listener沿用父类的
        super.registerExtendMenuItem();

        //增加扩展item
        //        inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);
        // 增加扩展item
        //        inputMenu.registerExtendMenuItem(R.string.attach_short_cut_message, R.drawable.em_icon_answer, ITEM_SHORT_CUT_MESSAGE, extendMenuItemClickListener);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: //复制消息
                    clipboard.setText(((TextMessageBody) contextMenuMessage.getBody()).getMessage());
                    break;

                case ContextMenuActivity.RESULT_CODE_DELETE: //删除消息
                    conversation.removeMessage(contextMenuMessage.getMsgId());
                    messageList.refresh();
                    break;

                case ContextMenuActivity.RESULT_CODE_FORWARD: //转发消息
                    Toast.makeText(getActivity(), "转发消息有待开发", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_EVAL) {
                messageList.refresh();
            } else if (requestCode == REQUEST_CODE_SHORTCUT) {
                String content = data.getStringExtra("content");
                if (!TextUtils.isEmpty(content)) {
                    inputMenu.setInputMessage(content);
                }
            } else if (requestCode == REQUEST_CODE_SELECT_FILE) {
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        sendFileByUri(uri);
                    }
                }
            }
        }
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        //设置消息扩展属性

        //设置用户信息(昵称 qq等等)
        setUserInfoAttribute(message);

        pointToSkillGroup(message, messageToIndex == 1 ? "dxdl" : "dgdq");
        LogcatUtil.e("当前技能组：" + (messageToIndex == 1 ? "电线电缆" : "电工电气"));
    }

    /**
     * 技能组分组 发消息到某个组
     */
    private void pointToSkillGroup(EMMessage message, String groupName) {
        try {
            JSONObject weichatJson = getWeichatJSONObject(message);
            weichatJson.put("queueName", groupName);
            message.setAttribute("weichat", weichatJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取消息中的扩展 weichat是否存在并返回jsonObject
     */
    private JSONObject getWeichatJSONObject(EMMessage message) {
        JSONObject weichatJson = null;
        try {
            String weichatString = message.getStringAttribute("weichat", null);
            if (weichatString == null) {
                weichatJson = new JSONObject();
            } else {
                weichatJson = new JSONObject(weichatString);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weichatJson;
    }

    /**
     * 设置用户的属性
     * 通过消息的扩展  传递客服系统用户的属性信息
     */
    private void setUserInfoAttribute(EMMessage message) {
        if (TextUtils.isEmpty(currentUserNick)) {
            currentUserNick = EMChatManager.getInstance().getCurrentUser();
        }
        JSONObject weichatJson = getWeichatJSONObject(message);
        try {
            JSONObject visitorJson = new JSONObject();
            visitorJson.put("userNickname", currentUserNick);//visitorJson.put("userNickname", "");
            visitorJson.put("trueName", UserInfo.getInstance(App.app).getAccount());
            visitorJson.put("qq", "");
            visitorJson.put("phone", UserInfo.getInstance(App.app).getPhone());
            visitorJson.put("companyName", "");
            visitorJson.put("description", "");
            visitorJson.put("email", UserInfo.getInstance(App.app).getEmail());
            weichatJson.put("visitor", visitorJson);
            LogcatUtil.e("userNickname = " + currentUserNick);
            LogcatUtil.e("trueName = " + UserInfo.getInstance(App.app).getAccount());
            LogcatUtil.e("phone = " + UserInfo.getInstance(App.app).getPhone());
            message.setAttribute("weichat", weichatJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setVisitorInfoSrc(EMMessage message) {
        //传递用户的属性到自定义的iframe界面
        String strName = "name-test.json from hxid:" + EMChatManager.getInstance().getCurrentUser();
        JSONObject cmdJson = new JSONObject();
        try {
            JSONObject updateVisitorInfosrcJson = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            paramsJson.put("name", strName);
            updateVisitorInfosrcJson.put("params", paramsJson);
            cmdJson.put("updateVisitorInfoSrc", updateVisitorInfosrcJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        message.setAttribute("cmd", cmdJson);
    }

    /**
     * 指向某个具体客服，
     *
     * @param message 消息
     * @param agentUsername 客服的登录账号
     */
    private void pointToAgentUser(EMMessage message, String agentUsername) {
        try {
            JSONObject weichatJson = getWeichatJSONObject(message);
            weichatJson.put("agentUsername", agentUsername);
            message.setAttribute("weichat", weichatJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //指向某个客服 , 当会话同时指定了客服和技能组时，以指定客服为准，指定技能组失效。
        //		pointToAgentUser(message, "ceshia@qq.com");
    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {
        //头像点击事件
        //      Intent intent = new Intent(getActivity(), UserProfileActivity.class);
        //		intent.putExtra("username", username);
        //		startActivity(intent);
        //        TT.showShort(getActivity(), "您点击了" + currentUserNick + "头像");
    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件 这里不做覆盖 如需覆盖 比如后期优化复制删除弹出样式可以再次重写 并返回true
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        //当消息长按时 弹出ContextMenuActivity 并发送requestCode
        //当返回为主界面时 回调onActivityResult()方法
        startActivityForResult(new Intent(getActivity(), ContextMenuActivity.class).putExtra("message", message), REQUEST_CODE_CONTEXT_MENU);
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        //此处扩展发送内容项 基类涉及的:拍照 图片 位置分享
        //子类加入一般文件  常用语 etc.
        switch (itemId) {
            case ITEM_FILE:
                //这里通过系统api选择文件,以后再优化
                selectFileFromLocal();
                break;

            case ITEM_SHORT_CUT_MESSAGE:
                Intent intent = new Intent(getActivity(), ShortCutMsgActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SHORTCUT);
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;

            default:
                break;
        }
        //不覆盖已有的点击事件
        return false;
    }

    /**
     * 选择文件
     */
    private void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 22) { //19以后该api不可用
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        //设置自定义listview item提供者
        return new CustomChatRowProvider();
    }

    public void sendRobotMessage(String content, String menuId) {
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        if (!TextUtils.isEmpty(menuId)) {
            JSONObject msgTypeJson = new JSONObject();
            try {
                JSONObject choiceJson = new JSONObject();
                choiceJson.put("menuid", menuId);
                msgTypeJson.put("choice", choiceJson);
            } catch (Exception e) {
            }
            message.setAttribute("msgtype", msgTypeJson);
        }
        sendMessage(message);
    }

    /**
     * 聊天行提供者
     */
    private class CustomChatRowProvider implements EaseCustomChatRowProvider {

        @Override
        public int getCustomChatRowTypeCount() {
            //此处返回的数目为getCustomChatRowType 中的布局个数
            return 8;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                if (IMHelper.getInstance().isRobotMenuMessage(message)) {
                    // 机器人 列表菜单
                    return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_ROBOT_MENU : MESSAGE_TYPE_SENT_ROBOT_MENU;
                } else if (IMHelper.getInstance().isEvalMessage(message)) {
                    // 满意度评价
                    return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_EVAL : MESSAGE_TYPE_SENT_EVAL;
                } else if (IMHelper.getInstance().isPictureTxtMessage(message)) {
                    // 订单图文组合
                    return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_PICTURE_TXT : MESSAGE_TYPE_SENT_PICTURE_TXT;
                } else if (IMHelper.getInstance().isTransferToKefuMsg(message)) {
                    //转人工消息
                    return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TRANSFER_TO_KEFU : MESSAGE_TYPE_SENT_TRANSFER_TO_KEFU;
                }
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                if (IMHelper.getInstance().isRobotMenuMessage(message)) {
                    return new ChatRowRobotMenu(getActivity(), message, position, adapter);
                } else if (IMHelper.getInstance().isEvalMessage(message)) {
                    return new ChatRowEvaluation(getActivity(), message, position, adapter);
                } else if (IMHelper.getInstance().isPictureTxtMessage(message)) {
                    return new ChatRowPictureText(getActivity(), message, position, adapter);
                } else if (IMHelper.getInstance().isTransferToKefuMsg(message)) {
//                                                            return new ChatRowTransferToKefu(getActivity(), message, position, adapter);
                }
            }
            return null;
        }
    }
}
