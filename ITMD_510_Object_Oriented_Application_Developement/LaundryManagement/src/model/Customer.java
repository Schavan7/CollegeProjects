package model;

import model.dao.CartDAO;

public class Customer extends User {
	
public Customer(){}



public void createCustomer()
{
	
	
}

public void updateCustomer()
{
	
}

public void editCustomer()
{
	
}

public void deleteCustomer()
{
	
}


public Cart getCart(){
	return new CartDAO().getUserCart(this.getUserId());
	
}


public void updateMembership(){
	
}
	
public void enrollMembership(){
	
}

}
