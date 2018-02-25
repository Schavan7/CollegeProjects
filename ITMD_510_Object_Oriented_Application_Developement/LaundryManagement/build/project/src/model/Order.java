package model;

import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {
	public enum OrderStatus {
		SUBMIT,PICKEDUP,PROCESS,COMPLETE;
	}

	private IntegerProperty orderId;
	private SimpleObjectProperty<Date> orderPickupDate;
	private StringProperty status;
	private DoubleProperty total;
	private DoubleProperty tax;
	private IntegerProperty userId;
	private SimpleObjectProperty<Date> orderCreatedDate;



	public Integer getUserId() {
		return userId.get();
	}
	public void setUserId(int userId) {
		this.userId =  new SimpleIntegerProperty(userId) ;
	}

	public Integer getOrderId() {
		return orderId.get();
	}
	public void setOrderId(Integer orderId) {
		this.orderId =  new SimpleIntegerProperty(orderId) ;
	}
	public String getStatus() {
		return status.get();
	}
	public void setStatus(String status) {
		this.status = new SimpleStringProperty(status);
	}
	public Double getTotal() {
		return total.get();
	}
	public void setTotal(Double total) {
		this.total = new SimpleDoubleProperty(total);
	}
	public Double getTax() {
		return tax.get();
	}
	public void setTax(Double tax) {
		this.tax = new SimpleDoubleProperty(tax);
	}
	public void setOrderPickupDate(Date orderPickupDate) {
		this.orderPickupDate = new SimpleObjectProperty(orderPickupDate);
	}

	public Date getOrderPickupDate() {
		return this.orderPickupDate.get();
	}

	public void setOrderCreatedDate(Date orderCreatedDate) {
		this.orderCreatedDate =  new SimpleObjectProperty(orderCreatedDate);
	}

	public Date getOrderCreatedDate() {
		return this.orderCreatedDate.get();
	}


}
