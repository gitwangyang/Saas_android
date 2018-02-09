package com.mmbao.saas.utils.im;

import com.king.app.m_easelib.EaseConstant;

/**
 * Author    LeoCheung
 * Version   V1.0
 * Email     leocheung4ever@gmail.com
 * Date      2016-04-04 14:10
 * Description  环信常量
 * Date          Author          Version          Description
 * ------------------------------------------------------------------
 * 2016/4/4     LeoCheung       1.0              1.0
 * Why & What is modified:
 */
public class IMConstant extends EaseConstant {
    //////////////////
    // IM Constants //
    /////////////////
    public static final String ACCOUNT_CONFLICT = "conflict";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String MESSAGE_ATTR_MSGTYPE = "msgtype";

    public static final String WEICHAT_MSG = "weichat";

    public static final String DEFAULT_ACCOUNT_PWD = "123456";

    public static final int MESSAGE_TO_DEFAULT = 1;
    public static final int MESSAGE_TO_PRE_SALES = 1;
    public static final int MESSAGE_TO_AFTER_SALES = 2;
    public static final String MESSAGE_TO_INTENT_EXTRA = "imGroup";

    public static final int CHATTYPE_SINGLE = 1;
    public static final int CHATTYPE_GROUP = 2;
    public static final int CHATTYPE_CHATROOM = 3;

    public static final String EXTRA_CHAT_TYPE = "chatType";
    public static final String EXTRA_USER_ID = "userId";
    public static final String EXTRA_SHOW_USERNICK = "showUserNick";
    public static final String EXTRA_SHOW_PHONE= "showUserPHONE";

}
