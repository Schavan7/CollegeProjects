package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ServiceItem extends Item {

	private  IntegerProperty serviceItemId;
	
	public ServiceItem(){
		this.setPrice(0.0);
		this.setServiceName("");
		this.setServiceType("");
		
	}

	public int getServiceItemId() {
		return serviceItemId.get();
	}
	public void setServiceItemId(int serviceItemId) {
		this.serviceItemId = new SimpleIntegerProperty(serviceItemId);
	}


}
