package thinh.gianhangao.gianhangao;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import thinh.gianhangao.gianhangao.ImageDownload.Image;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailsActivity extends Activity {

	JSONParser jsonParser = new JSONParser();
	Resources res;
	Control_id control;
	Long product_id;
	EditText quantity;
	String URL = "http://gianhangao-1.herokuapp.com/orders.json";
	ImageView IMG;
	private Image ivLoader = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);

		Intent intent = getIntent();
		Bundle product = intent.getExtras();
		product_id = product.getLong("product_id");
		Log.d("Detail", "oncreate");
		final String name = product.getString("name");
		final String description = product.getString("describe");
		final int price = product.getInt("price");
		final int inventory = product.getInt("inventory");
		final String urlIMG = product.getString("urlImage");
		String state = "out of stock";
		if (inventory > 0)
			state = "in stock";

		// Set the proper image and text
		TextView productTitleTextView = (TextView) findViewById(R.id.title_sanpham);
		productTitleTextView.setText(name);
		TextView productTitleState = (TextView) findViewById(R.id.title_tinhtrang);
		productTitleState.setText(state);
		TextView productPriceTextView = (TextView) findViewById(R.id.gia);
		productPriceTextView.setText(Integer.toString(price));
		TextView productDetailsTextView = (TextView) findViewById(R.id.mieuta);
		productDetailsTextView.setText(description);
		quantity = (EditText) findViewById(R.id.quantity_pro);
		IMG = (ImageView) findViewById(R.id.icon_detail);
		// IMG.setImageResource(R.drawable.detial);
		ivLoader = new Image(getApplicationContext());
		ivLoader.DisplayImage(urlIMG, IMG);

		Button addToCartButton = (Button) findViewById(R.id.add_to_cart);
		addToCartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (quantity.getText().length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Quantity must not empty", Toast.LENGTH_LONG)
							.show();
				} else {
					if (Integer.parseInt(quantity.getText().toString()) > 0) {
						if (inventory > 0) {
							JSONObject js = postEvents(product_id);

							try {
								if (js.getInt("id") > 0) {
									Toast.makeText(getApplicationContext(),
											"creater order is sussces",
											Toast.LENGTH_LONG).show();
									startActivity(new Intent(
											getApplicationContext(),
											ShoppingCartActivity.class));
									finish();
								} else
									Toast.makeText(getApplicationContext(),
											"creater order is false",
											Toast.LENGTH_LONG).show();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else
							Toast.makeText(getApplicationContext(),
									"product is out of stock",
									Toast.LENGTH_LONG).show();
					} else
						Toast.makeText(getApplicationContext(),
								"Quantity must not empty", Toast.LENGTH_LONG)
								.show();
				}
			}
		});
	}

	private JSONObject postEvents(Long cur) {
		// momentane testzwecke
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		control = new Control_id(getApplicationContext());
		String user_id = control.getID().toString();
		params.add(new BasicNameValuePair("order[user_id]", user_id));
		params.add(new BasicNameValuePair("order[product_id]", Long
				.toString(cur)));
		params.add(new BasicNameValuePair("order[quantily]", quantity.getText()
				.toString()));
		// getting JSON Object

		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		if (json != null)
			Log.i("json not null", json.toString());
		// return json
		return json;
	}
}