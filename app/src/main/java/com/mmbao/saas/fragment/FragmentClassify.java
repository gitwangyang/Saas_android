package com.mmbao.saas.fragment;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.mmbao.saas.R;
import com.mmbao.saas.fragment.base.BaseFragment;
import com.mmbao.saas.utils.BitmapLoad;
import com.mmbao.saas.utils.LogcatUtil;
import com.mmbao.saas.utils.async.AsyncLoader;
import com.mmbao.saas.utils.async.OnLoadListener;

import butterknife.Bind;

/**
 * Created by bajieaichirou on 17/9/6.
 * 分类
 */
public class FragmentClassify extends BaseFragment implements OnLoadListener<Void, Integer, Bitmap> {

    @Bind(R.id.iv)
    ImageView iv;

    private static final String TAG="FragmentClassify";

    private AsyncLoader<Void, Integer, Bitmap> mLoader;

    public FragmentClassify() {
    }

    @Override
    public int setContentView(int layout) {
        layout = R.layout.fragment_classify;
        return layout;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        loadImg(1);
    }

    /**
     * 执行异步任务
     * @param dataType 用来区分不同异步任务
     */
    private void loadImg(int dataType) {
        mLoader = new AsyncLoader<Void, Integer, Bitmap>(dataType);
        mLoader.setOnLoadListener(this);
        mLoader.execute();
    }


    //-------------------------------------异步加载-------------------------------------------------
    @Override
    public void onDataStart() {
        LogcatUtil.i(TAG,"开始异步任务");
    }

    @Override
    public Bitmap doInWorkerThread(int dataType, Void... params) throws Exception {
        return BitmapLoad.returnBitMap("http://c.hiphotos.baidu.com/image/pic/item/6c224f4a20a446234b87678d9a22720e0df3d794.jpg");
    }

    //调用publishProgress()方法，才会执行此方法
    @Override
    public void onDataProgress(Integer... values) {
        LogcatUtil.i(TAG,""+values);
    }

    @Override
    public void onDataGet(Bitmap result) {
        LogcatUtil.i(TAG,"获得异步任务结果");
        iv.setImageBitmap(result);
    }

    @Override
    public void onDataFail(Exception e) {
        LogcatUtil.i(TAG,"异步任务失败");
    }

    @Override
    public void onDataFinish() {
        LogcatUtil.i(TAG,"结束异步任务");
    }

}
