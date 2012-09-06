package thinh.gianhangao.gianhangao;

import java.io.Serializable;

import android.graphics.drawable.Drawable;
/**
 * 
 * @author Andrew Gertig
 *
 */
public class CompanyProperties implements Serializable{

	private static final long serialVersionUID = 223L;
	
	public String name			= "";	
	public int ID				= 0 ;
	public String getEventName() {
        return name;
    }
    public void setEventName(String name) {
        this.name = name;
    }
    public Integer getEventID(){
    	return ID;
    }
    public void setEventID(Integer ID)
    {
    	this.ID = ID;
    }
}
