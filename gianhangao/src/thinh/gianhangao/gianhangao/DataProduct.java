package thinh.gianhangao.gianhangao;

import java.io.Serializable;
/**
 * 
 * @author Andrew Gertig
 *
 */
public class DataProduct implements Serializable{

	private static final long serialVersionUID = 223L;
	
	public int id					= 0 ;
	public String describe			= "";
	public int price				= 0 ;
	public int inventory			= 0 ;
	public String product			= "";
	public String image				= "";
	
	public String getEventDescribe() {
        return describe;
    }
    public void setEventDescribe(String describe) {
        this.describe = describe;
    }
    public int getEventID() {
        return id;
    }
    public void setEventID(Integer id) {
        this.id = id;
    }
    public int getEventPrice() {
        return price;
    }
    public void setEventPrice(Integer price) {
        this.price = price;
    }
    public int getEventInventory() {
        return inventory;
    }
    public void setEventInventory(Integer inventory) {
        this.inventory = inventory;
    }
    public String getEventProduct() {
        return product;
    }
    public void setEventProduct(String product) {
        this.product = product;
    }
	public String getURL() {
		// TODO Auto-generated method stub
		return this.image;
	}
    
}
