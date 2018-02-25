package view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ServiceItem;
import model.dao.ServiceItemDAO;
/**
 * This class will handle all the process which will add, delete or edit a service item by the Admin.
 */

public class AdminServiceItemConfigController {
	@FXML
	private Stage primaryStage;
	@FXML
	private ObservableList<ServiceItem> serviceItemData = FXCollections.observableArrayList();


	/**
	 * @param primaryStage
	 * @throws IOException
	 * Show service item.
	 */
	public void showServiceItems(Stage primaryStage) throws IOException{
		this.primaryStage =  primaryStage;
		serviceItemData = FXCollections.observableArrayList(new ServiceItemDAO().getAllServiceItems());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ServiceItemsView.fxml"));
		AnchorPane loginScene = (AnchorPane) loader.load();
		Scene parentScene = this.primaryStage.getScene();
		BorderPane borderPane = (BorderPane)parentScene.getRoot();
		borderPane.setCenter(loginScene);
		AdminServiceItemController controller = loader.getController();
		controller.setParentControoller(this);
	}


	/**
	 * @param serviceItem
	 * @return
	 * show service item edit dialog box.
	 */
	public boolean showServiceItemEditDialog(ServiceItem serviceItem) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AdminServiceItemConfigController.class.getResource("/view/ServiceEditView.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Service");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AdminServiceEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setServiceItem(serviceItem);

			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}



	public ObservableList<ServiceItem> getServiceItemData() {
		return serviceItemData;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

}
