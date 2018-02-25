package view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.dao.CustomerDAO;
/**
 * @author shrija chavan
 *This class will show all the enrolled(Registered)customer.
 */
public class AdminCustomersController {
	@FXML
	private Stage primaryStage;

	@FXML
	private TableView<Customer> customersTable;

	@FXML
	private TableColumn<Customer, Integer> id;
	@FXML
	private TableColumn<Customer, String> firstName;
	@FXML
	private TableColumn<Customer, String> lastName;
	@FXML
	private TableColumn<Customer, String> email;
	@FXML
	private TableColumn<Customer, String> phone;

	@FXML
	private ObservableList<Customer> customersData = FXCollections.observableArrayList();

	/**
	 * Initialize the customers table view.
	 */
	@FXML
	private void initialize() {
		id.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("userId"));
		firstName.setCellValueFactory(new PropertyValueFactory<Customer,String>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<Customer,String>("lastName"));
		email.setCellValueFactory(new PropertyValueFactory<Customer,String>("email"));
		phone.setCellValueFactory(new PropertyValueFactory<Customer,String>("phone"));
		this.customersTable.setItems(null);
	}


	/**
	 * @param primaryStage
	 * @throws IOException
	 * launch customer table view scene
	 */
	@FXML
	public void showCustomers(Stage primaryStage) throws IOException{
		this.primaryStage =  primaryStage;
		customersData = FXCollections.observableArrayList(new CustomerDAO().getAllCustomers());
		this.customersTable.setItems(customersData);

	}

	/**
	 * redirect to Home page
	 */
	@FXML
	private void redirectHome() {
		new AdminDashController().launchAdminDash(primaryStage);
	}

	/**
	 * redirect to login page
	 */
	@FXML
	private void redirectlogin() {
		new AuthenticationController().logout(primaryStage);

	}


}
