package com.mmbao.saas.utils.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 第三级缓存:网络缓存的工具类
 * Created by ${Dota.Wang} on 2017/11/25.
 */
public class NetCacheUtils {
    private MemoryCacheUtils mMemoryCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;

    public NetCacheUtils(MemoryCacheUtils mMemoryCacheUtils, LocalCacheUtils mLocalCacheUtils) {
        this.mLocalCacheUtils = mLocalCacheUtils;
        this.mMemoryCacheUtils = mMemoryCacheUtils;
    }

    public void getBitmapFromNet(ImageView imageView, String url) {
        imageView.setTag(url);
        Bitmaptask task = new Bitmaptask();
        task.execute(imageView, url);
    }

    class Bitmaptask extends AsyncTask<Object, Void, Bitmap> {

        private HttpURLConnection urlConnection;
        private ImageView imageView;
        private String url;

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                if (url.equals(imageView.getTag())) {
                    imageView.setImageBitmap(bitmap);
                    System.out.print("onPostExecute");
                    mLocalCacheUtils.putBitmapToLoacl(bitmap, url);
                    mMemoryCacheUtils.putBitmapToMemory(bitmap, url);
                }
            }
        }

        @Override
        protected Bitmap doInBackground(Object[] params) {
            imageView = (ImageView) params[0];
            url = (String) params[1];
            Bitmap bitmap = downloadFromNet(url);
            return bitmap;
        }

        private Bitmap downloadFromNet(String url) {
            try {
                urlConnection = (HttpURLConnection) new URL(url).openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setRequestMethod("GET");
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    InputStream inputStream = urlConnection.getInputStream();
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, opts);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                urlConnection.disconnect();
            }
            return null;
        }
    }
}