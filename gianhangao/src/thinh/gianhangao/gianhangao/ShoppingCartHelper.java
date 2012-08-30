package thinh.gianhangao.gianhangao;

import java.util.List;
import java.util.Vector;
 
import android.content.res.Resources;
 
public class ShoppingCartHelper {
  
	 public static final String PRODUCT_INDEX = "PRODUCT_INDEX";
	 private static List<Product> cart;
	 
	 public static List<Product> getCart() {
	 if(cart == null) {
		 cart = new Vector<Product>();
  	 }
  	 return cart;
 	 }
 
}