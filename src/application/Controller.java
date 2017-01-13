package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Controller {
	@FXML protected Text actiontarget;
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		/*GridPane accueil = (GridPane) FXMLLoader.load(Main.class.getResource("../Interface/test.fxml"));
		Scene scene = new Scene(accueil);
		scene.setRoot(accueil);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());*/
	    System.out.println("button clicked");
	 }
}
