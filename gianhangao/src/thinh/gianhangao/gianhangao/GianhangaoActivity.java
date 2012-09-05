package thinh.gianhangao.gianhangao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	ProgressDialog dialog;
	// Hashmap for ListView
	String URL = "http://gianhangao-1.herokuapp.com/sessions.json";
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dialog.dismiss();
			int isLogin = (Integer) msg.obj;
			Log.d("Login", msg.toString());
			if(isLogin == 1){
				Toast.makeText(getApplicationContext(), "Login successful...", 1).show();
				startActivity(new Intent(getApplicationContext(), CompanyActivity.class));
				finish();
			} else if(isLogin == 0) Toast.makeText(getApplicationContext(), "Login fault...", 1).show();
			else Toast.makeText(getApplicationContext(), "Error connect to serrver...", 1).show();
		}
	}; // handler

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
			v.performHapticFeedback(HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING);
			dialog = ProgressDialog.show(this, "", "Please wait for few seconds...", true);
			Thread login = new Thread(new Runnable() {
				public void run() {
					try {
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("session[user]", username.getText().toString()));
						params.add(new BasicNameValuePair("session[password]", password.getText().toString()));
						// getting JSON Object
						JSONObject js = jsonParser.getJSONFromUrl(URL, params);
						//JSONObject js = postEvents();
						int isLogin;
						if (js == null ){
							isLogin = -1;
						} else if (js.getInt("status") == 1) {
							control = new Control_id(getApplicationContext());
							control.saveID(js.getString("user_id"));
							Log.d("LOGIN", js.toString());
							isLogin = 1;
						} else
							isLogin = 0;
						Message msg = handler.obtainMessage(1, isLogin);
						handler.sendMessage(msg);
					} catch (Throwable t) {
					}
				}// run
			});// background
			login.start();
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
}