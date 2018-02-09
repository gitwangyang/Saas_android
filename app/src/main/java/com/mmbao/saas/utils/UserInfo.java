package com.mmbao.saas.utils;

import android.content.Context;

/**
 * 用户信息保存
 * Created by bajieaichirou on 18/1/25.
 */

public class UserInfo {
    private SaveValueToShared saveValueToShared;
    private static UserInfo instance;
    public UserInfo(Context context) {
        this.saveValueToShared = SaveValueToShared.getInstance();
    }

    public static UserInfo getInstance(Context context) {
        if (null == instance) {
            synchronized (UserInfo.class) {
                if (null == instance) {
                    instance = new UserInfo(context);
                }
            }
        }
        return instance;
    }

    public String getToken(){
        return saveValueToShared.getStringValueFromSharedPreference(Constant.SHARED_PRENFENCE_TOKEN, "");
    }
    public void setToken(String token){
        saveValueToShared.saveDataToSharedpreference(Constant.SHARED_PRENFENCE_TOKEN, token);
    }

    public String getMemberid() {
        return saveValueToShared.getStringValueFromSharedPreference(Constant.SHARED_PRENFENCE_MEMBERID, "");
    }
    public void setMemberid(String memberid) {
        saveValueToShared.saveDataToSharedpreference(Constant.SHARED_PRENFENCE_MEMBERID, memberid);
    }
    public String getLoginid() {
        return saveValueToShared.getStringValueFromSharedPreference(Constant.SHARED_PRENFENCE_LOGINID, "");
    }
    public void setLoginid(String loginid) {
        saveValueToShared.saveDataToSharedpreference(Constant.SHARED_PRENFENCE_LOGINID, loginid);
    }
    public String getAccount() {
        return saveValueToShared.getStringValueFromSharedPreference(Constant.SHARED_PRENFENCE_ACCOUNT, "");
    }
    public void setAccount(String account) {
        saveValueToShared.saveDataToSharedpreference(Constant.SHARED_PRENFENCE_ACCOUNT, account);
    }
    public String getPassword() {
        return saveValueToShared.getStringValueFromSharedPreference(Constant.SHARED_PRENFENCE_PASSWORD, "");
    }
    public void setPassword(String password) {
        saveValueToShared.saveDataToSharedpreference(Constant.SHARED_PRENFENCE_PASSWORD, password);
    }
    public String getEmail() {
        return saveValueToShared.getStringValueFromSharedPreference(Constant.SHARED_PRENFENCE_EMAIL, "");
    }
    public void setEmail(String email) {
        saveValueToShared.saveDataToSharedpreference(Constant.SHARED_PRENFENCE_EMAIL, email);
    }
    public String getPhone() {
        return saveValueToShared.getStringValueFromSharedPreference(Constant.SHARED_PRENFENCE_PHONE, "");
    }
    public void setPhone(String phone) {
        saveValueToShared.saveDataToSharedpreference(Constant.SHARED_PRENFENCE_PHONE, phone);
    }
    public String getUserName() {
        return saveValueToShared.getStringValueFromSharedPreference(Constant.SHARED_PRENFENCE_USERNAME, "");
    }
    public void setUserName(String userName) {
        saveValueToShared.saveDataToSharedpreference(Constant.SHARED_PRENFENCE_USERNAME, userName);
    }
    public String getPoints() {
        return saveValueToShared.getStringValueFromSharedPreference(Constant.SHARED_PRENFENCE_POINTS, "");
    }
    public void setPoints(String points) {
        saveValueToShared.saveDataToSharedpreference(Constant.SHARED_PRENFENCE_POINTS, points);
    }

    //红包
    public String getWallet() {
        return saveValueToShared.getStringValueFromSharedPreference(Constant.SHARED_PRENFENCE_WALLET, "");
    }
    public void setWallet(String wallet) {
        saveValueToShared.saveDataToSharedpreference(Constant.SHARED_PRENFENCE_POINTS, wallet);
    }

    //优惠券
    public String getCoupon() {
        return saveValueToShared.getStringValueFromSharedPreference(Constant.SHARED_PRENFENCE_COUPON, "");
    }
    public void setCoupon(String coupon) {
        saveValueToShared.saveDataToSharedpreference(Constant.SHARED_PRENFENCE_POINTS, coupon);
    }

    public boolean isLogin(){
        String memberid = saveValueToShared.getStringValueFromSharedPreference(Constant.SHARED_PRENFENCE_MEMBERID, "");
        if(memberid==null || memberid.equals("")){
            return false;
        }
        return true;
    }
}
