package tt.app.texttime; 

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import tt.app.texttime.parsing.DataParsing;
import tt.app.texttime.user.UserInfo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class SigninActivity extends Activity implements OnClickListener{

	TextView countrycode, phonenumber,next;
	EditText verifycode;
	Button resendcode, callme;
	ActionBar actionBar;
	ImageView back;
	CounterClass timer;
	String code;
	AlertDialogManager alert = new AlertDialogManager();
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		actionBar =  getActionBar(); 
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
	    R.layout.actionbar_signin,null);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowHomeEnabled(false);
		countrycode = (TextView) findViewById(R.id.country_code);
		phonenumber = (TextView) findViewById(R.id.phone_number);
		verifycode = (EditText) findViewById(R.id.enter_code);
		resendcode = (Button) findViewById(R.id.resend_code);
		callme = (Button) findViewById(R.id.callme);
		back = (ImageView) actionBarLayout.findViewById(R.id.back_arrow);
		next = (TextView) actionBarLayout.findViewById(R.id.next);
		
		Intent intent = getIntent();
		countrycode.setText(intent.getExtras().getString("countrycode"));
		phonenumber.setText(intent.getExtras().getString("phonenumber"));
		verifycode.setText(intent.getExtras().getString("verifycode"));
		code = intent.getExtras().getString("verifycode");
		timer = new CounterClass(180000,1000);  	// 3 Minute timer is apply for entering the verification code in editbox 
		timer.start();		// Timer is start if the user no action in this time interval then automatic generate a call to the user
		resendcode.setOnClickListener(this);
		callme.setOnClickListener(this);
		back.setOnClickListener(this);
		next.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if( v == resendcode)
		{
			//new SendVerificationCode().execute();
		}
		if( v == callme)
		{ 
			//new SendVerificationCode().execute();
		}
		if( v == back)
		{
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
		}
		if( v == next)
		{
			if(code.equals(verifycode.getText().toString().trim()))
					{
							UserInfo user = new UserInfo();
							user.setUserStatus("active");
							user.updateStatus(getBaseContext());
							new CheckVerification().execute();
					}
			else{
					alert.showAlertDialog(this, getResources().getString(R.string.verification),getResources().getString(R.string.verification_error), false);
			}
		}
	}
	@SuppressLint("DefaultLocale")
	public class CounterClass extends CountDownTimer {

		public CounterClass(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
	        public void onFinish() {
	           //Call to  the user or resend the verifiation code in the 3 minute
				//new SendVerificationCode().execute();
	        }

		@SuppressLint("NewApi")
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@Override
		public void onTick(long millisUntilFinished) {
			
			  long millis = millisUntilFinished;
			    String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
			            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
			            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
			  callme.setText("Call Me ("+hms+")");
			    
		}
	}
	
	class CheckVerification extends AsyncTask<Void, Void, Void>
	{
		DataParsing parsing = new DataParsing();
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		String res;
		@Override
		protected void onPreExecute() {
			params.add(new BasicNameValuePair("mobileNumber",phonenumber.getText().toString()));
			params.add(new BasicNameValuePair("verifyCode",verifycode.getText().toString()));
		}
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			res = parsing.parseData("registers/verify", "POST", params);
			return null;
		}
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Void params)
		{
			try{
				final JSONObject json = new JSONObject(res);
				if(json.getString("message").equals("verified success"))
				{
					AlertDialog alertDialog = new AlertDialog.Builder(SigninActivity.this).create();
					alertDialog.setTitle(getResources().getString(R.string.verification));
					alertDialog.setMessage(getResources().getString(R.string.verification_success));
					alertDialog.setIcon(R.drawable.success);
					alertDialog.setButton(getResources().getString(R.string.okey), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							try{
								timer.cancel();
								JSONObject data = json.getJSONObject("result");
								Intent intent = new Intent(SigninActivity.this, ProfileSetup.class);
								intent.putExtra("mobilenumber", data.getString("mobileNumber"));
								startActivity(intent);
							}catch(JSONException e)
							{
								alert.showAlertDialog(SigninActivity.this, getResources().getString(R.string.parsing), getResources().getString(R.string.parsing_error), false);
							}
						}
					});
					alertDialog.show();
					
				}
				else{
					alert.showAlertDialog(SigninActivity.this, getResources().getString(R.string.connection), getResources().getString(R.string.connection_error), false);
				}
			}
			catch(JSONException e)
			{
				alert.showAlertDialog(SigninActivity.this,  getResources().getString(R.string.connection), getResources().getString(R.string.connection_error), false);
			}

		}
		
	}
}
