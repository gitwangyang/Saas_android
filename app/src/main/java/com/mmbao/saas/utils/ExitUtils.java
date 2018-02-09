package com.mmbao.saas.utils;

import android.app.Activity;

import java.util.LinkedList;

/**
 * 完全退出Activity工具类
 * @author 陈佳希
 * @date 2013-10-10
 * 
 */
public class ExitUtils {
	public LinkedList<Activity> activityList = new LinkedList<Activity>();
	private static ExitUtils instance;
	
	public ExitUtils() {
	}
	/**
	 * 单例模式
	 * 
	 * @return 唯一退出应用对象
	 */
	public static ExitUtils getInstance() {
		if (null == instance) {
			synchronized (ExitUtils.class) {
				if (null == instance) {
					instance = new ExitUtils();
				}
			}
		}
		return instance;
	}
	/**
	 * 添加所有进入过的Activity
	 * 
	 * @param activity
	 *            当前的Activity
	 */
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}
	/**
	 * 退出应该Finish掉所有进入过的的Activity
	 */
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		activityList.clear();
		System.exit(0);
	}
	
	/**
	 * 把所有的Activity都Finish掉
	 */
	public void finishAllActivity() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		activityList.clear();
	}
}
