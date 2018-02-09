package com.mmbao.saas.utils.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.mmbao.saas.utils.LogcatUtil;


/**
 * 第一级缓存：内存缓存的工具类
 * Created by ${Dota.Wang} on 2017/11/25.
 */
public class MemoryCacheUtils {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCacheUtils() {
        int maxmemory = (int) Runtime.getRuntime().maxMemory();
        LogcatUtil.i("MemoryCacheUtils: " + maxmemory);
        mMemoryCache = new LruCache<String, Bitmap>(maxmemory / 8) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    public void putBitmapToMemory(Bitmap bitmap, String url) {
        LogcatUtil.i( "putBitmapToMemory: ");
        mMemoryCache.put(url, bitmap);
    }

    public Bitmap getBitmapToMemory(String url) {
        LogcatUtil.i("getBitmapToMemory: ");
        Bitmap bitmap = mMemoryCache.get(url);
        return bitmap;
    }
}