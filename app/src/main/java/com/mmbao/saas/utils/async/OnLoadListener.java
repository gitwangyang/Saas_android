package com.mmbao.saas.utils.async;

/**
 * 异步数据加载器的回调实现
 * Description:com.mmbao.saas.utils.async类
 * Created by Administrator on 2018/2/5.
 * Maxim:There is no smoke without fire
 */
/**
 *
 * @Description：异步数据回调接口类
 * <p>创建日期：2013-9-3 </p>
 * @version V1.0
 * @author LZP
 * @see
 */
public interface OnLoadListener<P, S, T> {

    /**
     *
     * @Description：异步数据加载开始时回调
     * <p>创建人：LZP ,  2013-9-3  下午9:42:58</p>
     * <p>修改人：LZP ,  2013-9-3  下午9:42:58</p>
     *
     *
     */
    void onDataStart();

    /**
     *
     * @Description：异步数据请求回调，运行子线程中
     * <p>创建人：LZP ,  2013-9-3  下午9:43:21</p>
     * <p>修改人：LZP ,  2013-9-3  下午9:43:21</p>
     *
     * @param dataType
     * @return
     * @throws Exception
     *
     */
    T doInWorkerThread(int dataType,P... params) throws Exception;

    /**
     *
     * @Description：异步数据进行的进度回调，用来显示进度条或更新UI等
     * <p>创建人：LZP ,  2013-9-3  下午9:43:21</p>
     * <p>修改人：LZP ,  2013-9-3  下午9:43:21</p>
     *
     * @param values
     * @return
     * @throws Exception
     *
     */
    void onDataProgress(S... values);

    /**
     *
     * @Description：异步数据返回时回调
     * <p>创建人：LZP ,  2013-9-3  下午9:43:59</p>
     * <p>修改人：LZP ,  2013-9-3  下午9:43:59</p>
     *
     * @param result
     * void
     */
    void onDataGet(T result);

    /**
     *
     * @Description：异步数据请求失败时回调
     * <p>创建人：LZP ,  2013-9-3  下午9:44:21</p>
     * <p>修改人：LZP ,  2013-9-3  下午9:44:21</p>
     *
     * @param e
     * void
     */
    void onDataFail(Exception e);

    /**
     *
     * @Description：异步数据结束时回调
     * <p>创建人：LZP ,  2013-9-3  下午9:44:43</p>
     * <p>修改人：LZP ,  2013-9-3  下午9:44:43</p>
     *
     * void
     */
    void onDataFinish();

}