package tt.app.texttime.parsing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

import tt.app.texttime.connection.Connection;
import tt.app.texttime.user.UserProfile;

public class DataParsing {
	
	InputStream is = null;
	Connection con = new Connection();
	String json = null;
	UserProfile user = new UserProfile();
	
	@SuppressLint("NewApi")
	public String parseData(String url, String methods,ArrayList<NameValuePair> params)
	{
		if(user.getAccessToken()==null)
			is = con.getConnection(url, methods, params);
		else 
			is = con.openConnection(url, methods, params);
		try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) 
            {
                    sb.append(line + "\n");
            }
            is.close();
            reader.close();
            json=sb.toString();
          
        }
        catch(Exception e)
        {
           Log.e("log_tag", "Error converting result "+e.toString());
        }
  	  return json;
	}
	public String checkStatusCode(String result)
    {
    	try{
    		JSONObject jObject=new JSONObject(result);
        int ch = Integer.parseInt(jObject.getString("statusCode"));
        if(ch>=200 && ch<300)
            return "success";
        else if(ch>=400 && ch<500)
        	return "request_err";
        else if(ch>=500)
        	return "server_err";
        else if(jObject.getString("err").equalsIgnoreCase("true"))
        	return "api_err";
    	}
    	catch(JSONException e)
         {
            Log.e("log_tag", "Error parsing data "+e.toString());
         }
        return "unknow_err";
    }
	public String getResult(String res)
	{
		try{
			JSONObject jobj = new JSONObject(res);
			return jobj.getString("r");
		}catch(JSONException e)
		{
			Log.e("log_tag","Error parsing data "+e.toString());
		}
		return "parsing_error";
	}
}
