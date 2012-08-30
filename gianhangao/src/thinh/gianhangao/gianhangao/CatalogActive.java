package thinh.gianhangao.gianhangao;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import thinh.gianhangao.gianhangao.ImageDownload.Image;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Andrew Gertig
 */
public class CatalogActive extends Activity {
	
	private ListView listViewCatalog;
	private ArrayList<DataProduct> eventsArrayList = null;
	private AdapterListProduct m_adapter;
	String URL = "http://gianhangao-1.herokuapp.com/api/allproduct/";
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.catalog);
		listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
		
		
		
		eventsArrayList = new ArrayList<DataProduct>();
		eventsArrayList = parseXMLString("");

		this.m_adapter = new AdapterListProduct(this, R.layout.row, eventsArrayList);
		listViewCatalog.setAdapter(this.m_adapter);

	}

	private ArrayList<DataProduct> parseXMLString(String xmlString) {
		JSONArray array = null;
		JSONParser json = new JSONParser();

		try {
			Intent intent = getIntent();
			Bundle company_id = intent.getExtras();
			URL = URL + company_id.getInt("ID") + ".json";
			
			array = new JSONArray(json.readGetJson(URL));
			for (int i = 0; i < array.length(); i++) {
				DataProduct DataProduct = new DataProduct();
				JSONObject jb = array.getJSONObject(i);
				DataProduct.setEventDescribe(jb.getString("describe"));
				DataProduct.setEventProduct(jb.getString("product"));
				DataProduct.setEventID(jb.getInt("id"));
				DataProduct.setEventPrice(jb.getInt("price"));
				DataProduct.setEventInventory(jb.getInt("inventory"));
				
				JSONObject imageJsonObject = jb.getJSONObject("image");
				JSONObject thumb = imageJsonObject.getJSONObject("thumb1");
				DataProduct.image = thumb.getString("url");
				
				eventsArrayList.add(DataProduct);
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

	private class AdapterListProduct extends ArrayAdapter<DataProduct> {

		private ArrayList<DataProduct> items;
		private  LayoutInflater inflater = null;
		private Image ivLoader = null;
		private Intent intent;
		
		public AdapterListProduct(Context context, int textViewResourceId, ArrayList<DataProduct> items) {
			super(context, textViewResourceId, items);
			this.items = items;
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ivLoader = new Image(getApplicationContext());
			intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				v = inflater.inflate(R.layout.row, null);
			}
			final DataProduct o = items.get(position);
			if (o != null) {
				v.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Bundle product = new Bundle();
						product.putLong("product_id", o.getEventID());
						product.putString("name", o.getEventProduct());
						product.putString("describe", o.getEventDescribe());
						product.putInt("inventory", o.getEventInventory());
						product.putInt("price", o.getEventPrice());
						product.putString("urlImage", o.getURL());
						intent.putExtras(product);
						startActivity(intent);
					}
				});
				TextView id = (TextView) v.findViewById(R.id.id);
				TextView product = (TextView) v.findViewById(R.id.product);
				TextView describe = (TextView) v.findViewById(R.id.describe);
				TextView inventory = (TextView) v.findViewById(R.id.inventory);
				TextView price = (TextView) v.findViewById(R.id.price);
				ImageView img = (ImageView) v.findViewById(R.id.icon);
				
				id.setText("ID: " + o.getEventID());

				product.setText("Name: " + o.getEventProduct());

				describe.setText("Describe : " + o.getEventDescribe());

				inventory.setText("Inventory : " + o.getEventInventory());

				price.setText("Price : " + o.getEventPrice());

				ivLoader.DisplayImage(o.getURL(), img);
			}
			return v;
		}
	}

}