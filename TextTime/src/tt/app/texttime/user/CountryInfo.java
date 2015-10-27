package tt.app.texttime.user;

import tt.app.texttime.database.DBAdapter;
import android.annotation.SuppressLint;
import android.content.Context;

public class CountryInfo {

	static String countryid
		, dial_code
		, countryname
		, ISO_Alpha_2
		, ISO_Alpha_3;
	
	public CountryInfo(String dialcode, String countryname,String ISOAlpha2, String ISOAlpha3)
	{
		CountryInfo.dial_code = dialcode;
		CountryInfo.countryname = countryname;
		CountryInfo.ISO_Alpha_2 = ISOAlpha2;
		CountryInfo.ISO_Alpha_3 = ISOAlpha3;
	}
	public CountryInfo()
	{
		
	}
	public void setCountryID(String cid)
	{
		CountryInfo.countryid  = cid;
	}
	@SuppressLint("NewApi")
	public String getCountryID()
	{
		//if(!this.countryid.equalsIgnoreCase(""))
			return CountryInfo.countryid;
		//return null;
	}
	
	public String getDialCode()
	{
		//if(!this.dial_code.equalsIgnoreCase(""))
			return CountryInfo.dial_code;
		//return "";
	}
	public String getCountryName()
	{
		//if(!this.countryname.equalsIgnoreCase(""))
			return CountryInfo.countryname;
		//return "";
	}
	public String getISOAlpha2()
	{
		//if(!this.ISO_Alpha_2.equalsIgnoreCase(""))
			return CountryInfo.ISO_Alpha_2;
		//return "";
	}
	public String getISOAlpha3()
	{
		//if(!this.ISO_Alpha_3.equals(null))
			return CountryInfo.ISO_Alpha_3;
		//return "";
	}
	public boolean insertCountryInfo(Context context)
	{
		DBAdapter db = new DBAdapter(context);
		db.open();
		long cid = db.insertCountryDetail(CountryInfo.dial_code, CountryInfo.countryname, CountryInfo.ISO_Alpha_2, CountryInfo.ISO_Alpha_3);
		db.close();
		this.setCountryID(String.valueOf(cid));
		if(CountryInfo.countryid.equals(""))
			return false;
		return true;
	}
}
