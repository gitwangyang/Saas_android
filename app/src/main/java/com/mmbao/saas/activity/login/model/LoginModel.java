package com.mmbao.saas.activity.login.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mmbao.saas.App;
import com.mmbao.saas.activity.base.CallBack;
import com.mmbao.saas.activity.login.bean.LoginBean;
import com.mmbao.saas.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:com.mmbao.saas.activity.login.model类
 * Created by Administrator on 2018/2/3.
 * Maxim:There is no smoke without fire
 */
public class LoginModel {

    private Call<JsonObject> call;
    private CallBack<LoginBean> callBack;

    public LoginModel(CallBack<LoginBean> callBack) {
        this.callBack = callBack;
    }

    public void login(Context context, String name, String password) {
        call = App.service.login(name,password);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    JsonObject object = response.body();
                    if (null!=object){
                        LoginBean bean = new Gson().fromJson(object,LoginBean.class);
                        String result = bean.getCode();
                        if (Constant.SUCCESS_RESPONSE.equals(result)){
                            callBack.onResponse(bean);
                        }else {
                            callBack.onFailure();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callBack.onFailure();
            }
        });
    }

    public void cancelGet(){
        if(call != null && call.isExecuted()){
            call.cancel();
        }
    }
}
