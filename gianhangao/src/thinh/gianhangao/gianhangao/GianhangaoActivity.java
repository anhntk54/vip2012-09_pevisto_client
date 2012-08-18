package thinh.gianhangao.gianhangao;


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

public class GianhangaoActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	EditText username ; 
	EditText password ;
	String account_cur, jsonText;
	String User_key, Pass_key;
	JSONParser jsonParser = new JSONParser();
	String URL = "http://gianhangao-1.herokuapp.com/sessions.json";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViews();
        setClickListeners();

    }
	/** Get a handle to all user interface elements */
	private void findViews() {
		username = (EditText) findViewById(R.id.txt_username);

		password = (EditText) findViewById(R.id.txt_password);
		
	}
    
    public void setClickListeners() {
    	//BUTTON LISTENERS
        View login = findViewById(R.id.btn_login);
        login.setOnClickListener(this);
        View bt_register = findViewById(R.id.register);
        bt_register.setOnClickListener(this);
    }
	public void onClick(View v) {
		//Log.e("onClick","ClickListener is working");			
		switch (v.getId()) {
		
    	case R.id.btn_login:   		
    		v.performHapticFeedback(HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING); //Haptic feedback is cool
    		//Once the text boxes have been filled in we can post the data to the Rails server
    		JSONObject js = postEvents();
    		try {
				if(js.getInt("status")==1)
				{
					/*SharedPreferences shPre = getSharedPreferences(account_cur, MODE_WORLD_READABLE);
					SharedPreferences.Editor editor = shPre.edit();
					editor.putString(User_key, username.getText().toString());
					editor.putString(Pass_key, password.getText().toString());
					editor.commit();*/
					Toast.makeText(getApplicationContext(), "login is sucess", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),CompanyActivity.class));
					finish();
				}
				else
					Toast.makeText(getApplicationContext(), "login is fault", Toast.LENGTH_LONG).show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		Log.e("json", js.toString());
    		
    		break;
    	case R.id.register :
    		try{
				startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
				finish();
			}
			catch (Exception e){
				Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}
	private JSONObject postEvents()
	{
		 //fÃ¼r momentane testzwecke
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("session[user]", username.getText().toString()));
		params.add(new BasicNameValuePair("session[password]",password.getText().toString()));
		
		// getting JSON Object
		
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		if(json != null)
			Log.i("json not null", json.toString());
		// return json
		return json;
	}
}