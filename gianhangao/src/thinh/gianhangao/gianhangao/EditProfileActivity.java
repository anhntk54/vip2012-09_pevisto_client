package thinh.gianhangao.gianhangao;

import java.util.Calendar;
import android.app.DatePickerDialog;
import java.text.DateFormat;
import android.widget.DatePicker;
import android.widget.TextView;
import android.content.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends Activity {
    /** Called when the activity is first created. */
	EditText Name_st, Pass_st, Repass_st  ;
	EditText Email_st , Address , Phone , Edit_date ;
	Spinner Gender;
	Button Save;
	Button Cancer;
	JSONParser jsonParser = new JSONParser();
	String URL = "http://gianhangao-1.herokuapp.com/users/3/edit";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        findview();
        Save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				JSONObject js = postEvents();
				try {
					if(js.getInt("status")==1)
					{
						Toast.makeText(getApplicationContext(), "Edit profile is success", Toast.LENGTH_LONG).show();
						//startActivity(new Intent(getApplicationContext(), GianhangaoActivity.class));
					}
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
    	Name_st = (EditText) findViewById(R.id.Full_name);
    	Pass_st = (EditText) findViewById(R.id.reg_password);
    	Repass_st = (EditText) findViewById(R.id.re_password);
    	Email_st = (EditText) findViewById(R.id.reg_email);
    	Address = (EditText) findViewById(R.id.address);
    	Phone = (EditText) findViewById(R.id.phone);
    	Gender = (Spinner) findViewById(R.id.gender);
    	Edit_date = (EditText) findViewById(R.id.set_date);
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