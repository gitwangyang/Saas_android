package com.mmbao.saas.network;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by bajieaichirou on 2017/09/06.
 */
public interface APIService {

    //----------------------------login登录--------------------------
    @GET("saas/v1/user/login.html")
    Call<JsonObject> login(@Query("username") String userName, @Query("password") String psw);

}
