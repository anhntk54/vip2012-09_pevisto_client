package thinh.gianhangao.gianhangao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class GianhangaoActivity extends Activity {
	/** Called when the activity is first created. */
	EditText username;
	EditText password;
	Button register, login;
	int lg_user, lg_pass;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.main); // login
			username = (EditText) findViewById(R.id.txt_username);
			password = (EditText) findViewById(R.id.txt_password);
			register = (Button) findViewById(R.id.register);
			login = (Button) findViewById(R.id.btn_login);

			// control register with conditions
			register.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						startActivity(new Intent(getApplicationContext(),
								register.class));
						finish();
					} catch (Exception e) {
						Toast.makeText(getBaseContext(), e.getMessage(),
								Toast.LENGTH_LONG).show();
					}
				}
			});
			login.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					try {
						lg_user = username.getText().toString().length();
						lg_pass = password.getText().toString().length();
						if (lg_user == 0 || lg_pass == 0)
							Toast.makeText(getApplicationContext(),
									"Bạn nhập thiếu dữ liệu", Toast.LENGTH_LONG)
									.show();
						else {
							startActivity(new Intent(getApplicationContext(),
									CatalogActive.class));
							finish();
						}
					} catch (Exception e) {
						Toast.makeText(getBaseContext(), e.getMessage(),
								Toast.LENGTH_LONG).show();
					}
				}
			});
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}
}