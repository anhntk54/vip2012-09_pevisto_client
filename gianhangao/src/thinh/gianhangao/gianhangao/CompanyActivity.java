package thinh.gianhangao.gianhangao;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class CompanyActivity extends Activity {

	GridView gridView;
	TextView setting;

	static final String[] MOBILE_OS = new String[] { "Android", "iOS", "Windows", "Blackberry" };

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_company);
		
		setting = (TextView) findViewById(R.id.setting);
		gridView = (GridView) findViewById(R.id.gridViewCompany);

		gridView.setAdapter(new AdapterCompany(this, MOBILE_OS));
		setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),EditProfileActivity.class));
				
			}
		});
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				
				Toast.makeText(getApplicationContext(),
						((TextView) v.findViewById(R.id.tv_company)).getText(),
						Toast.LENGTH_SHORT).show();
			}

			
		});

	}

}