package thinh.gianhangao.gianhangao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class GianhangaoActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	EditText username;
	EditText password;
	String account_cur, jsonText;
	String User_key, Pass_key;
	Control_id control;
	JSONParser jsonParser = new JSONParser();
	// Hashmap for ListView
    
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
		// BUTTON LISTENERS
		View login = findViewById(R.id.btn_login);
		login.setOnClickListener(this);
		View bt_register = findViewById(R.id.register);
		bt_register.setOnClickListener(this);
	}

	public void onClick(View v) {
		// Log.e("onClick","ClickListener is working");
		switch (v.getId()) {

		case R.id.btn_login:
			v.performHapticFeedback(HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING); // Haptic
																						// feedback
																						// is
																						// cool
			// Once the text boxes have been filled in we can post the data to
			// the Rails server
			JSONObject js = postEvents();
			try {
				if (js.getInt("status") == 1) {
					control = new Control_id(getApplicationContext());
					control.saveID(js.getString("user_id"));
					Toast.makeText(getApplicationContext(), "login is sucess",
							Toast.LENGTH_LONG).show();
					Log.e("ID", js.getString("user_id"));
					startActivity(new Intent(getApplicationContext(),
							CompanyActivity.class));
					finish();
				} else
					Toast.makeText(getApplicationContext(), "login is fault",
							Toast.LENGTH_LONG).show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.e("json", js.toString());

			break;
		case R.id.register:
			try {
				startActivity(new Intent(getApplicationContext(),
						RegisterActivity.class));
				finish();
			} catch (Exception e) {
				Toast.makeText(getBaseContext(), e.getMessage(),
						Toast.LENGTH_LONG).show();
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