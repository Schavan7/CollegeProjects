package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import model.dao.AddressDAO;
import model.dao.CartItemDAO;

public class Cart {
	private int cartId;
	private double tax;
	private double total;
	private int userId;
	private Date orderPickupDate;


	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setOrderPickupDate(Date orderPickupDate) {
		this.orderPickupDate = orderPickupDate;
	}
	public Date getOrderPickupDate() {
		return this.orderPickupDate;
	}

	public Address getAddress(){
		return new AddressDAO().getAddress(this.getCartId(), "cart");
	}

	public ArrayList <CartItem> getCartItems(){
		return new CartItemDAO().getAllCartItems(this);
	}

	public void updateTax(){
		ArrayList <CartItem> cItems = getCartItems();
		Iterator<CartItem> itemsIterator = cItems.iterator();
		Double tempTax = 0.0;
		while(itemsIterator.hasNext()){
			CartItem item = itemsIterator.next();
			tempTax += item.getTax();
		}
		this.setTax(tempTax);
	}
	
	public void updateTotal(){
		ArrayList <CartItem> cItems = getCartItems();
		Iterator<CartItem> itemsIterator = cItems.iterator();
		Double total = 0.0;
		while(itemsIterator.hasNext()){
			CartItem item = itemsIterator.next();
			total += item.getTax() + item.getSubTotalPrice();
		}
		this.setTotal(total);
	}


}
