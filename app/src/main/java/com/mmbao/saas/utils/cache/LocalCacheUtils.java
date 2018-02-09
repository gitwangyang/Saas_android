package com.mmbao.saas.utils.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.mmbao.saas.utils.LogcatUtil;
import com.mmbao.saas.utils.Md5Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 第二级缓存:本地缓存（SD卡）的工具类
 * Created by ${Dota.Wang} on 2017/11/25.
 */
public class LocalCacheUtils {
    public static void putBitmapToLoacl(Bitmap bitmap, String url) {
        String encode = Md5Utils.encode(url);
        File file = new File(Environment.getExternalStorageDirectory(), encode);
        LogcatUtil.i("putBitmapToLoacl: " + file.toString());
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmapToLoacl(String url) {
        String encode = Md5Utils.encode(url);
        File file = new File(Environment.getExternalStorageDirectory(), encode);
        if (file.exists()) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 3;
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, opts);
                return bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}