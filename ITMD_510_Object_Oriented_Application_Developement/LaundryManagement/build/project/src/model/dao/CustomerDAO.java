package model.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import model.Customer;

public class CustomerDAO  extends Dao{

	public Customer getCustomer(String columnName, String value){
		Customer cust = null;  
		try {
			String sql= "select * from schavan7_users where " + columnName +  "='" + value + "'";
			rs = executeFetchQuery (sql);			
			if (rs.next()){	
				cust = new Customer();
				cust.setUserId(Integer.parseInt(rs.getString("user_id")));
				cust.setEmail(rs.getString("email"));
				cust.setFirstName(rs.getString("first_name"));
				cust.setLastName(rs.getString("last_name"));
				cust.setPassword(rs.getString("password"));
				cust.setPhone(rs.getString("phone"));
				cust.setUserType(rs.getString("user_type"));

			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return cust;
	}

	public ArrayList<Customer> getAllCustomers(){	
		ArrayList <Customer> list = new ArrayList<Customer>();
		try {
			String sql= "select * from schavan7_users where user_type='Customer'" ;
			rs = executeFetchQuery (sql);			

			while (rs.next()){	
				Customer cust =  new Customer();
				cust.setUserId(Integer.parseInt(rs.getString("user_id")));
				cust.setEmail(rs.getString("email"));
				cust.setFirstName(rs.getString("first_name"));
				cust.setLastName(rs.getString("last_name"));
				cust.setPassword(rs.getString("password"));
				cust.setPhone(rs.getString("phone"));
				list.add(cust);
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
		return list;		
	}


	public boolean addCustomer(Customer cust){
		try {
			String sql= "INSERT INTO schavan7_users(email,first_name,last_name,password,phone,user_type) values('" +
					cust.getEmail() + "','" + cust.getFirstName() + "','" + cust.getLastName() + "','" +
					cust.getPassword() + "','" + cust.getPhone() + "','" + cust.getUserType() + "')";

			executeModifySelectQuery(sql);				
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return true;
	}

	public void updateCustomer(Customer cust){
		try {
			String sql= "UPDATE schavan7_users SET firstName = '" + cust.getFirstName() + "',lastName='" + cust.getLastName() +
					"', email='" + cust.getEmail() + "', password= '"+ cust.getPassword() + "',phone='" + cust.getPhone() + "'";
			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void deleteCustomer(Customer cust){
		try {
			String sql= "DELETE FROM schavan7_users WHERE user_id = " + cust.getUserId();
			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
