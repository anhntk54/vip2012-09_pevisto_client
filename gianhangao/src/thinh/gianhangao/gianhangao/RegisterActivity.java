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

public class RegisterActivity extends Activity {
    /** Called when the activity is first created. */
	EditText Account , User_st, Pass_st, Repass_st  ;
	EditText  Name_st, Email_st ,ReEmail_st ,Address ,Phone ,Edit_date ;
	Spinner Gender;
	TextView LoginBack ;
	Button Reg_account;
	JSONParser jsonParser = new JSONParser();
	String URL = "http://gianhangao-1.herokuapp.com/users.json";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_order);
        findview();
        LoginBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), GianhangaoActivity.class));
			}
		});
        Reg_account.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String email = Email_st.getText().toString();
				String reemail = ReEmail_st.getText().toString();
				if(email.equals(reemail))
				{
					JSONObject js = postEvents();
					try {
						if(js.getInt("status")==1)
						{
							Toast.makeText(getApplicationContext(), "Register is success", Toast.LENGTH_LONG).show();
							startActivity(new Intent(getApplicationContext(),GianhangaoActivity.class));
							finish();
						}
						else
							Toast.makeText(getApplicationContext(), "Register is fault", Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
					Toast.makeText(getApplicationContext(), "Email is incorrect", Toast.LENGTH_LONG).show();
			}
		});
    }
    private void findview()
    {
    	Account = (EditText) findViewById(R.id.reg_acc);
    	User_st = (EditText) findViewById(R.id.re_password);
    	Pass_st = (EditText) findViewById(R.id.reg_password);
    	Repass_st = (EditText) findViewById(R.id.re_password);
    	Name_st = (EditText) findViewById(R.id.Full_name);
    	Email_st = (EditText) findViewById(R.id.reg_email);
    	ReEmail_st = (EditText) findViewById(R.id.re_email);
    	Address = (EditText) findViewById(R.id.address);
    	Phone = (EditText) findViewById(R.id.phone);
    	Gender = (Spinner) findViewById(R.id.gender);
    	Edit_date = (EditText) findViewById(R.id.set_date);
    	LoginBack = (TextView) findViewById(R.id.link_to_login);
    	Reg_account = (Button) findViewById(R.id.btnRegister);
    }
	private JSONObject postEvents()
	{
		 //fÃ¼r momentane testzwecke
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user[name]", Name_st.getText().toString()));
		params.add(new BasicNameValuePair("user[user]", Account.getText().toString()));
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