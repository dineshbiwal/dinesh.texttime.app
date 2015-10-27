package tt.app.texttime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CountryAdapter extends BaseAdapter{

	String[][] countrydata = new String[][]{{"USA","+1","US","USA"},
											{"INDIA","+91","IN","IND"}};
	Context context;
	public CountryAdapter(Context c) {
		// TODO Auto-generated constructor stub
		this.context = c;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint({ "InflateParams", "ViewHolder" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.country_item, null);

		TextView cname = (TextView) convertView.findViewById(R.id.country_name);
		TextView dcode = (TextView) convertView.findViewById(R.id.dial_code);
		TextView isoalpha2 = (TextView) convertView.findViewById(R.id.isoalpha2);
		TextView isoalpha3 = (TextView) convertView.findViewById(R.id.isoalpha3);
		cname.setText(countrydata[position][0]);
		dcode.setText(countrydata[position][1]);
		isoalpha2.setText(countrydata[position][2]);
		isoalpha3.setText(countrydata[position][3]);
		return convertView;
	}

}
