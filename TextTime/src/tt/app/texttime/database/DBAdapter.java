package tt.app.texttime.database;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
 
public class DBAdapter 
{
		static final String DATABASE_NAME = "texttime.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		static final String TAB_COUNTRY="COUNTRY";
		static final String TAB_USER = "USER";
		
		
		static final String  TABLE_COUNTRY = "create table "+TAB_COUNTRY+
		                             "( CID integer primary key autoincrement,DIAL_CODE text,COUNTRY_NAME text,ISO_ALPHA_2 text,ISO_ALPHA_3 text); ";
		
		static final String TABLE_USER = "create table "+TAB_USER+" ( USER_ID integer primary key autoincrement,CID text, DIAL_CODE text, MOBILE_NUMBER text, USER_NAME text, ACCESS_TOKEN text, USER_IMAGE text, DISPLAY_NAME text, STATUS text);"; 
		//+ "USERID text"
		public enum userdetail { USER_ID,DIAL_CODE,MOBILE_NUMBER,USER_NAME,ACCESS_TOKEN,USER_IMAGE,DISPLAY_NAME, STATUS};
		public  SQLiteDatabase db;
		
		@SuppressWarnings("unused")
		private final Context context;
		// Database open/upgrade helper
		private DataBaseHelper dbHelper;
		public  DBAdapter(Context context) 
		{
			this.context=context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		public  DBAdapter open() throws SQLException 
		{
			db = dbHelper.getWritableDatabase();
			return this;
		}
		public  SQLiteDatabase getDatabaseInstance()
		{
			return db;
		}
		public long insertCountryDetail(String dialcode,String countryname,String isoalpha2,String isoalpha3)
    	{
			Cursor cursor=db.query(TAB_COUNTRY, null, null, null,null , null, null);
		    if(cursor.getCount()>1) 
	        { db.delete(TAB_COUNTRY, null, null) ; }
			ContentValues newValues = new ContentValues();
			newValues.put("DIAL_CODE",dialcode);
			newValues.put("COUNTRY_NAME", countryname);
			newValues.put("ISO_ALPHA_2",isoalpha2);
			newValues.put("ISO_ALPHA_3",isoalpha3);
			return db.insert(TAB_COUNTRY, null, newValues);
			
		}
		public String[] getCountryDetail()
		{
			Cursor cursor=db.query(TAB_COUNTRY, null, null, null, null , null, null);
		    if(cursor.getCount()<1) 
	        {   
	        	cursor.close();
	        	return null;
	        }
		    String[] countryinfo = new String[cursor.getColumnCount()];
	        cursor.moveToFirst();
	        for(int i = 0; i<cursor.getColumnCount(); i++){
	        	countryinfo[i] = cursor.getString(i);
	        }
			cursor.close();
			return countryinfo;
		}
		public long insertUserDetail(String cid,String dialcode, String mobilenumber, String username, String accesstoken, String userimage, String displayname, String status)
		{
			ContentValues val = new ContentValues();
			val.put("CID", cid);
			val.put("DIAL_CODE", dialcode);
			val.put("MOBILE_NUMBER", mobilenumber);
			val.put("USER_NAME", username);
			val.put("ACCESS_TOKEN", accesstoken);
			val.put("USER_IMAGE", userimage);
			val.put("DISPLAY_NAME", displayname);
			val.put("STATUS", status);
			return db.insert(TAB_USER, null, val);
		}
		
		public void  updateUser(String userid,String username,String accesstoken,String userimage,String displayname)
		{   
			ContentValues updatedValues = new ContentValues();
			updatedValues.put("USER_NAME", username);
			updatedValues.put("ACCESS_TOKEN",accesstoken);
			updatedValues.put("USER_IMAGE", userimage);
			updatedValues.put("DISPLAY_NAME",displayname);
			
			String where="USER_ID = ?";
		    db.update(TAB_USER,updatedValues, where, new String[]{userid});			   
		} 
		public void updateUserCountry(String access_token, String cid, String dialcode)
		{
			ContentValues cv = new ContentValues();
			cv.put("CID",cid);
			cv.put("DAIL_CODE",dialcode);
			String where ="ACCESS_TOKEN = ?";
			db.update(TAB_USER, cv, where, new String[]{access_token});
		}
		public void updateUserDisplayName(String access_token,String displayname)
		{
			ContentValues cv = new ContentValues();
			cv.put("DISPLAY_NAME", displayname);
			String where ="ACCESS_TOKEN = ?";
			db.update(TAB_USER, cv, where, new String[]{access_token});
		}
		public  void updateUserProfileImage(String access_token,String profileimage)
		{
			ContentValues cv = new ContentValues();
			cv.put("USER_IMAGE", profileimage);
			String where ="ACCESS_TOKEN = ?";
			db.update(TAB_USER, cv, where, new String[]{access_token});
		}
		
		public void updateUserMobileNumber(String access_token,String mobilenumber)
		{
			ContentValues cv = new ContentValues();
			cv.put("MOBILE_NUMBER", mobilenumber);
			String where ="ACCESS_TOKEN = ?";
			db.update(TAB_USER, cv, where, new String[]{access_token});
		}
		public void updateUserStatus(String userid,String status)
		{
			ContentValues val = new ContentValues();
			val.put("Status", status);
			String where = "USER_ID = ?";
			db.update(TAB_USER, val, where, new String[]{userid});
		}
		public int deleteEntry(String userid)
		{
			
		    String where="USER_ID=?";
		    int numberOFEntriesDeleted= db.delete(TAB_USER, where, new String[]{userid}) ;
	   
	        return numberOFEntriesDeleted;
		}	
		public String[] getUser()
		{   
			Cursor cursor=db.query(TAB_USER, null, null, null,null , null, null);
		    if(cursor.getCount()<1) 
	        {   
	        	cursor.close();
	        	return null;
	        }
		    String[] userinfo = new String[cursor.getColumnCount()];
	        cursor.moveToFirst();
	        for(int i = 0; i<cursor.getColumnCount(); i++){
	        	userinfo[i] = cursor.getString(i);
	        }
			cursor.close();
			return userinfo;
		}
		public String[] getUserByAPIKey(String accesstoken)
		{   
			Cursor cursor=db.query(TAB_USER, null, " ACCESS_TOKEN = '"+accesstoken+"' ", null,null , null, null);
		    if(cursor.getCount()<1) 
	        {   
	        	cursor.close();
	        	return null;
	        }
		    String[] userinfo = new String[cursor.getColumnCount()];
	        cursor.moveToFirst();
			userinfo[0] = cursor.getString(cursor.getColumnIndex("USERID"));
			userinfo[1] = cursor.getString(cursor.getColumnIndex("CID"));
			userinfo[2] = cursor.getString(cursor.getColumnIndex("COUNTRY_CODE"));
			userinfo[3] = cursor.getString(cursor.getColumnIndex("MOBILE_NUMBER"));
			userinfo[4] = cursor.getString(cursor.getColumnIndex("USER_NAME"));
			userinfo[5] = cursor.getString(cursor.getColumnIndex("ACCESS_TOKEN"));
			userinfo[6] = cursor.getString(cursor.getColumnIndex("USER_IMAGE"));
			userinfo[7] = cursor.getString(cursor.getColumnIndex("DISPLAY_NAME"));
			userinfo[8] = cursor.getString(cursor.getColumnIndex("STATUS"));
			
			cursor.close();
			return userinfo;
		}
		public void close() 
		{
			db.close();
		}
}