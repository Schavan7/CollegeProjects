package model.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import model.Address;

public class AddressDAO  extends Dao{

	public Address getAddress(Integer addressableId, String addressableType){
		Address address = null; 
		try {
			String sql= "select * from schavan7_addresses where addressable_Id = " + addressableId + " and addressable_type = '" + addressableType +"'";
			rs = executeFetchQuery(sql);			

			if (rs.next()){	
				address = new Address();
				address.setAddressId(Integer.parseInt(rs.getString("address_id")));
				address.setAddressline1(rs.getString("address_line1"));
				address.setAddressline2(rs.getString("address_line2"));
				address.setState(rs.getString("city"));
				address.setState(rs.getString("state"));	
				address.setState(rs.getString("zip_code"));
				address.setPhone(rs.getString("phone"));
				address.setAddressableId(Integer.parseInt(rs.getString("addressable_id")));
				address.setAddressableType(rs.getString("addressable_type"));
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		return address;
	}

	public ArrayList<Address> getAllAddresses(){	
		Address address = null;
		ArrayList <Address> list = new ArrayList<Address>();
		try {
			String sql= "select * from schavan7_addresses" ;
			rs = executeFetchQuery (sql);			

			while (rs.next()){	
				address =  new Address();
				address.setAddressId(Integer.parseInt(rs.getString("address_id")));
				address.setAddressline1(rs.getString("address_line1"));
				address.setAddressline2(rs.getString("address_line2"));
				address.setState(rs.getString("city"));
				address.setState(rs.getString("state"));	
				address.setState(rs.getString("zip_code"));
				address.setPhone(rs.getString("phone"));
				address.setAddressableId(Integer.parseInt(rs.getString("addressable_id")));
				address.setAddressableType(rs.getString("addressable_type"));
				list.add(address);
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
		return list;		
	}


	public void addAddress(Address address){
		try {
			String sql= "INSERT into schavan7_addresses(full_name,address_line1,address_line2,city,state,zip_code,phone,addressable_id,addressable_type) values('" +
					address.getFullName() + "','" +	 address.getAddressline1() + "','" + address.getAddressline2() + "','" + address.getCity() +
					"','" +	address.getState() + "','" + address.getZip() + "','" + address.getPhone() + "'," + address.getAddressableId() + ",'" + address.getAddressableType()  + "')";
			executeModifySelectQuery(sql);			

		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateAddress(Address address){
		try {
			String sql= "UPDATE schavan7_addresses SET full_name = '" + address.getFullName() + "', address_line1 = '" + address.getAddressline1() + "',address_line2='" + address.getAddressline2() +
					"', zip_code='" + address.getZip() + "', city='"+ address.getCity() + "',phone='" + address.getPhone() + "', state='"  + address.getState() + "'";
			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteAddress(Address address){
		try {
			String sql= "DELETE FROM schavan7_addresses WHERE address_id = " + address.getAddressId();
			executeModifySelectQuery(sql);					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
