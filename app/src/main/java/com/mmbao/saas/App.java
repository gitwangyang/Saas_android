package com.mmbao.saas;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.easemob.chat.EMChat;
import com.mmbao.saas.network.APIService;
import com.mmbao.saas.utils.AppUtil;
import com.mmbao.saas.utils.CaheInterceptor;
import com.mmbao.saas.utils.Constant;
import com.mmbao.saas.utils.EaseUtil;
import com.mmbao.saas.utils.ExitUtils;
import com.mmbao.saas.utils.exception.CrashHandler;
import com.mmbao.saas.utils.DeviceUuidFactory;
import com.mmbao.saas.utils.LogcatUtil;
import com.mmbao.saas.utils.UserInfo;
import com.mmbao.saas.db.IMUserDBHelper;
import com.mmbao.saas.utils.im.domain.IMHelper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bajieaichirou on 2017/09/06.
 */
public class App extends Application {
    private static Context context;
    private Cache cache;
    private File httpCacheDirectory;
    public static App app;
    public static APIService service;

    private static final int DEFAULT_TIMEOUT = 20;
    private static final String TAG = "Application";


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        LogcatUtil.DEBUG = BuildConfig.DEBUG;
        context = getApplicationContext();

        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        initRetrofit();
        initIMUser();
        initEaseUi();
    }

    /**
     * 环信im聊天
     * 即时通讯数据库配置
     */
    private void initIMUser() {
        IMUserDBHelper imdb = new IMUserDBHelper(this);
        imdb.getWritableDatabase();
        imdb.close();
    }

    /**
     * 初始化环信
     * 获取环信AppKey
     */
    private void initEaseUi() {
        String easemob_appKey = EaseUtil.getEasemobAppKey();
        EMChat.getInstance().setAppkey(easemob_appKey);
        System.out.println("easemob_appKey = " + easemob_appKey);
        IMHelper.getInstance().init(this);
    }

    private void initRetrofit() {
        //设置缓存 20M
        try {
            if (null == httpCacheDirectory){
                httpCacheDirectory = new File(app.getCacheDir(), "responses");
            }
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);//设置缓存 20M
            }
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        okhttp3.Request request = chain.request().newBuilder()
                                .addHeader(Constant.USER_AGENT, Constant.USER_AGENT_VALUE)
                                .addHeader(Constant.IMEI, DeviceUuidFactory.getUUID(context))
                                .addHeader(Constant.TOKEN, UserInfo.getInstance(context).getToken())
                                .addHeader(Constant.MEMBER_ID, UserInfo.getInstance(context).getMemberid())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new CaheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cache(cache).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.appUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(APIService.class);
        LogcatUtil.d("App     service  = " +service);
    }

    public static Context getContext() {
        return context;
    }


    // activity管理：结束所有activity
    public void finishAllActivity() {
        ExitUtils.getInstance().finishAllActivity();
    }

    public void returnToLogin() {
        finishAllActivity();
        UserInfo.getInstance(context).setMemberid("");
        AppUtil.clearUserInfo();

        Toast.makeText(context, "您的账号已经在别的设备登录", Toast.LENGTH_SHORT).show();
    }
}
