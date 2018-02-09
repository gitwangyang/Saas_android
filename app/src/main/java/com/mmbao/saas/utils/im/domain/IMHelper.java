package com.mmbao.saas.utils.im.domain;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.exceptions.EaseMobException;
import com.easemob.util.EMLog;
import com.king.app.m_easelib.EaseConstant;
import com.king.app.m_easelib.controller.EaseUI;
import com.king.app.m_easelib.domain.EaseEmojicon;
import com.king.app.m_easelib.domain.EaseEmojiconGroupEntity;
import com.king.app.m_easelib.domain.EaseUser;
import com.king.app.m_easelib.model.EaseNotifier;
import com.king.app.m_easelib.model.EmojiconGroupData;
import com.king.app.m_easelib.utils.EaseCommonUtils;
import com.king.app.m_easelib.utils.EaseUserUtils;
import com.mmbao.saas.App;
import com.mmbao.saas.R;
import com.mmbao.saas.activity.login.controller.LoginActivity;
import com.mmbao.saas.utils.EaseUtil;
import com.mmbao.saas.utils.LogcatUtil;
import com.mmbao.saas.utils.SharedPrenfenceUtil;
import com.mmbao.saas.utils.im.IMActivity;
import com.mmbao.saas.utils.im.IMConstant;
import com.mmbao.saas.utils.im.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import butterknife.Bind;


/**
 * Author    LeoCheung
 * Version   V1.0
 * Email     leocheung4ever@gmail.com
 * Date      2016-04-04 14:08
 * Description  IM辅助类
 * Date          Author          Version          Description
 * ------------------------------------------------------------------
 * 2016/4/4     LeoCheung       1.0              1.0
 * Why & What is modified:
 */
public class IMHelper {
//    @Bind(R.id.footer_im_msgcount_txt) TextView  footer_im_msgcount_txt;

    private static final String TAG = IMHelper.class.getSimpleName();

    private EaseUI easeUI;
    private UserModel userModel;

    //全局事件监听器
    private EMEventListener eventListener;
    private EMConnectionListener connectionListener;

    private Context appContext;

    private static IMHelper instance = null;

    private boolean alreadyNotified = false;
    private String username;
    private Map<String, EaseUser> contactList;
    public boolean isVoiceCalling;
    public boolean isVideoCalling;
    //私有化 防止在外部被直接new对象

    private IMHelper() {
    }

    public synchronized static IMHelper getInstance() {
        if (instance == null) {
            instance = new IMHelper();
        }
        return instance;
    }

    /**
     * 初始化辅助类
     */
    public void init(Context context) {
        if (EaseUI.getInstance().init(context)) {
            appContext = context;
            //在小米手机上当app被kill时使用小米推送进行消息提示，SDK已支持，可选
//            EMChatManager.getInstance().setMipushConfig("2882303761517370134", "5131737040134");
            //设为调试模式，打成正式包时，最好设为false，以免消耗额外的资源
            EMChat.getInstance().setDebugMode(true);
            //get easeui instance
            easeUI = EaseUI.getInstance();
            //调用easeui的api设置providers
            setEaseUIProviders();
            userModel = new UserModel(context);
            //初始化PreferenceManager
            PreferenceManager.init(context);
            //设置全局监听
            setGlobalListeners();
//			broadcastManager = LocalBroadcastManager.getInstance(appContext);
        }
    }

