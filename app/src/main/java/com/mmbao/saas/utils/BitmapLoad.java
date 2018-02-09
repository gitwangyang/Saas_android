package com.mmbao.saas.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Description:com.mmbao.saas.utils类
 * Created by Administrator on 2018/2/5.
 * Maxim:There is no smoke without fire
 */
public class BitmapLoad {
    /**
     * 网络下载图片的方法
     * @param url 图片地址
     * @return
     */
    public static Bitmap returnBitMap(String url) {
        URL imUrl = null;
        Bitmap bitmap = null;
        try {
            imUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) imUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
