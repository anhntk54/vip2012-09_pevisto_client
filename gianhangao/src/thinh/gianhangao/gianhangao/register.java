package thinh.gianhangao.gianhangao;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import java.text.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.content.*;

public class register extends Activity {
    /** Called when the activity is first created. */
	DateFormat datefomat = DateFormat.getDateInstance();
	EditText editDate ; // set Date after click edittext and it throw widget set calendar
	TextView LoginBack ;
	// ------------ set calendar---------------------------------------------
	Calendar cal = Calendar.getInstance();
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, monthOfYear);
			cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
			updateCalendar();
			// TODO Auto-generated method stub
		
		}
	};
	public void updateCalendar()
	{
		editDate.setText(datefomat.format(cal.getTime()));
	}
	//----------- end setCalendar --------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_order);
        LoginBack = (TextView) findViewById(R.id.link_to_login); // link click will back to activity login
        editDate = (EditText) findViewById(R.id.setDate); // EditText set Date
        LoginBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), GianhangaoActivity.class));
			}
		});
        editDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(register.this,d,
						cal.get(Calendar.YEAR),
						cal.get(Calendar.MONDAY),
						cal.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
        
    }
}