package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * @author shrija chavan
 * This class will display the Admin Dashboard.
 *
 */
public class AdminDashController {
	@FXML
	private Stage primaryStage;
	@FXML
	private Button serviceConfigBtn;
	@FXML
	private Button ordersBtn;
	@FXML
	private Button customersBtn;

/**
 * @param primaryStage
 * This method will launch Admin dash board. 
 * 
 */
	public void launchAdminDash(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AdminDashController.class.getResource("/view/AdminDashView.fxml"));
			AnchorPane adminDash = (AnchorPane) loader.load();
			Scene parentScene = this.primaryStage.getScene();
			BorderPane borderPane = (BorderPane)parentScene.getRoot();
			AdminDashController cehckout = loader.getController();
			borderPane.setCenter(adminDash);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


/**
 * @param event
 * @throws IOException
 * This method will launch service item configuration page.
 */
	@FXML
	private void launchServiceConfig(ActionEvent event) throws  IOException{
		primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		new AdminServiceItemConfigController().showServiceItems(primaryStage);
	}
	
	
/**
 * @param event
 * @throws IOException
 * This method will launch customer list page.
 */
	@FXML
	private void launchCustomerView(ActionEvent event) throws  IOException{
		primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminCustomersView.fxml"));
		AnchorPane loginScene = (AnchorPane) loader.load();
		Scene parentScene = this.primaryStage.getScene();
		BorderPane borderPane = (BorderPane)parentScene.getRoot();
		borderPane.setCenter(loginScene);
		AdminCustomersController controller = loader.getController();
		controller.showCustomers(primaryStage);
	}
	/**
	 * 
	 * @param event
	 * @throws IOException
	 * This method will launch Orders list page
	 */
	@FXML
	private void launchOrdersView(ActionEvent event) throws  IOException{
		primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminOrdersView.fxml"));
		AnchorPane loginScene = (AnchorPane) loader.load();
		Scene parentScene = this.primaryStage.getScene();
		BorderPane borderPane = (BorderPane)parentScene.getRoot();
		borderPane.setCenter(loginScene);
		AdminOrdersController controller = loader.getController();
		controller.showOrders(primaryStage);
	}

	/**
	 * @param event
	 * @throws IOException
	 * this method terminate the session and redirect to login page
	 */
	@FXML
	private void redirectlogin(ActionEvent event) throws IOException{
		primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		new AuthenticationController().logout(primaryStage);
	}

}
