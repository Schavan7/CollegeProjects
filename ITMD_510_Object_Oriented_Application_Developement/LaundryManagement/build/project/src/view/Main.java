package view;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application  {
	private Parent parent;
	private Scene scene;
	private Stage primaryStage;
	
	/**
	 * Start method of application. 
	 * Loads root layout.
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		parent = FXMLLoader.load(getClass().getResource("/view/RootLayout.fxml"));
		scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login Page");
		primaryStage.setResizable(true);
		AuthenticationController cntrl = new AuthenticationController();
		cntrl.launchLogingController(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
