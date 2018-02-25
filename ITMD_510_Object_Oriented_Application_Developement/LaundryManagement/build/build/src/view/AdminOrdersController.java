package view;

import java.io.IOException;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Order;
import model.dao.OrderDAO;
/**
 * @author shrija Chavan
 * This class will show all the orders placed by customer to user.
 */
public class AdminOrdersController {

	@FXML
	private Stage primaryStage;

	@FXML
	private TableView<Order> ordersTable;

	@FXML
	private TableColumn<Order, Integer> id;
	@FXML
	private TableColumn<Order, Integer> userId;
	@FXML
	private TableColumn<Order, String> status;
	@FXML
	private TableColumn<Order, Double> total;
	@FXML
	private TableColumn<Order, Date> orderPickupDate;
	@FXML
	private TableColumn<Order, Date> orderCreatedDate;

	@FXML
	private ObservableList<Order> ordersData = FXCollections.observableArrayList();

	/**
	 * Initialize the orders table view.
	 */
	@FXML
	private void initialize() {
		userId.setCellValueFactory(new PropertyValueFactory<Order,Integer>("userId"));
		status.setCellValueFactory(new PropertyValueFactory<Order,String>("status"));
		total.setCellValueFactory(new PropertyValueFactory<Order,Double>("total"));
		orderPickupDate.setCellValueFactory(new PropertyValueFactory<Order,Date>("orderPickupDate"));
		orderCreatedDate.setCellValueFactory(new PropertyValueFactory<Order,Date>("orderCreatedDate"));
		this.ordersTable.setItems(null);
	}


	/**
	 * @param primaryStage
	 * @throws IOException
	 * Sets show order scene.
	 */
	@FXML
	public void showOrders(Stage primaryStage) throws IOException{
		this.primaryStage =  primaryStage;
		ordersData = FXCollections.observableArrayList(new OrderDAO().getAllOrders());
		ordersTable.setItems(ordersData);

	}

	@FXML
	private void redirectHome() {
		new AdminDashController().launchAdminDash(primaryStage);
	}

	@FXML
	private void redirectlogin() {
		new AuthenticationController().logout(primaryStage);
	}


}
