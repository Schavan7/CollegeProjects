package model.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import model.Cart;
import model.CartItem;

public class CartItemDAO  extends Dao{

	public CartItem getCartItem(String id){
		CartItem item =  new CartItem();
		try {
			String sql= "select * from schavan7_cart_items where ='" + id + "'";
			rs = executeFetchQuery (sql);			
			if (rs.next()){	
				item.setCartItemId(Integer.parseInt(rs.getString("cart_item_id")));
				item.setServiceName(rs.getString("service_name"));
				item.setPrice(rs.getDouble("price"));
				item.setServiceType(rs.getString("service_type"));
				item.setQuantity(Integer.parseInt(rs.getString("quantity")));
				item.setTax(Double.parseDouble(rs.getString("tax")));
				item.setSubTotalPrice(Double.parseDouble(rs.getString("sub_total_price")));
				item.setTotalPrice(Double.parseDouble(rs.getString("total_price")));
				item.setCartId(Integer.parseInt(rs.getString("cart_id")));
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		return item;
	}

	public ArrayList<CartItem> getAllCartItems(Cart cart){	

		ArrayList <CartItem> list = new ArrayList<CartItem>();
		try {
			String sql= "select * from schavan7_cart_items where cart_id="+ cart.getCartId() ;
			rs = executeFetchQuery (sql);			
			while (rs.next()){	
				CartItem item =  new CartItem();
				item.setCartItemId(Integer.parseInt(rs.getString("cart_item_id")));
				item.setServiceName(rs.getString("service_name"));
				item.setPrice(rs.getDouble("price"));
				item.setServiceType(rs.getString("service_type"));
				item.setQuantity(Integer.parseInt(rs.getString("quantity")));
				item.setTax(Double.parseDouble(rs.getString("tax")));
				item.setSubTotalPrice(Double.parseDouble(rs.getString("sub_total_price")));
				item.setTotalPrice(Double.parseDouble(rs.getString("total_price")));
				item.setCartId(Integer.parseInt(rs.getString("cart_id")));
				list.add(item);
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
		return list;		
	}


	public void addCartItem(CartItem item){
		try {
			String sql= "INSERT into schavan7_cart_items(service_name,service_type,price,quantity,tax,sub_total_price,total_price,cart_id) values('" + 
					item.getServiceName() + "','" + item.getServiceType() + "'," + item.getPrice() + "," + item.getQuantity() + ","
					+item.getTax() + "," + item.getSubTotalPrice() + "," + item.getTotalPrice() + ","	+ item.getCartId() + ")";
			Integer id = executeModifySelectQuery(sql);	
			item.setCartItemId(id);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateCartItem(CartItem item){
		try {
			String sql= "UPDATE schavan7_cart_items SET service_name = '" + item.getServiceName() + "',price='" + item.getPrice() +
					",sub_total_price=" + item.getSubTotalPrice() + "', service_type='" + item.getServiceType() + "' WHERE cart_item_id=" + item.getCartItemId() ;
			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteCartItem(CartItem item){
		try {
			String sql= "DELETE FROM schavan7_cart_items WHERE cart_item_id = " + item.getCartItemId();
			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
