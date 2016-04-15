package com.mome.db;

import java.util.ArrayList;
import java.util.List;

import com.mome.main.business.model.UserProperty;
import com.mome.main.core.utils.DateUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewFriendDbHelper {
	private static NewFriendDbHelper instance = null;
	
	private SqlLiteHelper helper;
	private SQLiteDatabase db;  // �����ҵ�
	private Context context;
	public NewFriendDbHelper(Context context) {
		this.context=context;
		helper = new SqlLiteHelper(context);
		db = helper.getWritableDatabase();
	}

	public void closeDb(){
		db.close();
		helper.close();
	}
	public static NewFriendDbHelper getInstance(Context context) {
		if (instance == null) {
			instance = new NewFriendDbHelper(context);
		}
		return instance;
	}
	
	private class SqlLiteHelper extends SQLiteOpenHelper {

		private static final int DB_VERSION = 1;
		private static final String DB_NAME = "newFiend";

		public SqlLiteHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = "CREATE TABLE  IF NOT EXISTS " + DB_NAME
						+ "( id INTEGER PRIMARY KEY AUTOINCREMENT,"+
						"username text ,sendDate text,isDeal INTEGER," +
						"whos text,i_filed INTEGER,t_field text)";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			dropTable(db);
			onCreate(db);
		}

		private void dropTable(SQLiteDatabase db) {
			String sql = "DROP TABLE IF EXISTS "+DB_NAME;
			db.execSQL(sql);
		}

	}

	public void saveNewFriend(String username){
		int nowCount = getCount(username);
		ContentValues values = new ContentValues();
		if (nowCount == 0) {
			values.put("username", username);
			values.put("sendDate",DateUtil.now_MM_dd_HH_mm_ss());
			values.put("whos", UserProperty.getInstance().getUid());
			db.insert(helper.DB_NAME, "id", values);
		}
		else{
			values.put("sendDate", DateUtil.now_MM_dd_HH_mm_ss());
			values.put("isDeal", 0);
			db.update(helper.DB_NAME, values, " username=? and whos=?", 
					new String[]{username,UserProperty.getInstance().getUid()});
		}
		NewMsgDbHelper.getInstance(context).saveNewMsg(""+0);
	}
	
	public void delFriend(String username){
		ContentValues values = new ContentValues();
		values.put("isDeal", 1);
		db.update(helper.DB_NAME, values, " username=? and whos=?", 
				new String[]{username,UserProperty.getInstance().getUid()});
	}

	/**
	 * ȡ�����ҵ�
	 */
	public List<String> getNewFriend(){
		List<String> friends = new ArrayList<String>();
		String sql = "select username from " +helper.DB_NAME +
				" where whos = ? order by sendDate desc";
		Cursor cursor = db.rawQuery(sql, new String[]{UserProperty.getInstance().getUid()});
		while(cursor.moveToNext()){
			friends.add(cursor.getString(0));
		}
		cursor.close();
		return friends;
	}
	
	//ĳ����
	public int getCount(String username){   
		int count = 0 ;
		String sql ="select count(0) from "+helper.DB_NAME+" where username=? and whos=?";
		Cursor cursor = db.rawQuery(sql, new String[]{username,UserProperty.getInstance().getUid()});
		while(cursor.moveToNext()){
			count = cursor.getInt(0);
		}
		cursor.close();
		return count;
	}
	
	//ĳ�����Ƿ��Ѵ���
	public boolean isDeal(String username){   
		boolean isDeal = false ;
		String sql ="select isDeal from "+helper.DB_NAME+" where username=? and whos=?";
		Cursor cursor = db.rawQuery(sql, new String[]{username,UserProperty.getInstance().getUid()});
		while(cursor.moveToNext()){
			if (cursor.getInt(0) == 0) {
				isDeal = false;
			}
			else {
				isDeal = true;
			}
		}
		cursor.close();
		return isDeal;
	}
	
	
	public void clear(){
		db.delete(helper.DB_NAME, "id>?", new String[]{"0"}); 
	}
}
