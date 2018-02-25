package view;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Address;
import model.Cart;
import model.CartItem;
import model.Customer;
import model.Order;
import model.OrderItem;
import model.dao.AddressDAO;
import model.dao.CartDAO;
import model.dao.OrderDAO;
import model.dao.OrderItemDAO;

/**
 * @author shrija chavan
 *This class will handle checkout process.
 *validate address create order from cart.
 */
public class CheckoutController {
	@FXML
	private Stage primaryStage;

	@FXML
	private TextField fullName;
	@FXML
	private TextField addressLine1;
	@FXML
	private TextField addressLine2;
	@FXML
	private TextField city;
	@FXML
	private TextField zipCode;
	@FXML
	private ComboBox<String> state;
	@FXML
	private TextField phone;
	@FXML
	private DatePicker orderPickupDate;
	@FXML 
	private Label errorMessage;

	final Pattern phonePattern  = Pattern.compile("^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$");
	final Pattern zipPattern = Pattern.compile("[0-9]{5}");

	/**
	 * @param primaryStage
	 * launch checkout scene in that user can pickup/Delivery address.
	 */
	@FXML
	public void launchCheckout(Stage primaryStage) {	
		this.primaryStage = primaryStage;
		try {
			ObservableList<String> options = FXCollections.observableArrayList("IL","NY");
			state.setItems(options);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event
	 * @throws IOException
	 * this method handles the place order request, validate the address input and create the order from cart.
	 */
	@FXML
	private void placeOrder(ActionEvent event) throws IOException{
		primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		String errorMsg = "";
		if ((errorMsg = addressInputValid()).length() ==0){
			Cart cart = ((Customer) SessionManger.getUser()).getCart();
			AddressDAO addrDao =  new AddressDAO();
			Address address = new Address();
			address.setFullName(fullName.getText());
			address.setAddressline1(addressLine1.getText());
			address.setAddressline2(addressLine2.getText());
			address.setCity(city.getText());
			address.setState(state.getValue());
			address.setZip(zipCode.getText());
			address.setPhone(phone.getText());
			address.setAddressableId(SessionManger.getUser().getUserId());
			address.setAddressableType("order");
			cart.setOrderPickupDate(Date.from(orderPickupDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

			Order order = new Order();
			saveOrderFromCart(cart,order);

			address.setAddressableId(order.getOrderId());
			addrDao.addAddress(address);

			CartDAO cartDao = new CartDAO();
			cartDao.deleteCart(cart);

			launchOrderConfirmation();
		}else {
			errorMessage.setText(errorMsg);
		}

	}

/**
 * @param cart
 * @param order
 * this method will create order and order_items objects from cart object.
 */
	private void saveOrderFromCart(Cart cart, Order order ){
		try {

			order.setTotal(cart.getTotal());
			order.setUserId(cart.getUserId());
			order.setTotal(cart.getTotal());
			order.setTax(cart.getTax());
			order.setTotal(cart.getTotal());
			order.setOrderPickupDate(cart.getOrderPickupDate());
			order.setStatus("submitted");
			OrderDAO orderdao = new OrderDAO();
			OrderItemDAO itemDao = new OrderItemDAO();

			orderdao.addOrder(order);


			ArrayList <CartItem> items = cart.getCartItems();
			Iterator<CartItem> itemsIterator = items.iterator();
			while(itemsIterator.hasNext()){
				CartItem cItem = itemsIterator.next();
				OrderItem oItem = new OrderItem();
				oItem.setOrderId(order.getOrderId());
				oItem.setPrice(cItem.getPrice());
				oItem.setQuantity(cItem.getQuantity());
				oItem.setServiceName(cItem.getServiceName());
				oItem.setServiceType(cItem.getServiceType());
				oItem.setTax(cItem.getTax());
				oItem.setSubTotalPrice(cItem.getSubTotalPrice());
				oItem.setTotalPrice(cItem.getTotalPrice());
				itemDao.addOrderItem(oItem);					
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException
	 * this method will display order confirmation scene.
	 */
	private void launchOrderConfirmation() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderCnfView.fxml"));	
		AnchorPane orderCnf = (AnchorPane) loader.load();
		Scene parentScene = primaryStage.getScene();
		BorderPane borderPane = (BorderPane)parentScene.getRoot();
		borderPane.setCenter(orderCnf);	
	}

	/**
	 * @return
	 * this method will validate address input fields and return error message if any mismatch occurs.
	 */
	private String addressInputValid() {
		String error = "";
		if (fullName.getText() == null || fullName.getText().trim().length() == 0) {
			error += "Full Name Can't be blank! \n"; 
		}
		if (addressLine1.getText() == null || addressLine1.getText().trim().length() == 0) {
			error += "Address Line1 Can't be blank! \n"; 
		}
		if (city.getText() == null || city.getText().trim().length() == 0) {
			error += "City Can't be blank! \n"; 
		}	
		if (zipCode.getText() == null || zipCode.getText().trim().length() == 0) {
			error += "Zip code Can't be blank!. \n"; 
		}else if( !zipPattern.matcher(zipCode.getText().trim()).find()){
			error += "Please enter a 5-digit zip code \n";
		}
		if (state.getValue() == null || state.getValue().trim().length() == 0) {
			error += "State Can't be blank! \n" ; 
		}
		if (phone.getText() == null || phone.getText().trim().length() == 0) {
			error += "Phone Can't be blank! \n" ; 
		}else if( !phonePattern.matcher(phone.getText().trim()).find()){
			error += "Please enter a valid phone! \n";
		}
		if (orderPickupDate.getValue() == null ) {
			error += "Pickup Date Can't be blank! \n" ; 
		}
		return error;
	}


	@FXML
	private void redirectlogin(ActionEvent event) throws IOException{
		primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		new AuthenticationController().logout(primaryStage);
	}

	@FXML
	private void redirectCart(ActionEvent event) throws IOException{
		primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ServiceCartView.fxml"));
		AnchorPane personOverview = (AnchorPane) loader.load();
		Scene parentScene = primaryStage.getScene();
		BorderPane borderPane = (BorderPane)parentScene.getRoot();
		borderPane.setCenter(personOverview);		
		ServiceCartController serviceCart = loader.getController();
		serviceCart.launchServiceCart(primaryStage);
	}
}
