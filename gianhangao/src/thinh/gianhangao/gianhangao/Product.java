package thinh.gianhangao.gianhangao;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class Product implements Serializable {
 
	private static final long serialVersionUID = 223L;
	
	public int product_id		= 0;
	public String name			= "";
	public int price 			= 0;
	public int quantity			= 0;
	public int ID = 0;

	public Drawable productImage;

	public CharSequence title;

	public boolean selected;
 
 	public int getEventProId()
 	{
 		return this.product_id;
 	}
 	public void setEventProId(int product_id)
 	{
 		this.product_id=product_id;
 	}
 	public String getEventName()
 	{
 		return this.name;
 	}
 	public void setEventName(String name)
 	{
 		this.name=name;
 	}
 	public int getEventPrice()
 	{
 		return this.price;
 	}
 	public void setEventPrice(int price)
 	{
 		this.price=price;
 	}
 	public int getEventQuantity()
 	{
 		return this.quantity;
 	}
 	public void setEventQuantity(int quantity)
 	{
 		this.quantity=quantity;
 	}
 	public void setID(int id){
 		ID = id;
 	}
 	public int getID(){
 		return ID;
 	}
}