package tt.app.texttime.connection;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import tt.app.texttime.user.Constant;
import tt.app.texttime.user.UserProfile;

public class Connection {

	InputStream is = null;
	UserProfile user = new UserProfile();
	
	 @SuppressWarnings("unused")
	private boolean isNetworkAvailable(Context context){
	    	boolean available = false;
	    	ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    	NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    	if(networkInfo !=null && networkInfo.isAvailable())
	    		available = true;
	    	return available;
	    }
	public InputStream getConnection(String url, String methods,ArrayList<NameValuePair> params)
    {
    	try{
    		if(methods=="POST"){
    			
	             HttpClient httpclient = new DefaultHttpClient();
	             HttpPost httppost = new HttpPost(Constant.CONNECTION_STRING+url);
	             httppost.setEntity(new UrlEncodedFormEntity(params));
	             HttpResponse response = httpclient.execute(httppost); 
	             HttpEntity entity = response.getEntity();
	             is = entity.getContent();
	             Log.e("log_tag", "connection success ");
   		 }
   		 else
   		 {
   			 DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(Constant.CONNECTION_STRING+url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                
                Log.e("log_tag", "connection success ");
   		 }
    	}
    	catch(Exception e)
         {
             Log.e("log_tag", "Error in http connection "+e.toString());
         }
    	return is;
    }
	public InputStream openConnection(String url, String methods, ArrayList<NameValuePair> params )
    {
    	 try
         {
    		 if(methods=="POST"){
	             HttpClient httpclient = new DefaultHttpClient();
	             HttpPost httppost = new HttpPost(Constant.CONNECTION_STRING+url);
			    //httppost.addHeader("PHP_AUTH_USER", user.getAccessToken());
	             httppost.setEntity(new UrlEncodedFormEntity(params));
	             HttpResponse response = httpclient.execute(httppost); 
	             HttpEntity entity = response.getEntity();
	             is = entity.getContent();
	
	             Log.e("log_tag", "connection success ");
    		 }
    		 else
    		 {
    			 DefaultHttpClient httpClient = new DefaultHttpClient();
                 String paramString = URLEncodedUtils.format(params, "utf-8");
                 url += "?" + paramString;
                 HttpGet httpGet = new HttpGet(Constant.CONNECTION_STRING+url);
                 httpGet.addHeader("PHP_AUTH_USER",user.getAccessToken());
                 HttpResponse httpResponse = httpClient.execute(httpGet);
                 HttpEntity httpEntity = httpResponse.getEntity();
                 is = httpEntity.getContent();
                 
                 Log.e("log_tag", "connection success ");
    		 }
         }
     catch(Exception e)
         {
             Log.e("log_tag", "Error in http connection "+e.toString());
         }
    	 return is;
    }
	public InputStream openConnection(String url, String methods)
	{
		 try
         {
    		 if(methods=="POST"){
	             HttpClient httpclient = new DefaultHttpClient();
	             HttpPost httppost = new HttpPost(Constant.CONNECTION_STRING+url);
			     HttpResponse response = httpclient.execute(httppost); 
	             HttpEntity entity = response.getEntity();
	             is = entity.getContent();
	
	             Log.e("log_tag", "connection success ");
    		 }
    		 else
    		 {
    			 DefaultHttpClient httpClient = new DefaultHttpClient();
                 HttpGet httpGet = new HttpGet(Constant.CONNECTION_STRING+url);
                 httpGet.addHeader("PHP_AUTH_USER",user.getAccessToken());
                 HttpResponse httpResponse = httpClient.execute(httpGet);
                 HttpEntity httpEntity = httpResponse.getEntity();
                 is = httpEntity.getContent();
                 
                 Log.e("log_tag", "connection success ");
    		 }
         }
     catch(Exception e)
         {
             Log.e("log_tag", "Error in http connection "+e.toString());
         }
    	 return is;
	}
}
