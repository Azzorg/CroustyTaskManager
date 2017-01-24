package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller_accueil implements Initializable {
	
	@FXML
	private VBox vb1;
	@FXML
	private Button log_out; 
	@FXML
	private Button btn2;
	
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {

	     if (event.getSource() == btn2)
	     {
	    	 /*Stage stage = null; 
		     Parent root = null;
		     stage=(Stage) btn2.getScene().getWindow();
			 root = FXMLLoader.load(getClass().getResource("../Interface/task_page.fxml"));
			 Scene scene = new Scene(root);
		     stage.setScene(scene);
		     stage.show();*/
	     }
	     
	     if (event.getSource() == log_out)
	     {
	    	 Stage stage = null; 
		     Parent root = null;
		     stage=(Stage) log_out.getScene().getWindow();
			 root = FXMLLoader.load(getClass().getResource("../Interface/login.fxml"));
			 Scene scene = new Scene(root);
		     stage.setScene(scene);
		     stage.show();
	     }

	 }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}

