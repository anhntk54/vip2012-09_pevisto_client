package thinh.gianhangao.gianhangao;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class Control_id {
 private static final String APP_SHARED_PREFS = "thinh.gianhangao.gianhangao"; 
 private SharedPreferences appSharedPrefs;
 private Editor prefsEditor;
 
 public Control_id(Context context) {
  this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
    Activity.MODE_PRIVATE);
  this.prefsEditor = appSharedPrefs.edit();
 }
 
 public String getID() {
  return appSharedPrefs.getString("ID", "");
 }
 
 public void saveID(String text) {
  prefsEditor.putString("ID", text);
  prefsEditor.commit();
 }
 public String getIdCompany()
 {
	 return appSharedPrefs.getString("IDCompany", "");
 }
 public void saveIdCompany(int id)
 {
	 prefsEditor.putInt("IDCompany", id);
	 prefsEditor.commit();
 }
}