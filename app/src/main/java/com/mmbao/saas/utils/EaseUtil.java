package com.mmbao.saas.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.mmbao.saas.App;

/**
 * 环信IM工具类
 * Description:com.mmbao.saas.utils类
 * Created by Administrator on 2018/2/6.
 * Maxim:There is no smoke without fire
 */
public class EaseUtil {
    /**
     * 获取环信AppKey
     */
    public static String getEasemobAppKey() {
        String easemob_appkey = "";
        try {
            ApplicationInfo appInfo = App.app.getPackageManager().getApplicationInfo(App.app.getPackageName(), PackageManager.GET_META_DATA);
            easemob_appkey = appInfo.metaData.getString("EASEMOB_APPKEY");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return easemob_appkey;
    }

    /**
     * 获取环信IM服务号:电线电缆
     */
    public static String getEasemobIMCode1() {
        String easemob_im_code = "";
        try {
            ApplicationInfo appInfo = App.app.getPackageManager().getApplicationInfo(App.app.getPackageName(), PackageManager.GET_META_DATA);
            easemob_im_code = appInfo.metaData.getString("EASEMOB_IM_CODE_1");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return easemob_im_code;
    }
    /**
     * 获取环信IM服务号:电工电气&金具附件
     */
    public static String getEasemobIMCode2() {
        String easemob_im_code = "";
        try {
            ApplicationInfo appInfo = App.app.getPackageManager().getApplicationInfo(App.app.getPackageName(), PackageManager.GET_META_DATA);
            easemob_im_code = appInfo.metaData.getString("EASEMOB_IM_CODE_2");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return easemob_im_code;
    }
}
