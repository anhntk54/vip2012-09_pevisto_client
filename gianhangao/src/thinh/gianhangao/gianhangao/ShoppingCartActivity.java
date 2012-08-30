package thinh.gianhangao.gianhangao;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShoppingCartActivity extends Activity {

	private ListView listViewCatalog;
	private ArrayList<Product> eventsArrayList = null;
	private AdapterListProduct m_adapter;
	String URL = "http://gianhangao-1.herokuapp.com/orders.json";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoppingcart);
		listViewCatalog = (ListView) findViewById(R.id.list_pro_cart);

		eventsArrayList = new ArrayList<Product>();
		eventsArrayList = parseXMLString("");

		this.m_adapter = new AdapterListProduct(this,
				R.layout.item_product_cart, eventsArrayList);
		listViewCatalog.setAdapter(this.m_adapter);

	}

	/*
	 * parseXMLString() by Jonathan Gertig edited by Andrew Gertig
	 */
	private ArrayList<Product> parseXMLString(String xmlString) {
		JSONArray array = null;
		JSONParser json = new JSONParser();

		try {

			array = new JSONArray(json.readGetJson(URL));
			for (int i = 0; i < array.length(); i++) {
				Product Product = new Product();
				JSONObject jb = array.getJSONObject(i);
				Product.setEventProId(jb.getInt("product_id"));
				Product.setEventQuantity(jb.getInt("quantily"));
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
					}
				});
				TextView name = (TextView) v
						.findViewById(R.id.name_product_cart);
				TextView quantily = (TextView) v
						.findViewById(R.id.quantity_product_cart);

				if (name != null) {
					name.setText("ID: " + o.getEventProId());
				}
				if (quantily != null) {
					quantily.setText("Quantily : " + o.getEventQuantity());
				}
			}
			return v;
		}
	}
}
