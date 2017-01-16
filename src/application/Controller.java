package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {
	@FXML protected Text actiontarget;
	
	@FXML
    private Button btn1;
	@FXML
	private Button btn2;
	@FXML
	private Button valid;
	@FXML
	private VBox vb1; 
	
	@FXML 
	private TextField task_name;
	@FXML 
	private TextField task_content;
	
	private int i = 2;
	
	private String task_Name = null;
	private String task_Content = null;
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
	     
	     
	     if (event.getSource() == btn1)
	     {
	    	 Stage stage = null; 
		     Parent root = null;
		     stage=(Stage) btn1.getScene().getWindow();
			 root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
			 Scene scene = new Scene(root);
		     stage.setScene(scene);
		     stage.show();

	     }
	     if (event.getSource() == btn2)
	     {
	    	 Stage stage = null; 
		     Parent root = null;
		     stage=(Stage) btn2.getScene().getWindow();
			 root = FXMLLoader.load(getClass().getResource("../Interface/task_page.fxml"));
			 Scene scene = new Scene(root);
		     stage.setScene(scene);
		     stage.show();
	     }
	     
	     if (event.getSource() == valid)
	     {
	    	 task_Name = task_name.getText();
	    	 task_Content = task_content.getText();
	    	 Stage stage = null; 
		     Parent root = null;
		     stage=(Stage) valid.getScene().getWindow();
			 root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
			 Scene scene = new Scene(root);
		     stage.setScene(scene);
		     stage.show();
		     System.out.println(task_Name +"\n");
		     System.out.println(task_Content + "\n");
	     }
	     

	 }
}
