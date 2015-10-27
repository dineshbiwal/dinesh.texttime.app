package tt.app.texttime.user;

import android.content.Context;
import tt.app.texttime.database.DBAdapter;

public class UserProfile {
	
	static String profilename 		/* Define th user profile name display on the profile */
		, profileImage			/* Profile Image path and name */
		, access_token 			/* Uniquely Indentify the user */
		, display_name;			/* Display name of the user */
	
	public UserProfile(String username, String imagename, String access_token, String display_name)
	{
		UserProfile.profileImage = imagename;
		UserProfile.profilename = username;
		UserProfile.access_token = access_token;
		UserProfile.display_name = display_name;
	}
	public UserProfile()
	{
	
	}
	public void setProfileName(String name)
	{
		UserProfile.profilename = name;
	}
	public String getProfileName()
	{
		//if(!UserProfile.profilename.equals(""))
			return UserProfile.profilename;
		//return null;
	}
	public void setProfileImage(String imagename)
	{
		UserProfile.profileImage = imagename;
	}
	public String  getProfileImagePath()
	{
		//if(!UserProfile.profileImage.equals(""))
			return Constant.SERVER_IMAGE_URL + UserProfile.profileImage;
		//return null;
	}
	public void setAccessToken(String access_token)
	{
		UserProfile.access_token = access_token;
	}
	public String getAccessToken()
	{
		//if(!UserProfile.access_token.equals(""))
			return UserProfile.access_token;
		//return null;
	}
	public void setDisplayName(String displayname)
	{
		UserProfile.display_name = displayname;
	}
	public String getDisplayName()
	{
		//if(!UserProfile.display_name.equals(""))
			return UserProfile.display_name;
		//return null;
	}
	public boolean updateUser(Context context)
	{
		DBAdapter  db = new DBAdapter(context);
		db.open();
		db.updateUser(new UserInfo().getUserID(), UserProfile.profilename, UserProfile.access_token, UserProfile.profileImage, UserProfile.display_name);
		db.close();
		return true;
	}
	public boolean updateUserProfileImage(Context context)
	{
		DBAdapter  db = new DBAdapter(context);
		db.open();
		db.updateUserProfileImage(UserProfile.access_token, UserProfile.profileImage);
		db.close();
		return true;
	}
	public boolean updateUserDisplayName(Context context)
	{
		DBAdapter  db = new DBAdapter(context);
		db.open();
		db.updateUserDisplayName(UserProfile.access_token, UserProfile.display_name);
		db.close();
		return true;
	}
}
