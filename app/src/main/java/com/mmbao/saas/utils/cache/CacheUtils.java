package com.mmbao.saas.utils.cache;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.mmbao.saas.utils.LogcatUtil;


/**
 * 三级缓存的管理工具类
 * Created by ${Dota.Wang} on 2017/11/25.
 */
public class CacheUtils {
    private MemoryCacheUtils mMemoryCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private NetCacheUtils mNetCacheUtils;

    public CacheUtils() {
        mMemoryCacheUtils = new MemoryCacheUtils();
        mLocalCacheUtils = new LocalCacheUtils();
        mNetCacheUtils = new NetCacheUtils(mMemoryCacheUtils, mLocalCacheUtils);
    }

    public void display(ImageView imageView, String url) {

        //内存缓存   生命周期同调用者
        Bitmap bitmap = mMemoryCacheUtils.getBitmapToMemory(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            LogcatUtil.i( "diaplay: 221111111111");
            return;
        }

        //本地缓存
        bitmap = LocalCacheUtils.getBitmapToLoacl(url);
        if (bitmap != null) {
            LogcatUtil.i( "diaplay: 1111111");
            imageView.setImageBitmap(bitmap);
            mMemoryCacheUtils.putBitmapToMemory(bitmap, url);
            return;
        }

        //网络缓存
        mNetCacheUtils.getBitmapFromNet(imageView, url);
    }
}