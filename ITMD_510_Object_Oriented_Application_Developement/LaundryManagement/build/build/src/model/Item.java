package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {

	
	private  StringProperty serviceName;
	private  DoubleProperty price;
	private  StringProperty serviceType;



public String getServiceName() {
	return serviceName.get();
}
public void setServiceName(String serviceName) {
	this.serviceName = new SimpleStringProperty(serviceName);
}

public StringProperty serviceNameProperty(){
	return this.serviceName;
}

public Double getPrice() {
	return price.get();
}
public void setPrice(Double price) {
	this.price = new SimpleDoubleProperty(price);
}

public DoubleProperty priceProperty(){
	return this.price;
}

public String getServiceType() {
	return serviceType.get();
}
public void setServiceType(String serviceType) {
	this.serviceType = new SimpleStringProperty(serviceType);
}

@Override
public String toString() {
    return this.getServiceName();
}
}
