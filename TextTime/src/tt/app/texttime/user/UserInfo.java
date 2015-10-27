package tt.app.texttime.user;

import android.content.Context;
import tt.app.texttime.database.DBAdapter;

public class UserInfo {

	
	static String userid 
		, mobile_number 
		, dial_code
		, status
		, countryid;
	
	public UserInfo(String mobile_number,String dial_code,String cid)
	{
		UserInfo.mobile_number = mobile_number;
		UserInfo.dial_code = dial_code;
		UserInfo.countryid = cid;
	}
	
	public UserInfo()
	{
		
	}
	public void setMobileNumber(String mobile)
	{
		UserInfo.mobile_number = mobile;
	}
	public String getMobileNumber()
	{
		if(!UserInfo.mobile_number.equals(""))
			return UserInfo.mobile_number;
		return null;
	}
	public void setDialCode(String ccode)
	{
		UserInfo.dial_code = ccode;
	}
	public String getDialCode()
	{
		if(!UserInfo.dial_code.equals(""))
			return UserInfo.dial_code;
		return null;
					
	}
	public void setCountryID(String cid)
	{
		UserInfo.countryid = cid;
	}
	public String getCountryID()
	{
		if(!UserInfo.countryid.equals(""))
			return UserInfo.countryid;
		return null;
	}
	public void setUserStatus(String status)
	{
		UserInfo.status = status;
	}
	public String getUserStatus()
	{
		if(!UserInfo.status.equals(""))
			return UserInfo.status;
		return null;
	}
	public void setUserID(String userid)
	{
		UserInfo.userid = userid;
	}
	public String getUserID()
	{
		if(!UserInfo.userid.equals(""))
			return UserInfo.userid;
		return null;
	}
	
	public boolean insertUser(Context context)
	{
		DBAdapter  db = new DBAdapter(context);
		db.open();
		long userid = db.insertUserDetail(UserInfo.countryid, UserInfo.dial_code, UserInfo.mobile_number, null,null, null, null, UserInfo.status);
		db.close();
		this.setUserID(String.valueOf(userid));
		if(UserInfo.userid.equals(""))
			return false;
		return true;
	}
	public void updateStatus(Context context)
	{
		DBAdapter  db = new DBAdapter(context);
		db.open();
		db.updateUserStatus(UserInfo.userid, UserInfo.status);
		db.close();
	}
	public boolean updateUserMobileNumber(Context context)
	{
		DBAdapter  db = new DBAdapter(context);
		db.open();
		new UserProfile();
		db.updateUserMobileNumber(UserProfile.access_token, UserInfo.mobile_number);
		db.close();
		return true;
	}
	public boolean updateUserCountry(Context context)
	{
		DBAdapter  db = new DBAdapter(context);
		db.open();
		new UserProfile();
		db.updateUserCountry(UserProfile.access_token, UserInfo.countryid, UserInfo.dial_code);
		db.close();
		return true;
	}
}
