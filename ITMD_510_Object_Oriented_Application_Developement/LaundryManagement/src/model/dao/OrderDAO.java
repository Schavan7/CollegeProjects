package model.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import model.Order;

public class OrderDAO  extends Dao{

	public Order getServiceItem(String id){
		Order order =  new Order();
		try {
			String sql= "select * from schavan7_orders where order_id ='" + id + "'";
			rs = executeFetchQuery (sql);			

			if (rs.next()){	
				order.setOrderId(Integer.parseInt(rs.getString("order_id")));
				order.setUserId(Integer.parseInt(rs.getString("user_id")));
				order.setStatus(rs.getString("status"));
				order.setTax(rs.getDouble("tax"));
				order.setTotal(rs.getDouble("total"));
				order.setOrderPickupDate(rs.getDate("order_pickup_date"));
				order.setOrderCreatedDate(rs.getDate("created"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	public ArrayList<Order> getAllOrders(){	
		Order order =  new Order();
		ArrayList <Order> list = new ArrayList<Order>();
		try {
			String sql= "select * from schavan7_orders" ;
			rs = executeFetchQuery (sql);			

			while (rs.next()){	
				order.setOrderId(Integer.parseInt(rs.getString("order_id")));
				order.setUserId(Integer.parseInt(rs.getString("user_id")));
				order.setStatus(rs.getString("status"));
				order.setTax(rs.getDouble("tax"));
				order.setTotal(rs.getDouble("total"));
				order.setOrderPickupDate(rs.getDate("order_pickup_date"));
				order.setOrderCreatedDate(rs.getDate("created"));
				list.add(order);
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
		return list;		
	}


	public void addOrder(Order order){
		Integer orderId= null;
		try {
			String sql= "INSERT into schavan7_orders(total,status,tax,order_pickup_date,user_id)  "
					+ "values(" + order.getTotal() +  ",'"+ 
					order.getStatus() + "'," + order.getTax() + ",'" + new java.sql.Date(order.getOrderPickupDate().getTime()) + "'," + order.getUserId() + ")";

			orderId =  executeModifySelectQuery(sql);
			order.setOrderId(orderId);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}


	}

	public void updateOrder(Order order){
		try {
			String sql= "UPDATE schavan7_orders SET total = " + order.getTotal()  ;

			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteOrder(Order order){
		try {
			String sql= "DELETE FROM schavan7_orders WHERE order_id = " + order.getOrderId();
			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
