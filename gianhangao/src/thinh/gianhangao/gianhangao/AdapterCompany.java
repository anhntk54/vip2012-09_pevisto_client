package thinh.gianhangao.gianhangao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
 
public class AdapterCompany extends BaseAdapter {
	private Context context;
	private final String[] mobileValues;
 
	public AdapterCompany(Context context, String[] mobileValues) {
		this.context = context;
		this.mobileValues = mobileValues;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
 
			gridView = new View(context);
 
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.item_company, null);
 
			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.name_company);
			textView.setText(mobileValues[position]);
 
			// set image based on selected text
			ImageView imageView = (ImageView) gridView .findViewById(R.id.img_company);
 
			String mobile = mobileValues[position];
 
			if (mobile.equals("Windows")) {
				imageView.setImageResource(R.drawable.bgregister);
			} else if (mobile.equals("iOS")) {
				imageView.setImageResource(R.drawable.deadoralive);
			} else if (mobile.equals("Blackberry")) {
				imageView.setImageResource(R.drawable.cart);
			} else {
				imageView.setImageResource(R.drawable.logo);
			}
 
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
 
	@Override
	public int getCount() {
		return mobileValues.length;
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
}