package com.mmbao.saas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


import com.mmbao.saas.utils.LogcatUtil;

import java.util.ArrayList;

/**
 * IM Users DbHelper
 */
public class IMUserDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "im_user.db";
	private static final int DATABASE_VERSION = 2;

	public IMUserDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + IMUserTableContains.USER_TABLE_NAME + " (" 
				+ IMUserTableContains._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ IMUserTableContains.USER + " TEXT NOT NULL,"
				+ IMUserTableContains.PASSWORD + " TEXT NOT NULL," 
				+ IMUserTableContains.TYPE+ " INTEGER NOT NULL);" );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch (oldVersion) {
		case 1:
			break;
		default:
			LogcatUtil.e("数据库升级");
			db.execSQL("DROP TABLE IF EXISTS " + IMUserTableContains.USER_TABLE_NAME);
			onCreate(db);
		}
	}



	/**
	 * IMUserTable Contains
	 */
	public static final class IMUserTableContains implements BaseColumns {

		private IMUserTableContains() {

		}
		//user table constains
		public static final String USER_TABLE_NAME = "im_user_table";
		public static final String USER = "user";
		public static final String PASSWORD = "password";
		public static final String TYPE= "type"; //用户类型  1 App账号注册用户, 0 临时用户

		// user type
		public static final int TMP_USER = 0;
		public static final int REG_USER = 1;

		/**
		 * 返回常量  Name List
		 */
		public static ArrayList<String> getRequiredUserTableColumns() {
			ArrayList<String> tmpList = new ArrayList<String>();
			tmpList.add(USER);
			tmpList.add(PASSWORD);
			tmpList.add(TYPE);
			return tmpList;
		}	
	}
	
	public static final class IMUserTableEntity {
		private String mUser;
		private String mPassword;
		private int mType;

		public IMUserTableEntity() {
			mUser = null;
			mPassword = null;
		}
		
		public IMUserTableEntity(String user, String password, int type) {
			mUser = user;
			mPassword = password;
			mType = type;
		}
		
		public String getUser() {
			return mUser;
		}
		public String getPassword() {
			return mPassword;
		}
		public int getType(){
			return mType;
		}
		public void setUser(String user) {
			mUser = user;
		}
		public void setPassword(String password) {
			mPassword = password;
		}
		public void setType(int type) {
			mType = type;
		}
		
		public boolean isEmpty() {
			return (mUser == null || mUser.isEmpty() || mPassword == null || mPassword.isEmpty()); 
		}
		
		
	}
}