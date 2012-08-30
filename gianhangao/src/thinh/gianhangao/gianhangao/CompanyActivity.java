package thinh.gianhangao.gianhangao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class CompanyActivity extends Activity implements OnClickListener {

	TextView setting, help, logout;
	private ArrayList<CompanyProperties> eventsArrayList = null;
	private ItemListBaseAdapter m_adapter;
	String URL = "http://gianhangao-1.herokuapp.com/api/showcompany.json";
	Control_id control;
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_company);
		findview();
		setClickListeners();
		eventsArrayList = new ArrayList<CompanyProperties>();
	    eventsArrayList  = parseXMLString("");
	    this.m_adapter = new ItemListBaseAdapter(this, R.layout.item_company, eventsArrayList);
	    GridView gridview = (GridView) findViewById(R.id.gridViewCompany);
        gridview.setAdapter(this.m_adapter);

	}
	private void retreiveProjects()
	{
	  HttpClient httpClient = new DefaultHttpClient();
	  
	  String xmlResponse;
	  
	  try
	  {
	    /** FOR LOCAL DEV String url = "http://192.168.0.186:3000/events?format=xml"; */
		String url = "http://gianhangao-1.herokuapp.com/api/showcompany.json";
	    Log.d( "gertigable", "performing get " + url );
	
	    HttpGet method = new HttpGet( new URI(url) );
	    HttpResponse response = httpClient.execute(method);
	    if ( response != null )
	    {
	    	xmlResponse = getResponse(response.getEntity());
	        //Log.i( "Gertig", "received " + xmlResponse);
	    	eventsArrayList = parseXMLString(xmlResponse);
	    }
	    else
	    {
	      Log.i( "Gertig", "got a null response" );
	    }
	  } catch (IOException e) {
	    Log.e( "Error", "IOException " + e.getMessage() );
	  } catch (URISyntaxException e) {
	    Log.e( "Error", "URISyntaxException " + e.getMessage() );
	  }
	  
	}

	private String getResponse( HttpEntity entity )
	{
	  String response = "";
	
	  try
	  {
	    int length = ( int ) entity.getContentLength();
	    StringBuffer sb = new StringBuffer( length );
	    InputStreamReader isr = new InputStreamReader( entity.getContent(), "UTF-8" );
	    char buff[] = new char[length];
	    int cnt;
	    while ( ( cnt = isr.read( buff, 0, length - 1 ) ) > 0 )
	    {
	      sb.append( buff, 0, cnt );
	    }
	
	      response = sb.toString();
	      isr.close();
	  } catch ( IOException ioe ) {
	    ioe.printStackTrace();
	  }
	
	  return response;
	}

/*
 * parseXMLString() by Jonathan Gertig
 * edited by Andrew Gertig
 */
	private ArrayList<CompanyProperties> parseXMLString(String xmlString) {
		JSONArray array = null;
		JSONParser json = new JSONParser();
		String url_image = "";
		try {
			array = new JSONArray(json.readGetJson(URL));
			for ( int i =0 ; i < array.length() ; i++){
				CompanyProperties myEvents = new CompanyProperties();
				JSONObject jb = array.getJSONObject(i);
				myEvents.setEventName(jb.getString("name"));
				myEvents.setEventID(jb.getInt("id"));
				JSONObject ij = array.getJSONObject(i).getJSONObject("image");
				url_image = ij.getString("thumb");
				eventsArrayList.add(myEvents);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return eventsArrayList;   
		
	}
	
	public static String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	       CharacterData cd = (CharacterData) child;
	       return cd.getData();
	    }
	    return "?"; //ListActivity will display a ? if a null value is passed to the Rails server
	}
	public  class ItemListBaseAdapter extends ArrayAdapter<CompanyProperties>{
		
		private ArrayList<CompanyProperties> eventsArrayList=null;
		
		public ItemListBaseAdapter(Context context,int textViewResourceId, ArrayList<CompanyProperties> results) {
	        super(context, textViewResourceId, results);
	        this.eventsArrayList = results;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.item_company, null);
			}
	        final CompanyProperties o = eventsArrayList.get(position);
	        if (o != null) {
	        	
	        	v.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getApplicationContext(), CatalogActive.class);
						Bundle conpany_id = new Bundle();
						intent.putExtra("ID", o.getEventID());
						startActivity(intent);
					}
				});
	        		
	        	TextView name = (TextView) v.findViewById(R.id.name_company);
	            if(name != null){
	            	name.setText(o.getEventName());
	            }
	        }
	        return v;
		}
	}
	public void findview() {

		setting = (TextView) findViewById(R.id.setting);
		help = (TextView) findViewById(R.id.help);
		logout = (TextView) findViewById(R.id.logout);
	}

	public void setClickListeners() {
		// BUTTON LISTENERS
		setting.setOnClickListener(this);
		help.setOnClickListener(this);
		logout.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting:
			startActivity(new Intent(getApplicationContext(),
					EditProfileActivity.class));
			break;
		case R.id.help:
			break;
		case R.id.logout:
			if (logout.getText().toString().equals("Logout")) {
				Control_id control = new Control_id(getApplicationContext());
				control.saveID("");
				Toast.makeText(getApplicationContext(),
						"You are logout is success", Toast.LENGTH_LONG).show();
				logout.setText("Login");
			} else
				startActivity(new Intent(getApplicationContext(),
						GianhangaoActivity.class));
			break;
		}
	}
}