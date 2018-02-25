package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ServiceItem;
/**
 * This class will handle all the process which will edit a service item by the Admin.
 */

public class AdminServiceEditController {
	@FXML
	private TextField serviceNameField;
	@FXML
	private ComboBox<String> serviceTypeCombo;
	@FXML
	private TextField priceField;

	private Stage dialogStage;
	private ServiceItem serviceItem;
	private boolean okClicked = false;

	
	@FXML
	private void initialize() {
		ObservableList<String> options = FXCollections.observableArrayList("washing","Dry Cleaning");
		serviceTypeCombo.setItems(options);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}


	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;

		serviceNameField.setText(serviceItem.getServiceName());
		serviceTypeCombo.setValue(serviceItem.getServiceType());
		priceField.setText(Double.toString(serviceItem.getPrice()));
	}
	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			serviceItem.setServiceName(serviceNameField.getText());
			serviceItem.setServiceType(serviceTypeCombo.getValue());
			serviceItem.setPrice(Double.parseDouble(priceField.getText()));
			okClicked = true;
			dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (serviceNameField.getText() == null || serviceNameField.getText().length() == 0) {
			errorMessage += "Invalid Service Name!\n"; 
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}


}
