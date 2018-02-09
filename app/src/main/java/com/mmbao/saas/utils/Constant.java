package com.mmbao.saas.utils;


import android.os.Environment;

import java.io.File;

/**
 * Created by bajieaichirou on 17/9/6.
 *
 * 常量 类
 */
public class Constant {
    public static String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

    public static String SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar;//文件分隔符

    public static String appUrl;
    public static String payUrl;
    public static String imgUrl;
    public static String imUrl;

    //服务端接口版本号
    public static String serviceVersion = "v1";

    static {
        if(LogcatUtil.DEBUG){//调试模式
            appUrl = "http://beta.mmbao.com:8096/";
            payUrl = "http://beta.mmbao.com:8096/";
            imUrl = "http://trade.mmbao.com/";
            imgUrl = "http://beta.mmbao.com:8096";
        }else{//正式环境
            appUrl = "http://saas.mmbao.com/";
            payUrl = "http://saas.mmbao.com/";
            imUrl = "http://trade.mmbao.com/";
            imgUrl = "http://www.mmbao.com/";
        }
    }

    public static final String USER_AGENT = "User-Agent";
    public static final String IMEI = "imei";
    public static final String TOKEN = "token";
    public static final String MEMBER_ID = "memberId";
    public static String USER_AGENT_VALUE = "1fy8LLuCLgc=";


    /**
     * 接口返回参数
     */
    public static final String NULL_RESPONSE = "";
    public static final String FAILURE_RESPONSE = "0";  //请求失败
    public static final String SUCCESS_RESPONSE = "1";  //请求成功
    public static final String KEEP_KEY_RESPONSE = "2"; //账号锁定

    public static final String SHARED_PRENFENCE_MEMBERID = "mmb_memberid";
    public static final String SHARED_PRENFENCE_LOGINID = "mmb_loginid";
    public static final String SHARED_PRENFENCE_ACCOUNT = "mmb_account";
    public static final String SHARED_PRENFENCE_PASSWORD = "mmb_password";
    public static final String SHARED_PRENFENCE_EMAIL = "mmb_email";
    public static final String SHARED_PRENFENCE_PHONE = "mmb_phone";
    public static final String SHARED_PRENFENCE_USERNAME = "mmb_username";
    public static final String SHARED_PRENFENCE_TOKEN = "mmb_token";
    public static final String SHARED_PRENFENCE_POINTS = "mmb_points";
    public static final String SHARED_PRENFENCE_WALLET = "mmb_wallet";
    public static final String SHARED_PRENFENCE_COUPON = "mmb_coupon";


    /**
     * @Fields sharedPreferencesname : SharedPreferences文件名
     */
    public static final String sharedPreferencesname = "MMB_v2_0";

}
