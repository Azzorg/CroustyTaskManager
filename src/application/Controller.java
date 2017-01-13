package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {
	@FXML protected Text actiontarget;
	
	@FXML
    private Button btn1;
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
	     Stage stage; 
	     Parent root;
	     stage=(Stage) btn1.getScene().getWindow();
		 root = FXMLLoader.load(getClass().getResource("../Interface/test.fxml"));
		 Scene scene = new Scene(root);
	     stage.setScene(scene);
	     stage.show();
	 }
}
