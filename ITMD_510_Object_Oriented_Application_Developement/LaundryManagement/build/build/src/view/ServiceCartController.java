package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.StringUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Cart;
import model.CartItem;
import model.Customer;
import model.ServiceItem;
import model.dao.CartDAO;
import model.dao.CartItemDAO;
import model.dao.ServiceItemDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ServiceCartController {
	@FXML
	private Stage primaryStage;

	@FXML
	private ComboBox<ServiceItem> serviceItemsCombo;

	@FXML
	private ComboBox<String> serviceTypeCombo;

	@FXML
	private TextField quantity;
	@FXML
	private Label serviceItemLabel;
	@FXML
	private Label serviceTypeLabel;
	@FXML
	private Label quantityLabel;
	@FXML
	private Label priceLabel;
	@FXML
	private Label taxLabel;
	@FXML
	private Label subTotalPriceLabel;
	@FXML
	private Label totalPriceLabel;
	@FXML
	private Button addToCartBtn;


	@FXML
	private TableView<CartItem> cartItemTable;
	@FXML
	private TableColumn<CartItem, String> serviceNameColumn;
	@FXML
	private TableColumn<CartItem, String> serviceTypeColumn;
	@FXML
	private TableColumn<CartItem, Double> priceColumn;
	@FXML
	private TableColumn<CartItem, Integer> quantityColumn;
	@FXML
	private TableColumn<CartItem, Double> subTotalColumn;
	@FXML
	private TableColumn<CartItem, Double> totalPriceColumn;
	@FXML 
	private Label cartErrorLabel;
	@FXML 
	private Label addToCartErrorLabel;

	@FXML
	private ObservableList<CartItem> cartItemData = FXCollections.observableArrayList();
	List<Map<String , String>> serviceCart  = new ArrayList<Map<String,String>>();


	@FXML
	private void initialize() {
		initializeListner();
		serviceNameColumn.setCellValueFactory(new PropertyValueFactory<CartItem,String>("serviceName"));
		serviceTypeColumn.setCellValueFactory(new PropertyValueFactory<CartItem,String>("serviceType"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<CartItem,Double>("price"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<CartItem,Integer>("quantity"));
		subTotalColumn.setCellValueFactory(new PropertyValueFactory<CartItem,Double>("subTotalPrice"));
		totalPriceColumn.setCellValueFactory(new PropertyValueFactory<CartItem,Double>("totalPrice"));

		cartItemTable.setItems(cartItemData);

	}
	private void initializeListner(){
		quantity.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				try {Integer.parseInt(newValue);}
				catch (NumberFormatException e) {
					addToCartErrorLabel.setText("Please add valid " + quantityLabel.getText());
					return;
				}
				addToCartErrorLabel.setText("");
				updatePriceLabels();
			}
		});
	}


	@FXML
	public void launchServiceCart(Stage primaryStage) {	
		this.primaryStage = primaryStage;
		try {
			ServiceItemDAO dao = new ServiceItemDAO();
			serviceTypeCombo.setItems(FXCollections.observableArrayList(dao.getServiceTypes()));
			Cart cart = ((Customer) SessionManger.getUser()).getCart();
			if(cart !=null){
				cartItemData = FXCollections.observableArrayList(new CartItemDAO().getAllCartItems(cart));
				this.cartItemTable.setItems(cartItemData);		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleServiceTypeCombo(){
		if (serviceTypeCombo.getValue().equals("washing")){
			serviceItemsCombo.setVisible(false);
			serviceItemLabel.setVisible(false);
			priceLabel.setText("10.0");
			quantityLabel.setText("Weight");
			serviceItemsCombo.setItems(null);
			quantity.setPromptText("pounds");			
		}else{
			serviceItemsCombo.setVisible(true);
			serviceItemLabel.setVisible(true);
			priceLabel.setText("0.0");
			quantityLabel.setText("Quantity");
			ServiceItemDAO dao = new ServiceItemDAO();
			serviceItemsCombo.setItems(FXCollections.observableArrayList(dao.getAllServiceItems()));
		}
		quantity.setText("");
		totalPriceLabel.setText("0.0");
		taxLabel.setText("0.0");
		subTotalPriceLabel.setText("0.0");

	}


	@FXML
	public void handleServiceItemsCombo(){
		if (!serviceTypeCombo.getValue().equals("washing")){
			priceLabel.setText(serviceItemsCombo.getSelectionModel().getSelectedItem().getPrice().toString());
			updatePriceLabels();
		}
	}

	private void updatePriceLabels(){
		if (!StringUtils.isNullOrEmpty(quantity.getText())){
			Double price;
			if (serviceTypeCombo.getValue().equals("washing")){
				price = 10.0;
			}else{
				price =  serviceItemsCombo.getSelectionModel().getSelectedItem().getPrice();		
			}
			Double subTotal = price * Integer.parseInt(quantity.getText());
			Double tax = (subTotal * 0.05);
			Double totalPrice = subTotal +  tax;
			taxLabel.setText(tax.toString());
			subTotalPriceLabel.setText(subTotal.toString());
			totalPriceLabel.setText(totalPrice.toString());
		}
	}


	@FXML
	private void addToCart(){	
		if(!quantity.getText().equals("")){
			CartItemDAO dao = new CartItemDAO();
			CartDAO cartDao = new CartDAO();
			Cart cart = ((Customer) SessionManger.getUser()).getCart();
			if (cart == null){
				cart = new Cart();
				cart.setUserId(SessionManger.getUser().getUserId());
				cartDao.addCart(cart);
			}

			CartItem cartItem = new  CartItem();
			if(serviceItemsCombo.getValue() == null) {
				cartItem.setServiceName("Washing Service");
			}else{
				cartItem.setServiceName(serviceItemsCombo.getValue().toString());
			}
			cartItem.setServiceType(serviceTypeCombo.getValue());
			cartItem.setQuantity(Integer.parseInt(quantity.getText()));
			cartItem.setPrice(Double.parseDouble(priceLabel.getText()));
			cartItem.setTax(Double.parseDouble(taxLabel.getText()));
			cartItem.setSubTotalPrice(Double.parseDouble(subTotalPriceLabel.getText()));
			cartItem.setTotalPrice(Double.parseDouble(totalPriceLabel.getText()));
			cartItem.setCartId(cart.getCartId());
			dao.addCartItem(cartItem);
			cartItemData.add(cartItem);

			cart.updateTax();
			cart.updateTotal();
			cartDao.updateCart(cart);
			cartErrorLabel.setText("");
		}else{
			addToCartErrorLabel.setText("Please add valid " + quantityLabel.getText());
		}


	}


	@FXML
	private void handleDeleteCartItem(ActionEvent event) throws IOException {
		primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		int selectedIndex = cartItemTable.getSelectionModel().getSelectedIndex();
		CartItem cartItem = cartItemTable.getSelectionModel().getSelectedItem();
		Cart cart = ((Customer) SessionManger.getUser()).getCart();
		if (selectedIndex >= 0) {
			CartItemDAO dao =  new CartItemDAO();  	
			dao.deleteCartItem(cartItem);
			cartItemTable.getItems().remove(selectedIndex);
			cart.updateTax();
			cart.updateTotal();
			new CartDAO().updateCart(cart);
		}else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(primaryStage);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Item Selected");
			alert.setContentText("Please select a Item in the table.");
			alert.showAndWait();
		}
	}

	@FXML
	private void goToCheckout(ActionEvent event) throws IOException{	
		if (this.cartItemTable.getItems().size() == 0){
			cartErrorLabel.setText( "Please add Service item into cart.");
		}else{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CheckoutView.fxml"));	
			AnchorPane personOverview = (AnchorPane) loader.load();
			Scene parentScene = primaryStage.getScene();
			BorderPane borderPane = (BorderPane)parentScene.getRoot();
			borderPane.setCenter(personOverview);	
			CheckoutController cehckout = loader.getController();
			cehckout.launchCheckout(primaryStage);
		}
	}


	@FXML
	private void redirectlogin(ActionEvent event) throws IOException{
		primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		new AuthenticationController().logout(primaryStage);
	}


}
