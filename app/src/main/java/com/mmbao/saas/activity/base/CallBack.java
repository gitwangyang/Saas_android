package com.mmbao.saas.activity.base;

/**
 * Description:com.mmbao.saas.activity.login类
 * Created by Administrator on 2018/2/3.
 * Maxim:There is no smoke without fire
 */
public interface CallBack<T> {
    void onResponse(T t);
    void onFailure();
    void timeOut();
}
