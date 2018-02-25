package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CartItem extends Item {
	private  IntegerProperty cartItemId;
	private  IntegerProperty cartId;
	private  IntegerProperty quantity;
	private DoubleProperty tax;
	private DoubleProperty subTotalPrice;
	private DoubleProperty totalPrice;


	public Integer getCartItemId() {
		return cartItemId.get();
	}
	public void setCartItemId(Integer cartItemId) {
		this.cartItemId = new SimpleIntegerProperty(cartItemId);
	}

	public int getCartId() {
		return cartId.get();
	}
	public void setCartId(Integer cartId) {
		this.cartId = new SimpleIntegerProperty(cartId);
	}

	public Integer getQuantity() {
		return quantity.get();
	}
	public void setQuantity(int quantity) {
		this.quantity = new SimpleIntegerProperty(quantity);
	}

	public Double getTax() {
		return tax.get();
	}
	public void setTax(Double tax) {
		this.tax = new SimpleDoubleProperty(tax);
	}
	public Double getSubTotalPrice() {
		return subTotalPrice.get();
	}
	public void setSubTotalPrice(Double subTotalPrice) {
		this.subTotalPrice = new SimpleDoubleProperty(subTotalPrice);
	}

	public Double getTotalPrice() {
		return totalPrice.get();
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = new SimpleDoubleProperty(totalPrice);
	}



}
