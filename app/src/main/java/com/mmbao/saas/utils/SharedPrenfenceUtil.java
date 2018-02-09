package com.mmbao.saas.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mmbao.saas.App;


@SuppressLint("CommitPrefEdits")
public class SharedPrenfenceUtil {

	private static SharedPrenfenceUtil instance;
    private Editor editor;
    private SharedPreferences sf;
	public SharedPrenfenceUtil() {
		
      sf = App.app.getSharedPreferences(Constant.sharedPreferencesname, Context.MODE_PRIVATE);
      editor = sf.edit();
	}
	
	/**
	 * 单例模式
	 * 
	 * @return 唯一退出应用对象
	 */
	public static SharedPrenfenceUtil getInstance() {
		if (null == instance) {
			synchronized (SharedPrenfenceUtil.class) {
				if (null == instance) {
					instance = new SharedPrenfenceUtil();
				}
			}
		}
		return instance;
	}
	

	
	public String getStringValue(String key, String defValue){
        return sf.getString(key, defValue);
    }
    
    public int getIntValue(String key, int defValue){
        return sf.getInt(key, defValue);
    }
    
    public long getLongValue(String key, long defValue){
        return sf.getLong(key, defValue);
    }
    
    public boolean getBleanValue(String key, boolean defValue){
        return sf.getBoolean(key, defValue);
    }
    
    public float getFloatValue(String key, float defValue){
        return sf.getFloat(key, defValue);
    }
    
    
    public void putLongValue(String key, long value){
        editor.putLong(key, value);
        commit();
    }
    
    public void putStringValue(String key, String value){
        editor.putString(key, value);
        commit();
    }
    
    public void putIntValue(String key, int value){
        editor.putInt(key, value);
        commit();
    }
    
    public void putBooleanValue(String key, boolean value){
        editor.putBoolean(key, value);
        commit();
    }
    
    public void putFloatValue(String key, Float value){
        editor.putFloat(key, value);
        commit();
    }
    
    public void removeValue(String key){
        editor.remove(key);
        commit();
    }
    
    public void clearData(){
        editor.clear();
        commit();
    }
    
    public void commit(){
    	editor.commit();
    }
}
