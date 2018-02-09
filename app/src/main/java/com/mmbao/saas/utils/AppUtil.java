package com.mmbao.saas.utils;

import android.text.TextUtils;

/**
 * Created by bajieaichirou on 17/9/6.
 */
public class AppUtil {

    //----------------------AppName------------------------------


    //----------------------手机号码验证------------------------------
    /**
     * 验证手机格式
     */
    public static boolean mobilePattern(String mobile) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobile)) return false;
        else return mobile.matches(telRegex);
    }


    //----------------------防止快速点击------------------------------
    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    //----------------------清空sp保存数据------------------------------
    public static void clearUserInfo(){
        SharedPrenfenceUtil sp = new SharedPrenfenceUtil();
        sp.putStringValue(Constant.SHARED_PRENFENCE_ACCOUNT, "");
        sp.putStringValue(Constant.SHARED_PRENFENCE_PASSWORD, "");
        sp.putStringValue(Constant.SHARED_PRENFENCE_USERNAME, "");
        sp.putStringValue(Constant.SHARED_PRENFENCE_PHONE, "");
        sp.commit();
    }
}
