package thinh.gianhangao.gianhangao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

public class ShoppingCartActivity extends Activity {

	private Button remove;
	private ListView listViewCatalog;
	private ArrayList<Product> eventsArrayList = null;
	private AdapterListProduct m_adapter;
	private Context context;
	private Stack<Integer> delList;
	JSONParser json = new JSONParser();
	String URL = "http://gianhangao-1.herokuapp.com/api/userorder/";
	String URLRemove = "http://gianhangao-1.herokuapp.com/api/deleteorder/";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoppingcart);
		listViewCatalog = (ListView) findViewById(R.id.list_pro_cart);
		
		delList = new Stack<Integer>();
		remove = (Button) findViewById(R.id.romove_from_cart);
		remove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				while(!delList.empty()){
					String urlDel = URLRemove + delList.pop();
					String js = json.readGetJson(urlDel);
					Log.d("card", js);
				}
				m_adapter.clear();
				eventsArrayList = parseXMLString(URL);
				m_adapter = new AdapterListProduct(context, R.layout.item_product_cart, eventsArrayList);
				listViewCatalog.setAdapter(m_adapter);
			}
		});
		
		Control_id control = new Control_id(getApplicationContext());
		String ID = control.getID();
		URL = URL + ID + ".json";
		context = getBaseContext();
		eventsArrayList = new ArrayList<Product>();
		eventsArrayList = parseXMLString(URL);

		this.m_adapter = new AdapterListProduct(this, R.layout.item_product_cart, eventsArrayList);
		listViewCatalog.setAdapter(this.m_adapter);

	}

	/*
	 * parseXMLString() by Jonathan Gertig edited by Andrew Gertig
	 */
	private ArrayList<Product> parseXMLString(String xmlString) {
		JSONArray array = null;
		JSONParser json = new JSONParser();

		try {

			array = new JSONArray(json.readGetJson(xmlString));
			for (int i = 0; i < array.length(); i++) {
				Product Product = new Product();
				JSONObject jb = array.getJSONObject(i);
				Log.d("SP", jb.toString());
				Product.setEventProId(jb.getInt("product_id"));
				Product.setEventQuantity(jb.getInt("quantily"));
				Product.setID(jb.getInt("id"));
				Product.setEventName(jb.getString("name"));
				Product.setEventPrice(jb.getInt("price"));
				
				JSONObject imageJsonObject = jb.getJSONObject("image");
				Log.d("IMG", imageJsonObject.toString());
/*				JSONObject thumb = imageJsonObject.getJSONObject("thumb1");
				String url = thumb.getString("url");
				Product.setIMG(url);*/
				
				eventsArrayList.add(Product);
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
		return "?"; // ListActivity will display a ? if a null value is passed
					// to the Rails server
	}

	private class AdapterListProduct extends ArrayAdapter<Product> {

		private ArrayList<Product> items;

		public AdapterListProduct(Context context, int textViewResourceId,
				ArrayList<Product> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.item_product_cart, null);
			}
			final Product o = items.get(position);
			if (o != null) {
				v.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showAlert(o);
					}
				});
				TextView name = (TextView) v.findViewById(R.id.name_product_cart);
				TextView quantily = (TextView) v.findViewById(R.id.quantity_product_cart);
				TextView ten = (TextView) v.findViewById(R.id.ten);
				TextView gia = (TextView) v.findViewById(R.id.gia);
				final CheckBox del = (CheckBox) v.findViewById(R.id.checkbook_product_cart);
				
				name.setText("ID: " + o.getEventProId());
				quantily.setText("Quantily: " + o.getEventQuantity());
				ten.setText(o.getEventName());
				gia.setText("Price: " + o.getEventPrice());
				
				del.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(del.isChecked()) 
							delList.push(o.getID());
						else {
							int index = delList.search(o.getID());
							Iterator<Integer> i = delList.iterator(); 
							for(int j = 0; j < index; j++)
								i.next();
							i.remove();
						}
					}
				});
			} 
			return v;
		}
	}
	private void showAlert(final Product o) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.msg_del)
				.setTitle(R.string.title)
				.setCancelable(false)
				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								String urlDel = URLRemove + o.getID();
								String js = json.readGetJson(urlDel);
								Log.d("card", js);
								
								m_adapter.clear();
								eventsArrayList = parseXMLString(URL);
								m_adapter = new AdapterListProduct(context, R.layout.item_product_cart, eventsArrayList);
								listViewCatalog.setAdapter(m_adapter);
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
								dialog.cancel();
							}
						});
		builder.show();
	}
}
