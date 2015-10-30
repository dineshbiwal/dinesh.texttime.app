package tt.app.texttime;

import tt.app.texttime.user.UserProfile;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		TextView user = (TextView) findViewById(R.id.welcome_user);
		user.setText(new UserProfile().getProfileName());
	}

}
