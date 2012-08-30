package thinh.gianhangao.gianhangao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditProfileActivity extends Activity {
    /** Called when the activity is first created. */
	private EditText Name_st, Pass_st, Repass_st  ;
	private EditText Email_st , Address , Phone , Edit_date ;
	private Spinner Gender;
	private Button Save;
	private Button Cancer;
	private JSONParser jsonParser = new JSONParser();
	private String URL = "http://gianhangao-1.herokuapp.com/users/12/edit.json";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        findview();
        Save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				JSONObject js_edit = postEvents();
				try {
					if(js_edit.getInt("status")==1)
					{
						Toast.makeText(getApplicationContext(), "Edit profile is success", Toast.LENGTH_LONG).show();
						//startActivity(new Intent(getApplicationContext(), GianhangaoActivity.class));
					}
					else
						Toast.makeText(getApplicationContext(), "Edit profile is failt", Toast.LENGTH_LONG).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        Cancer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    private void findview()
    {
    	Name_st = (EditText) findViewById(R.id.edit_name);
    	Pass_st = (EditText) findViewById(R.id.edit_password);
    	Repass_st = (EditText) findViewById(R.id.reedit_password);
    	Email_st = (EditText) findViewById(R.id.edit_email);
    	Address = (EditText) findViewById(R.id.edit_address);
    	Phone = (EditText) findViewById(R.id.edit_phone);
    	Gender = (Spinner) findViewById(R.id.edit_gender);
    	Edit_date = (EditText) findViewById(R.id.edit_set_date);
    	Save = (Button) findViewById(R.id.save);
    	Cancer = (Button) findViewById(R.id.cancel);
    }
	private JSONObject postEvents()
	{
		 //fÃ¼r momentane testzwecke
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user[name]", Name_st.getText().toString()));
		params.add(new BasicNameValuePair("user[password]", Pass_st.getText().toString()));
		params.add(new BasicNameValuePair("user[password_confirmation]", Repass_st.getText().toString()));
		params.add(new BasicNameValuePair("user[mail]", Email_st.getText().toString()));
		params.add(new BasicNameValuePair("user[addr]", Address.getText().toString()));
		params.add(new BasicNameValuePair("user[phone]", Phone.getText().toString()));
		params.add(new BasicNameValuePair("user[gender]", Gender.getSelectedItem().toString()));
		params.add(new BasicNameValuePair("user[birthday]", Edit_date.getText().toString()));
		// getting JSON Object
		
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		if(json != null)
			Log.i("json not null", json.toString());
		// return json
		return json;
	}
}