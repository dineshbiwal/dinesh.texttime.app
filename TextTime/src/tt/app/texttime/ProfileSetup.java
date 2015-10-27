package tt.app.texttime;

import tt.app.texttime.parsing.DataFunctions;
import tt.app.texttime.user.UserInfo;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileSetup extends Activity implements OnClickListener{

	ActionBar actionBar;
	ImageView back; 
	TextView done;
	EditText username;
	String imagename;
	Intent intx;
	UserInfo user = new UserInfo();
	
	@SuppressLint({ "InflateParams", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_profile);
		intx = getIntent();
		actionBar =  getActionBar(); 
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
	    R.layout.actionbar_signin,null);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowHomeEnabled(false);
		username = (EditText) findViewById(R.id.profilename);
		back = (ImageView) actionBarLayout.findViewById(R.id.back_arrow);
		done = (TextView) actionBarLayout.findViewById(R.id.next);
		done.setText(R.string.done);
		
		back.setOnClickListener(this);
		done.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == back )
		{
			finish();
		}
		if(v == done)
		{
			if(new DataFunctions().isUsernameValid(username.getText().toString())){
				Intent intent = new Intent(this, ProfilePasswordSetup.class);
				//intent.putExtra("imagename", imagename);
				intent.putExtra("mobilenumber", intx.getExtras().getString("mobilenumber"));
				intent.putExtra("status", "false");
				intent.putExtra("username", username.getText().toString());
				startActivity(intent);
			}
			else 
			{
				new AlertDialogManager().showAlertDialog(this,getResources().getString(R.string.user), getResources().getString(R.string.input_user_err), false);
			}
		}
	}
}