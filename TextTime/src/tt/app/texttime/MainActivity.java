package tt.app.texttime;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import tt.app.texttime.parsing.DataFunctions;
import tt.app.texttime.parsing.DataParsing;
import tt.app.texttime.user.CountryInfo;
import tt.app.texttime.user.UserInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("NewApi") public class MainActivity extends Activity implements OnClickListener{

	TextView countrycode ;
	EditText phonenumber;
	Button verifycode;
	Intent intent;
	String cid,username,status;
	AlertDialogManager alert = new AlertDialogManager();
	CountryInfo ci = new CountryInfo();
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String countrydata[] = new CountryInfo().getCountry(getBaseContext());
		//Log.d("countrydata.length", String.valueOf(countrydata.length));
		if(countrydata != null)
		{
			new DataFunctions().setCountry(countrydata);
		}
		String userdata[] = new UserInfo().getUserDetail(getBaseContext());
		//Log.d("userdata.length", String.valueOf(userdata.length));
		if(userdata != null){
			if(userdata[8].equalsIgnoreCase("active"))
			{
				new DataFunctions().setUser(userdata);
				Intent intx = new Intent(this,Welcome.class);
				startActivity(intx);
			}
		}
		countrycode = (TextView) findViewById(R.id.your_country);
		phonenumber = (EditText) findViewById(R.id.phonenumber);
		verifycode = (Button) findViewById(R.id.send_verify_code);
		StrictMode.setThreadPolicy(policy);
		countrycode.setOnClickListener(this);
		verifycode.setOnClickListener(this);
		if(ci.getCountryID()!=null)
		{
			cid = ci.getCountryID();
			countrycode.setText(ci.getCountryName() + " " + ci.getDialCode());
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == countrycode)
		{
			Intent i = new Intent(this,CountryList.class);
			startActivity(i);
		}
		if(v == verifycode)
		{
			if(ci.getDialCode() == null)
			{
				alert.showAlertDialog(this, getResources().getString(R.string.country), getResources().getString(R.string.country_err), false);
				return;
			}
			if(new DataFunctions().isPhoneNumberValid(phonenumber.getText().toString())){
				UserInfo user = new UserInfo(phonenumber.getText().toString(), ci.getDialCode(),ci.getCountryID());
				user.insertUser(this);
				new SendVerificationCode().execute();
			}
			else
			{
				alert.showAlertDialog(this, getResources().getString(R.string.phonenumber), getResources().getString(R.string.phonenumber_error), false);
			}
			
		}
	}
	private class SendVerificationCode extends AsyncTask<Void, Void, Void>
	{
		DataParsing parsing = new DataParsing();
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		String res;
		@Override
		protected void onPreExecute() {
			params.add(new BasicNameValuePair("dialCode",ci.getDialCode()));
			params.add(new BasicNameValuePair("mobileNumber",phonenumber.getText().toString()));
		}
		@Override
		protected Void doInBackground(Void... arg0) {
			res = parsing.parseData("registers", "POST", params);
			return null;
		}
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Void params)
		{
			try{
				final JSONObject json = new JSONObject(res);
				JSONObject result = null;
				if(json.getString("statusCode").equalsIgnoreCase("409"))
				{
					result = json.getJSONObject("message");
					username = json.getString("username");
					status = json.getString("isActive");
				}
				else if(json.getString("statusCode").equalsIgnoreCase("201")){
					result = json.getJSONObject("result");
				}
				else if(json.getString("statusCode").equalsIgnoreCase("400"))
				{
					alert.showAlertDialog(MainActivity.this, getResources().getString(R.string.country), getResources().getString(R.string.country_err), false);
					return;
				}
				if(json.getString("message").equals("New user created successfully!"))
				{
					JSONObject verifyCode =  result.getJSONObject("verifyCode");
					if(verifyCode.getString("verified").equalsIgnoreCase("false"))
					{
						Intent intx = new Intent(MainActivity.this, SigninActivity.class);
						intx.putExtra("countrycode", ci.getDialCode());
						intx.putExtra("phonenumber", phonenumber.getText().toString());
						intx.putExtra("verifycode", verifyCode.getString("verifyCode"));
						startActivity(intx);
					}
				}
				else if(result.getString("msg").equalsIgnoreCase("User already exist!") && status.equalsIgnoreCase("true")){
					AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
					alertDialog.setTitle(getResources().getString(R.string.user));
					alertDialog.setMessage(getResources().getString(R.string.user_error));
					alertDialog.setIcon(R.drawable.fail);
					alertDialog.setButton(getResources().getString(R.string.okey), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent(MainActivity.this, ProfilePasswordSetup.class);
								intent.putExtra("mobilenumber", phonenumber.getText().toString());
								intent.putExtra("username", username);
								intent.putExtra("status", status);
								startActivity(intent);
						}
					});
					alertDialog.show();
				}
				else {
					alert.showAlertDialog(MainActivity.this,  getResources().getString(R.string.user), getResources().getString(R.string.status_error), false);
				}
			}
			catch(JSONException e)
			{
				alert.showAlertDialog(MainActivity.this,  getResources().getString(R.string.connection), getResources().getString(R.string.connection_error), false);
			}
		}
	}
}

