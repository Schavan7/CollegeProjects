package model.dao;


import java.sql.SQLException;
import java.util.ArrayList;

import model.Cart;
import model.Order;
import model.OrderItem;

public class CartDAO  extends Dao{

	public Cart getUserCart(int userId){
		Cart cart =  null;
		
		try {
			String sql= "select * from schavan7_carts where user_id =" + userId ;
			rs = executeFetchQuery (sql);			
			if (rs.next()){	
				cart = new Cart();
				cart.setCartId(Integer.parseInt(rs.getString("cart_id")));
				cart.setTotal(rs.getDouble("total"));
				cart.setTax(rs.getDouble("tax"));
				cart.setOrderPickupDate(rs.getDate("order_pickup_date"));
				cart.setUserId(Integer.parseInt(rs.getString("user_id")));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return cart;
	}
	
	public void addCart(Cart cart){
		Integer cart_id = null;
		try {
			String sql= "INSERT into schavan7_carts(total,tax,order_pickup_date,user_id)  "
					+ "values(" + cart.getTotal() + "," + cart.getTax() + "," + cart.getOrderPickupDate() + ","+  cart.getUserId()
					+ ")";
			cart_id =  executeModifySelectQuery(sql);
			cart.setCartId(cart_id);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateCart(Cart cart){
		try {
			String sql= "UPDATE schavan7_carts SET total = " + cart.getTotal()  + 
					", user_id=" + cart.getUserId() + ",tax=" + cart.getTax() + ",order_pickup_date=" + cart.getOrderPickupDate();
			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteCart(Cart cart){
		try {
			String cart_sql= "DELETE FROM schavan7_carts WHERE cart_id = " + cart.getCartId();
			String cart_item_sql= "DELETE FROM schavan7_cart_items WHERE cart_id = " + cart.getCartId();
			executeModifySelectQuery(cart_item_sql);	
			executeModifySelectQuery(cart_sql);	
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
