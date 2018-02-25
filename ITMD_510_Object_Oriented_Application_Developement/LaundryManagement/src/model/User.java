package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	
	protected  IntegerProperty userId=new SimpleIntegerProperty(1);
	protected  StringProperty firstName;
	protected  StringProperty lastName;
	protected  StringProperty password;
	protected  StringProperty email;
	protected  StringProperty phone;
	protected  StringProperty userType;
	

	public Integer getUserId() {
		return userId.get();
	}

	public void setUserId(Integer userId) {
		this.userId.set(userId);
	}

	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName = new SimpleStringProperty(firstName);
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName= new SimpleStringProperty(lastName);
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password = new SimpleStringProperty(password);
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email = new SimpleStringProperty(email);
	}

	public String getPhone() {
		return phone.get();
	}

	public void setPhone(String phone) {
		this.phone = new SimpleStringProperty(phone);
	}

	public String getUserType() {
		return userType.get();
	}

	public void setUserType(String userType) {
		this.userType = new SimpleStringProperty(userType);
	}
	

}
