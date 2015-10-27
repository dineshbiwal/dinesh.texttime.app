package tt.app.texttime;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import tt.app.texttime.parsing.DataParsing;
import tt.app.texttime.user.UserInfo;
import tt.app.texttime.user.UserProfile;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfilePasswordSetup extends Activity implements OnClickListener{

	ActionBar actionBar;
	ImageView back,profileimage; 
	TextView done,welcome,no_user,username;
	String imagename;
	EditText password;
	UserInfo user = new UserInfo();
	
	@SuppressLint({ "InflateParams", "NewApi" })
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_setting);
		
		actionBar =  getActionBar(); 
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
	    R.layout.actionbar_signin,null);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowHomeEnabled(false);
		imagename = getIntent().getExtras().getString("imagename");
		back = (ImageView) actionBarLayout.findViewById(R.id.back_arrow);
		done = (TextView) actionBarLayout.findViewById(R.id.next);
		done.setText(R.string.done);
		Intent i = getIntent();
		welcome = (TextView) findViewById(R.id.welcome);
		no_user = (TextView) findViewById(R.id.no_user);
		username = (TextView) findViewById(R.id.username);
		profileimage = (ImageView) findViewById(R.id.profileimage);
		password = (EditText) findViewById(R.id.password);
		username.setText("@ "+ i.getExtras().getString("username"));
		no_user.setText(getResources().getString(R.string.not)+" "+i.getExtras().getString("username")+"?");
		back.setOnClickListener(this);
		done.setOnClickListener(this);
		if(i.getExtras().getString("status").equalsIgnoreCase("true"))
		{
			welcome.setText(getResources().getString(R.string.welcome_back)+" "+i.getExtras().getString("username").toString()+"!");
		}
		else
		{
			welcome.setText(getResources().getString(R.string.wel_come)+" "+i.getExtras().getString("username")+"!");
		}
		//profileimage.setImageURI(Constant.LOCAL_IMAGE_URL + imagename));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==done)
		{
			if(password.getText().toString().length()>=6)
				new SetProfile().execute();
			else 
				new AlertDialogManager().showAlertDialog(this, getResources().getString(R.string.password),getResources().getString(R.string.password_error), false);
		}
		if(v == back)
		{
			finish();
		}
	}
	ProgressDialog mProgressDialog;
	private class SetProfile extends AsyncTask<Void, Void, Void> {
		DataParsing parsing = new DataParsing();
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		String res;
		UserProfile profile;
		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(1111);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			params.add(new BasicNameValuePair("mobileNumber",user.getMobileNumber()));
			params.add(new BasicNameValuePair("username",getIntent().getExtras().getString("username")));
			params.add(new BasicNameValuePair("password", password.getText().toString()));
			res = parsing.parseData("registers/complete", "POST", params);
			return null;
		}
		@Override
		protected void onPostExecute(Void params) {
			mProgressDialog.dismiss();
			try{
				JSONObject json= new JSONObject(res);
				if(json.getString("statusCode").equalsIgnoreCase("200")){
					if(json.getString("message").equals("Profile created!"))
					{
						JSONObject data = json.getJSONObject("result");
						profile = new UserProfile(data.getString("username"), null, data.getString("token"), data.getString("username"));
						if(profile.updateUser(getBaseContext())){
							Intent i = new Intent(ProfilePasswordSetup.this, Welcome.class);
							startActivity(i);
						}
					}
				}
				else
				{
					new AlertDialogManager().showAlertDialog(ProfilePasswordSetup.this, getResources().getString(R.string.profile), getResources().getString(R.string.username_error), false);
				}
			}catch(JSONException e)
			{
				new AlertDialogManager().showAlertDialog(ProfilePasswordSetup.this, getResources().getString(R.string.profile), getResources().getString(R.string.profile_error), false);
			}
		}
	}
	 @Override
		protected Dialog onCreateDialog(int id) {
			switch (id) {
			case 1111:
				mProgressDialog = new ProgressDialog(this);
				mProgressDialog.setMessage(getResources().getString(R.string.create_profile));
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				mProgressDialog.setCancelable(true);
				mProgressDialog.show();
				return mProgressDialog;
			default:
				return null;
			}
		}

}
