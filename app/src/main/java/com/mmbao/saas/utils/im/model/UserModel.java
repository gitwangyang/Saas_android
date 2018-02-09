package com.mmbao.saas.utils.im.model;

import android.content.Context;

import com.mmbao.saas.utils.im.domain.PreferenceManager;


/**
 * Author    LeoCheung
 * Version   V1.0
 * Email     leocheung4ever@gmail.com
 * Date      2016-04-04 14:21
 * Description  用户对象
 * Date          Author          Version          Description
 * ------------------------------------------------------------------
 * 2016/4/4     LeoCheung       1.0              1.0
 * Why & What is modified:
 */
public class UserModel {

    protected Context context = null;

    public UserModel(Context ctx) {
        context = ctx;
        PreferenceManager.init(context);
    }

    /**
     * 设置当前用户的环信id
     *
     * @param username
     */
    public void setCurrentUserName(String username) {
        PreferenceManager.getInstance().setCurrentUserName(username);
    }

    /**
     * 设置当前用户的环信密码
     */
    public void setCurrentUserPwd(String password){
        PreferenceManager.getInstance().setCurrentUserPwd(password);
    }


    /**
     * 获取当前用户的环信id
     */
    public String getCurrentUsernName() {
        return PreferenceManager.getInstance().getCurrentUsername();
    }
}
