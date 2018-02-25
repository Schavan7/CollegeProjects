package view;


import javafx.fxml.FXML;
/**
 * This class will handle all the process which will add, delete or edit a service item by the Admin.
 */
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.ServiceItem;
import model.dao.ServiceItemDAO;

public class AdminServiceItemController {
	@FXML
	private TableView<ServiceItem> serviceItemTable;

	@FXML
	private TableColumn<ServiceItem, String> serviceNameColumn;
	@FXML
	private TableColumn<ServiceItem, Double> priceColumn;

	@FXML
	private Label serviceNameLabel;
	@FXML
	private Label serviceTypeLabel;
	@FXML
	private Label priceLabel;

	private AdminServiceItemConfigController serviceConfigController;

	/**
	 * Initiliaze the table View and table columns.
	 */
	@FXML
	private void initialize() {
		serviceNameColumn.setCellValueFactory(cellData -> cellData.getValue().serviceNameProperty());
		priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
		showServiceItemDetails(null);
		serviceItemTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showServiceItemDetails(newValue));
	}


	/**
	 * @param serviceConfigController
	 * set up instance of parent controller of service item configuration.
	 */
	public void setParentControoller(AdminServiceItemConfigController serviceConfigController) {
		this.serviceConfigController = serviceConfigController;
		serviceItemTable.setItems(serviceConfigController.getServiceItemData());
	}

	/**
	 * @param serviceItem
	 * This method shows selected service item in show view.
	 */
	private void showServiceItemDetails(ServiceItem serviceItem) {
		if (serviceItem != null) {
			serviceNameLabel.setText(serviceItem.getServiceName());
			serviceTypeLabel.setText(serviceItem.getServiceType());
			priceLabel.setText(Double.toString(serviceItem.getPrice()));

		} else {
			serviceNameLabel.setText("");
			serviceTypeLabel.setText("");
			priceLabel.setText("");
		}
	}	

	/**
	 * this method handel delete action and update the table view and DB.
	 */
	@FXML
	private void handleDeleteServiceItem() {
		int selectedIndex = serviceItemTable.getSelectionModel().getSelectedIndex();
		ServiceItem selectedService = serviceItemTable.getSelectionModel().getSelectedItem();
		if (selectedIndex >= 0) {
			ServiceItemDAO dao =  new ServiceItemDAO();
			dao.deleteServiceItem(selectedService);
			serviceItemTable.getItems().remove(selectedIndex);
		}else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(serviceConfigController.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Service Selected");
			alert.setContentText("Please select a Service in the table.");
			alert.showAndWait();
		}
	}

	/**
	 * Handle create new request for service item.
	 */
	@FXML
	private void handleNewServiceItem() {
		ServiceItem service = new ServiceItem();
		boolean okClicked = serviceConfigController.showServiceItemEditDialog(service);
		if (okClicked) {
			ServiceItemDAO dao =  new ServiceItemDAO();
			dao.addServiceItem(service);
			serviceConfigController.getServiceItemData().add(service);
		}
	}

	/*
	 * Handle Edit service request and update the DB.
	 */
	@FXML
	private void handleEditServiceItem() {
		ServiceItem selectedService = serviceItemTable.getSelectionModel().getSelectedItem();
		if (selectedService != null) {
			boolean okClicked = serviceConfigController.showServiceItemEditDialog(selectedService);
			if (okClicked) {
				ServiceItemDAO dao =  new ServiceItemDAO();
				dao.updateServiceItem(selectedService);
				showServiceItemDetails(selectedService);
				serviceItemTable.refresh();
			}

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(serviceConfigController.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Service Selected");
			alert.setContentText("Please select a Service in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void redirectHome() {
		new AdminDashController().launchAdminDash(serviceConfigController.getPrimaryStage());
	}

	@FXML
	private void redirectlogin() {
		new AuthenticationController().logout(serviceConfigController.getPrimaryStage());
	}


}
