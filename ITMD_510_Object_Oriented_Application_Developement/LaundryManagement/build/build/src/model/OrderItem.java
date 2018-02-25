package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class OrderItem  extends Item {
	private int orderItemId;
	private int quantity;
	private double tax;
	private DoubleProperty subTotalPrice;
	private double totalPrice;
	private int orderId;


	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public Double getSubTotalPrice() {
		return subTotalPrice.get();
	}
	public void setSubTotalPrice(Double subTotalPrice) {
		this.subTotalPrice = new SimpleDoubleProperty(subTotalPrice);
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
