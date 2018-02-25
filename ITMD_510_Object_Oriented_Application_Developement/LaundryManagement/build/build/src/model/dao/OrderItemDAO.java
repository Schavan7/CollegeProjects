package model.dao;


import java.sql.SQLException;
import java.util.ArrayList;

import model.OrderItem;

public class OrderItemDAO  extends Dao{

	public OrderItem getOrderItem(String id){
		OrderItem item =  new OrderItem();
		try {
			String sql= "select * from schavan7_order_items where  order_item_id='" + id + "'";
			rs = executeFetchQuery (sql);			

			if (rs.next()){	
				item.setOrderItemId(Integer.parseInt(rs.getString("order_item_id")));
				item.setOrderId(Integer.parseInt(rs.getString("order_id")));
				item.setServiceName(rs.getString("service_name"));
				item.setPrice(rs.getDouble("price"));
				item.setServiceType(rs.getString("service_type"));
				item.setQuantity(Integer.parseInt(rs.getString("quantity")));
				item.setTax(rs.getDouble("tax"));
				item.setSubTotalPrice(rs.getDouble("sub_total_price"));
				item.setTotalPrice(rs.getDouble("total_price"));
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		return item;
	}

	public ArrayList<OrderItem> getAllOrderItems(){	
		OrderItem item =  new OrderItem();
		ArrayList <OrderItem> list = new ArrayList<OrderItem>();
		try {
			String sql= "select * from schavan7_order_items" ;
			rs = executeFetchQuery (sql);			

			while (rs.next()){	
				item.setOrderItemId(Integer.parseInt(rs.getString("order_item_id")));
				item.setOrderId(Integer.parseInt(rs.getString("order_id")));
				item.setServiceName(rs.getString("service_name"));
				item.setPrice(rs.getDouble("price"));
				item.setServiceType(rs.getString("service_type"));
				item.setQuantity(Integer.parseInt(rs.getString("quantity")));
				item.setTax(rs.getDouble("tax"));
				item.setSubTotalPrice(rs.getDouble("sub_total_price"));
				item.setTotalPrice(rs.getDouble("total_price"));
				list.add(item);
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
		return list;		
	}


	public void addOrderItem(OrderItem item){
		try {
			String sql= "INSERT into schavan7_order_items(service_name,service_type,price,quantity,tax,sub_total_price,total_price,order_id)  "
					+ "values('" + item.getServiceName() + "','"+ item.getServiceType() +"',"+ item.getPrice() +
					"," + item.getQuantity() + ","+ item.getTax() + "," + item.getSubTotalPrice() + "," + item.getTotalPrice() + "," + item.getOrderId() + ")";
			executeModifySelectQuery(sql);			

		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateOrderItem(OrderItem item){
		try {
			String sql= "UPDATE schavan7_order_items SET service_name = '" + item.getServiceName() + "',price='" + item.getPrice() +
					"', service_type='" + item.getServiceType() + "'" + ",quantity =" + item.getQuantity() +
					",tax=" + item.getTax()	;
			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteOrderItem(OrderItem item){
		try {
			String sql= "DELETE FROM schavan7_order_items WHERE order_item_id = " + item.getOrderItemId();
			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
