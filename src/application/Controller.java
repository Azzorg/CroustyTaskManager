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
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Controller {
	Client client = new Client();

	private enum Connex {
		OK, NOTOK;
	}

	@FXML
	protected Text actiontarget;

	@FXML
	private TextField password;

	@FXML
	private Label message;

	@FXML
	private TextField userName;

	@FXML
	private Button btn1;
	@FXML
	private Button btn2;
	@FXML
	private Button valid;
	@FXML
	private VBox vb1;
	@FXML
	private VBox todo_task;
	@FXML
	private VBox assigned_task;
	@FXML
	private Button register;
	@FXML
	private Button register_page;
	@FXML
	private Button log_out;

	@FXML
	private TextField task_name;
	@FXML
	private TextField task_content;

	private int i = 2;

	private String task_Name = null;
	private String task_Content = null;

	@FXML
	protected void initialize() {
		client.connexionServer();
		if (vb1 != null) {
			for (int i = 0; i < 50; i++) {
				Button user = new Button("Utilisateur " + (i + 1));
				user.setMaxWidth(Double.MAX_VALUE);
				user.setMaxHeight(Double.MAX_VALUE);
				user.setId("user" + (i + 1));
				user.addEventHandler(ActionEvent.ACTION, event -> {
					assigned_task.getChildren().clear();
					todo_task.getChildren().clear();
					for (int j = 0; j < 1 + (int) (Math.random() * 1000); j++)
						assigned_task.getChildren().add(new Button("assigned task" + j));

					for (int j = 0; j < 1 + (int) (Math.random() * 1000); j++)
						todo_task.getChildren().add(new Button(" to dotask" + j));
				});
				vb1.getChildren().add(user);
			}
		}

	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {

		// Button connect
		if (event.getSource() == btn1) {
			client.sendUserConnexionNotNew(userName.getText(), password.getText()); // �
																					// enlever
																					// lorsque
																					// les
																					// mots
																					// de
																					// passe
																					// seront
																					// crypt�s
			
			// client.sendUserConnexionNotNew(userName.getText(), client.md5(password.getText())); //� d�s�lectionner

			// Attente r�ponse
			while (!client.getIn().readLine().equals("CONNEXION"))
				System.out.println("Attente r�ponse serveur");
			Connex connex = Connex.valueOf(client.getIn().readLine());

			switch (connex) {
			// Le nom et le mot de passe corresponde
			case OK:
				Stage stage = null;
				Parent root = null;
				stage = (Stage) btn1.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
				break;
			// Le mot de passe ne correspond pas au nom
			case NOTOK:
				message.setText("Le mot de passe ou le nom n'est pas le bon");
				break;
			default:
				break;
			}

		}

		if (event.getSource() == btn2) {
			Stage stage = null;
			Parent root = null;
			stage = (Stage) btn2.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("../Interface/task_page.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		}

		if (event.getSource() == valid) {
			task_Name = task_name.getText();
			task_Content = task_content.getText();
			Stage stage = null;
			Parent root = null;
			stage = (Stage) valid.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			System.out.println(task_Name + "\n");
			System.out.println(task_Content + "\n");
		}
		
		//Sur la page register teste d'enregistrement
		if (event.getSource() == register) {
			Stage stage = null;
			Parent root = null;
			stage = (Stage) register.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
		if (event.getSource() == register_page) {
			Stage stage = null;
			Parent root = null;
			stage = (Stage) register_page.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("../Interface/register.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		if (event.getSource() == register_page) {
			Stage stage = null;
			Parent root = null;
			stage = (Stage) register_page.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("../Interface/register.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		if (event.getSource() == log_out) {
			Stage stage = null;
			Parent root = null;
			stage = (Stage) log_out.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("../Interface/login.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}

	}
}