    //todo 此处设置客服 待研究
    private void setEaseUIProviders() {
        //设置昵称头像
        //此方法设置需要考虑是发送方还是接收方
        easeUI.setEaseUserInfoProvider(new EaseUI.EaseUserInfoProvider() {
            @Override
            public void setNickAndAvatar(Context context, EMMessage message, ImageView userAvatarView, TextView usernickView) {
                JSONObject jsonAgent = getAgentInfoByMessage(message);
                if (message.direct == EMMessage.Direct.SEND) {
                    EaseUserUtils.setUserAvatar(context, EMChatManager.getInstance().getCurrentUser(), userAvatarView);
                    //发送方不显示nick
//                    UserUtils.setUserNick(EMChatManager.getInstance().getCurrentUser(), usernickView);
                } else {
                    if (jsonAgent ==null){
                        userAvatarView.setImageResource(R.drawable.ease_default_avatar);
                        usernickView.setText(message.getFrom());
                    }else {
                        String strNick = null;
                        String strUrl = null;
                        try {
                            strNick = jsonAgent.getString("userNickname");
                            strUrl = jsonAgent.getString("avatar");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //TODO 设置客服昵称
                        if (!TextUtils.isEmpty(strNick)) {
                            usernickView.setText(strNick);
                        } else {
                            usernickView.setText(message.getFrom());
                        }
                        //TODO 设置客服头像
                        if (!TextUtils.isEmpty(strUrl)) {
                            if (!strUrl.startsWith("http")) {
                                strUrl = "http:" + strUrl;
                            }
                            //正常的string路径
                            Glide.with(context).load(strUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar).into(userAvatarView);
                        } else {
                            Glide.with(context).load(R.drawable.ease_default_avatar).into(userAvatarView);
                        }
                    }
                }
            }
        });

        //不设置的话 则使用环信默认的方式
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {
            @Override
            public String getDisplayedText(EMMessage message) {
                //设置状态栏的消息提示,可以根据message的类型做相应提示
                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                return message.getFrom() + ": " + ticker;
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                // TODO: 2016/8/13 收到推送消息
                int unreadMsgCountTotal = 0;

                LogcatUtil.e("收到推送消息，来源：" + message.getFrom());
                if(message.getFrom().equals(EaseUtil.getEasemobIMCode1())){
                    SharedPrenfenceUtil.getInstance().putBooleanValue("DXDL", true);
                }else if(message.getFrom().equals(EaseUtil.getEasemobIMCode2())){
                    SharedPrenfenceUtil.getInstance().putBooleanValue("DGDQ_JJFJ", true);
                }

                unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
                return  "有" + unreadMsgCountTotal + "条消息";
            }

            @Override
            public String getTitle(EMMessage message) {
                //修改标题  这里使用默认
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                // TODO: 2016/8/13 设置IM推送小图标，0为默认icon 
                return R.mipmap.ic_mmbao_logo_alpha;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                //设置点击通知栏跳转事件
                if (IMActivity.activityInstance != null) {
                    IMActivity.activityInstance.finish();
                }
                Intent intent = new Intent(appContext, IMActivity.class);
                EMMessage.ChatType chatType = message.getChatType();
                if (chatType == EMMessage.ChatType.Chat) { //单聊信息
                    intent.putExtra(EaseConstant.EXTRA_USER_ID,message.getFrom());
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, IMConstant.CHATTYPE_SINGLE);
                    intent.putExtra(EaseConstant.EXTRA_SHOW_USERNICK, true);
                }
                return intent;
            }
        });
        //设置表情provider
        easeUI.setEmojiconInfoProvider(new EaseUI.EaseEmojiconInfoProvider() {
            @Override
            public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
                EaseEmojiconGroupEntity data = EmojiconGroupData.getData();
                for (EaseEmojicon emojicon : data.getEmojiconList()) {
                    if (emojicon.getIdentityCode().equals(emojiconIdentityCode)) {
                        return emojicon;
                    }
                }
                return null;
            }

            @Override
            public Map<String, Object> getTextEmojiconMapping() {
                //返回文字表情emoji文本和图片(resource id或者本地路径)的映射map
                return null;
            }
        });
    }

    /**
     * 显示客服昵称和头像信息
     *
     * @param message
     * @return
     */
    public JSONObject getAgentInfoByMessage(EMMessage message) {
        try {
            JSONObject jsonWeichat = message.getJSONObjectAttribute(IMConstant.WEICHAT_MSG);
            if (jsonWeichat == null) {
                return null;
            }
            if (jsonWeichat.has("agent") && !jsonWeichat.isNull("agent")) {
                return jsonWeichat.getJSONObject("agent");
            }
        } catch (EaseMobException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置全局事件监听
     */
    private void setGlobalListeners() {
        connectionListener = new EMConnectionListener() {
            @Override
            public void onConnected() {
                IMHelper.getInstance().notifyForReceivingEvents();
            }

            @Override
            public void onDisconnected(int error) {
                if (error == EMError.USER_REMOVED) {
                    onCurrentAccountRemoved();
                } else if (error == EMError.CONNECTION_CONFLICT) {
                    onConnectionConflict();
                }
            }
        };

        //注册链接监听
        EMChatManager.getInstance().addConnectionListener(connectionListener);
        //注册消息事件监听
        registerEventListener();
    }

    public synchronized void notifyForReceivingEvents() {
        if (alreadyNotified) {
            return;
        }
        // 通知sdk，UI 已经初始化完毕，注册了相应的receiver和listener, 可以接受broadcast了
        EMChat.getInstance().setAppInited();
        alreadyNotified = true;
    }

    /**
     * 账号被移除
     */
    private void onCurrentAccountRemoved() {
        LogcatUtil.i(" onCurrentAccountRemoved");
        App.app.returnToLogin();//app重置到登录界面
        Intent intent = new Intent(appContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(IMConstant.ACCOUNT_REMOVED, true);
        appContext.startActivity(intent);
    }

    /**
     * 账号再别的设备登录
     */
    private void onConnectionConflict() {
        LogcatUtil.i(" onConnectionConflict");
        App.app.returnToLogin();//app重置到登录界面
        Intent intent = new Intent(appContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(IMConstant.ACCOUNT_CONFLICT, true);
        appContext.startActivity(intent);
    }

    /**
     * 全局事件监听
     * 因为可能会有UI页面先处理到这个消息，所以一般如果UI页面已经处理，这里就不需要再次处理
     * activityList.size() <= 0 意味着所有页面都已经在后台运行，或者已经离开Activity Stack
     */
    protected void registerEventListener() {
        eventListener = new EMEventListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onEvent(EMNotifierEvent event) {
                EMMessage message = null;
                if (event.getData() instanceof EMMessage) {
                    message = (EMMessage) event.getData();
                    EMLog.d(TAG, "receive the event : " + event.getEvent() + ",id : " + message.getMsgId());
                }

                switch (event.getEvent()) {
                    case EventNewMessage:
                        //应用在后台，不需要刷新UI,通知栏提示新消息
                        if (!easeUI.hasForegroundActivies()) {

                            getNotifier().onNewMsg(message);
//                            setEaseUIProviders();

                        }
//                        else{
//
//                            footer_im_msgcount_txt.setVisibility(View.VISIBLE);
//                        }
                        break;
                    case EventOfflineMessage:
//                        List<EMMessage> messages = (List<EMMessage>) event.getData();
//                        getNotifier().onNewMesg(messages);
//                        setEaseUIProviders();
                        if (!easeUI.hasForegroundActivies()) {
                            EMLog.d(TAG, "received offline messages");
                            List<EMMessage> messages = (List<EMMessage>) event.getData();
                            getNotifier().onNewMesg(messages);

                        }
                        break;
                    // below is just giving a example to show a cmd toast, the app should not follow this
                    // so be careful of this
                    case EventNewCMDMessage:
//                        EMLog.d(TAG, "收到透传消息");
                        //获取消息body
                        CmdMessageBody cmdMsgBody = (CmdMessageBody) message.getBody();
                        final String action = cmdMsgBody.action;//获取自定义action

                        //获取扩展属性 此处省略
                        //message.getStringAttribute("");
//                        EMLog.d(TAG, String.format("透传消息：action:%s,message:%s", action, message.toString()));
                        final String str = appContext.getString(R.string.receive_the_passthrough);

                        final String CMD_TOAST_BROADCAST = "easemob.demo.cmd.toast";
                        IntentFilter cmdFilter = new IntentFilter(CMD_TOAST_BROADCAST);

                        if (broadCastReceiver == null) {
                            broadCastReceiver = new BroadcastReceiver() {

                                @Override
                                public void onReceive(Context context, Intent intent) {
//                                    Toast.makeText(appContext, intent.getStringExtra("cmd_value"), Toast.LENGTH_SHORT).show();
                                }
                            };

                            //注册广播接收者
                            appContext.registerReceiver(broadCastReceiver, cmdFilter);
                        }

                        Intent broadcastIntent = new Intent(CMD_TOAST_BROADCAST);
                         broadcastIntent.putExtra("cmd_value", str + action);
                        appContext.sendBroadcast(broadcastIntent, null);

                        break;

                    case EventDeliveryAck:
                        message.setDelivered(true);
                        break;
                    case EventReadAck:
                        message.setAcked(true);
                        break;
                    // add other events in case you are interested in
                    default:
                        break;
                }
            }
        };

        EMChatManager.getInstance().registerEventListener(eventListener);
    }

    /**
     * 获取消息通知类
     *
     * @return
     */
    public EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }

    public void pushActivity(Activity activity) {
        easeUI.pushActivity(activity);
    }

    public void popActivity(Activity activity) {
        easeUI.popActivity(activity);
    }

    /**
     * 是否是机器人消息
     *
     * @return
     */
    public boolean isRobotMenuMessage(EMMessage message) {
        try {
            JSONObject jsonObj = message.getJSONObjectAttribute(IMConstant.MESSAGE_ATTR_MSGTYPE);
            if (jsonObj.has("choice") && !jsonObj.isNull("choice")) {
                JSONObject jsonChoice = jsonObj.getJSONObject("choice");
                if (jsonChoice.has("items") || jsonChoice.has("list")) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public String getRobotMenuMessageDigest(EMMessage message) {
        String title = "";
        try {
            JSONObject jsonObj = message.getJSONObjectAttribute(IMConstant.MESSAGE_ATTR_MSGTYPE);
            if (jsonObj.has("choice")) {
                JSONObject jsonChoice = jsonObj.getJSONObject("choice");
                title = jsonChoice.getString("title");
            }
        } catch (Exception e) {
        }
        return title;
    }

    //it is evaluation message
    public boolean isEvalMessage(EMMessage message) {
        try {
            JSONObject jsonObj = message.getJSONObjectAttribute(IMConstant.WEICHAT_MSG);
            if (jsonObj.has("ctrlType")) {
                try {
                    String type = jsonObj.getString("ctrlType");
                    if (!TextUtils.isEmpty(type) && (type.equalsIgnoreCase("inviteEnquiry") || type.equalsIgnoreCase("enquiry"))) {
                        return true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (EaseMobException e) {
        }
        return false;
    }

    /**
     * 检测是否为订单消息或者为轨迹消息
     *
     * @param message
     * @return
     */
    public boolean isPictureTxtMessage(EMMessage message) {
        JSONObject jsonObj = null;
        try {
            jsonObj = message.getJSONObjectAttribute(IMConstant.MESSAGE_ATTR_MSGTYPE);
        } catch (EaseMobException e) {
        }
        if (jsonObj == null) {
            return false;
        }
        if (jsonObj.has("order") || jsonObj.has("track")) {
            return true;
        }
        return false;
    }

    /**
     * 检测是否为转人工的消息，如果是则需要显示转人工的按钮
     */
    public boolean isTransferToKefuMsg(EMMessage message) {
        try {
            JSONObject jsonObj = message.getJSONObjectAttribute(IMConstant.WEICHAT_MSG);
            if (jsonObj.has("ctrlType")) {
                try {
                    String type = jsonObj.getString("ctrlType");
                    if (!TextUtils.isEmpty(type) && type.equalsIgnoreCase("TransferToKfHint")) {
                        return true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (EaseMobException e) {
        }
        return false;
    }

    /**
     * 是否登录成功过
     * @return
     */
    public boolean isLoginIn(){
        return EMChat.getInstance().isLoggedIn();
    }

    /**
     * 退出登录
     *
     * @param unbindDeviceToken
     *            是否解绑设备token(使用GCM才有)
     * @param callback
     *            callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        EMChatManager.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    /**
     * 获取当前用户的环信Id
     */
    public void setCurrentUserName(String username){
        this.username = username;
        userModel.setCurrentUserName(username);
    }

    /**
     * 设置当前用户的环信密码
     */
    public void setCurrentPassword(String password){
        userModel.setCurrentUserPwd(password);
    }

    /**
     * 获取当前用户的环信id
     */
    public String getCurrentUsernName(){
        if(username == null){
            username = userModel.getCurrentUsernName();
        }
        return username;
    }



}
