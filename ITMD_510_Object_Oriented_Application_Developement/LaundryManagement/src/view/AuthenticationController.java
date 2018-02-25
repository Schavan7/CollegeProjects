package view;



import java.io.IOException;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Customer;
import model.User;
import model.dao.CustomerDAO;
/**
 * @author shrija Chavan
 *This class will handle all authentication from login page, registration page and logout page.
 */
public class AuthenticationController {
	private Stage primaryStage;
	@FXML 
	private TextField userNameTf;
	@FXML 
	private PasswordField passwordTf;
	@FXML 
	private TextField firstName;
	@FXML 
	private TextField lastName;
	@FXML 
	private TextField phone;
	@FXML 
	private TextField email;
	@FXML 
	private PasswordField password;
	@FXML 
	private Label errorMessage;


	final Pattern emailPattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*@[a-zA-Z][a-zA-Z0-9_]+(\\.[a-zA-Z][a-zA-Z0-9_]*)+");
	final Pattern phonePattern  = Pattern.compile("^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$");

	public void setStage(Stage stage){
		primaryStage = stage;
	}


	/**
	 * @param primaryStage
	 * Launch login scene
	 */
	public void launchLogingController(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			AnchorPane loginScene = (AnchorPane) loader.load();
			Scene parentScene = primaryStage.getScene();
			BorderPane borderPane = (BorderPane)parentScene.getRoot();
			borderPane.setCenter(loginScene);
			primaryStage.setTitle("Login Page");
			primaryStage.setResizable(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event
	 * @throws IOException
	 * Handle login action.
	 * Depends on user role, It will redirect to appropriate scene.
	 */
	@FXML
	private void login(ActionEvent event) throws IOException{
		primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		String erMsg = null;
		if ( (erMsg = validateLoginInput()).length() ==0){
			CustomerDAO dao = new CustomerDAO();
			User user = dao.getCustomer("email", userNameTf.getText());
			// compare with db values with lower case email
			if(user == null ||  !(userNameTf.getText().toLowerCase().equals(user.getEmail().toLowerCase()) && passwordTf.getText().equals(user.getPassword()))){
				errorMessage.setText("Username/password is invalid.");
				return;
			}
			SessionManger.setUser(user);
			if (user.getUserType().equals("AdminUser")){
				AdminDashController dash = new AdminDashController();
				dash.launchAdminDash(primaryStage);
			}else{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ServiceCartView.fxml"));
				AnchorPane personOverview = (AnchorPane) loader.load();
				Scene parentScene = primaryStage.getScene();
				BorderPane borderPane = (BorderPane)parentScene.getRoot();
				borderPane.setCenter(personOverview);		
				ServiceCartController serviceCart = loader.getController();
				serviceCart.launchServiceCart(primaryStage);
			}
		}else{
			errorMessage.setText(erMsg);
		}		
	}

	/**
	 * @param event
	 * @throws IOException
	 * This method will launch registration scene.
	 */
	@FXML
	public void launchRegistration(ActionEvent event) throws IOException{
		try {
			primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegistrationView.fxml"));
			AnchorPane loginScene = (AnchorPane) loader.load();
			Scene parentScene = primaryStage.getScene();
			BorderPane borderPane = (BorderPane)parentScene.getRoot();
			borderPane.setCenter(loginScene);
			primaryStage.setTitle("Registration Page");
			primaryStage.setResizable(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param event
	 * @throws IOException
	 * Handle registration request.
	 * Validates the user input and will create user in DB
	 */
	@FXML
	private void registration(ActionEvent event) throws IOException{
		String eMsg=null;
		try{
			if ((eMsg = validateRegistrionInput()).length() == 0){
				Customer cust  =  new Customer();
				cust.setEmail(email.getText());
				cust.setFirstName(firstName.getText());
				cust.setLastName(lastName.getText());
				cust.setPassword(password.getText());
				cust.setPhone(phone.getText());
				cust.setUserType("Customer");
				CustomerDAO custDao = new CustomerDAO();
				if (custDao.getCustomer("email", cust.getEmail().trim()) == null)
				{
					if (custDao.addCustomer(cust)){
						redirectLogin(event);	
					}else{
						errorMessage.setText("Something went wrog!");
					}

				}else{
					errorMessage.setText("Email already Exist!");
				}			
			}else{
				errorMessage.setText(eMsg);
			}
		}catch(Exception e){
		}
	}

	/**
	 * user inputs validator method
	 * @return, error message if user data is invalid
	 */
	private String validateRegistrionInput() {
		String error = "";
		if (firstName.getText() == null || firstName.getText().trim().length() == 0) {
			error += "First Name Can't be blank! \n"; 
		}
		if (error != null  && (lastName.getText() == null || lastName.getText().trim().length() == 0)) {
			error += "Last Name Can't be blank! \n"; 
		}
		if ( error != null  && (email.getText() == null || email.getText().trim().length() == 0)) {
			error += "Email Can't be blank! \n"; 
		}else if( !emailPattern.matcher(email.getText().trim()).find()){
			error += "Please enter a valid email! \n";
		}	
		if ( error != null  && (password.getText() == null || password.getText().trim().length() == 0)) {
			error += "Password Can't be blank!. \n"; 
		}else if( password.getText().trim().length() <= 5){
			error += "Password length must be greater than 5 \n";
		}
		// compare with phone regx
		if (error != null  && (phone.getText() == null || phone.getText().trim().length() == 0)) {
			error += "Phone Can't be blank!" ; 
		}else if( !phonePattern.matcher(phone.getText().trim()).find()){
			error += "Please enter a valid phone! \n";
		}
		return error;
	}

	/**
	 * Validate login input fields
	 * @return,error message if user data is invalid
	 */
	private String validateLoginInput() {
		String error = "";
		if (userNameTf.getText() == null || userNameTf.getText().length() == 0) {
			error = "User Name Can't be blank! \n"; 
		}
		if (passwordTf.getText() == null || passwordTf.getText().length() == 0) {
			error += "Password Can't be blank! \n"; 
		}
		return error;
	}

	@FXML
	public void redirectLogin(ActionEvent event) throws IOException{
		primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		logout(primaryStage);
	}

	public void logout(Stage primaryStage){
		SessionManger.setUser(null);
		launchLogingController(primaryStage);
	}
}


