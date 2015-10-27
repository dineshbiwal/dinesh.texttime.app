package tt.app.texttime;

import tt.app.texttime.user.CountryInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CountryList extends Activity implements OnItemClickListener {

	ListView countrylist ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.countrylist);
		new LoadCountryName().execute();
		countrylist = (ListView) findViewById(R.id.list_country);
		countrylist.setOnItemClickListener(this);
	}
	
	public class LoadCountryName extends AsyncTask<Void, Void, Void> {

		CountryAdapter ca = new CountryAdapter(getBaseContext());
		@Override
		protected void onPreExecute() {
			
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			countrylist.setAdapter(ca);
			return null;
		}
		protected void onPostExecute() {
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		TextView cname = (TextView) view.findViewById(R.id.country_name);
		TextView dcode = (TextView) view.findViewById(R.id.dial_code);
		TextView isoalpha2 = (TextView) view.findViewById(R.id.isoalpha2);
		TextView isoalpha3 = (TextView) view.findViewById(R.id.isoalpha3);
			CountryInfo ci = new CountryInfo(dcode.getText().toString()
								, cname.getText().toString()
								, isoalpha2.getText().toString()
								, isoalpha3.getText().toString());
			if(ci.insertCountryInfo(this))
				{
				
				Log.d("CountryInfo",ci.getCountryID()+"  "+ci.getDialCode()+"  "+ ci.getCountryName()+"  "+ci.getISOAlpha2()+"  "+ci.getISOAlpha3());
					Intent intent = new Intent(this, MainActivity.class);
					startActivity(intent);
				}
		
		/*intent.putExtra("countryname",cname.getText().toString());
		intent.putExtra("dialcode", dcode.getText().toString());
		intent.putExtra("isoalpha2", isoalpha2.getText().toString());
		intent.putExtra("isoalpha3", isoalpha3.getText().toString());*/
		
		// TODO Auto-generated method stub
		
	}
}
