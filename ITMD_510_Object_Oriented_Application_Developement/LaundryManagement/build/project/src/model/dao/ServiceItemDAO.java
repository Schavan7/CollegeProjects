package model.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import model.ServiceItem;

public class ServiceItemDAO  extends Dao{

	public ServiceItem getServiceItem(String id){
		ServiceItem item =  new ServiceItem();
		try {
			String sql= "select * from schavan7_service_items where service_item_id='" + id + "'";
			rs = executeFetchQuery (sql);			

			if (rs.next()){	
				item.setServiceItemId(Integer.parseInt(rs.getString("service_item_id")));
				item.setServiceName(rs.getString("service_name"));
				item.setPrice(rs.getDouble("price"));
				item.setServiceType(rs.getString("service_type"));

			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		return item;
	}

	public ArrayList<ServiceItem> getAllServiceItems(){	

		ArrayList <ServiceItem> list = new ArrayList<ServiceItem>();
		try {
			String sql= "select * from schavan7_service_items" ;
			rs = executeFetchQuery (sql);			
			while (rs.next()){	
				ServiceItem item =  new ServiceItem();
				item.setServiceItemId(Integer.parseInt(rs.getString("service_item_id")));
				item.setServiceName(rs.getString("service_name"));
				item.setPrice(rs.getDouble("price"));
				item.setServiceType(rs.getString("service_type"));
				list.add(item);
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
		return list;		
	}


	public void addServiceItem(ServiceItem item){
		try {
			String sql= "INSERT into schavan7_service_items(service_name,service_type,price) values('" + 
					item.getServiceName() + "','" + item.getServiceType() + "'," + item.getPrice() + ")";
			executeModifySelectQuery(sql);			

		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateServiceItem(ServiceItem item){
		try {
			String sql= "UPDATE schavan7_service_items SET service_name = '" + item.getServiceName() + "',price=" + item.getPrice() +
					", service_type='" + item.getServiceType() + "' WHERE service_item_id=" + item.getServiceItemId() ;
			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteServiceItem(ServiceItem item){
		try {
			String sql= "DELETE FROM schavan7_service_items WHERE service_item_id = " + item.getServiceItemId();
			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}


	public ArrayList<String> getServiceTypes(){
		ArrayList <String> list = new ArrayList<String>();
		try {
			String sql= "select DISTINCT(service_type)  from schavan7_service_items;" ;
			rs = executeFetchQuery (sql);			
			while (rs.next()){	
				list.add(rs.getString("service_type"));
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
		return list;

	}

}
