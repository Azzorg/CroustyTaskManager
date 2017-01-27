package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
	private TextField username;
	@FXML
	private TextField password_repeat;
	@FXML
	private Label instruction;
	@FXML
	private Button btn1;
	@FXML
	private Button btn2;
	@FXML
	private Button valid;
	@FXML
	private Button edit_button;
	@FXML
	private VBox vb1;
	@FXML
	private VBox todo_task;
	@FXML
	private VBox given_task;
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
	
	
	//Edit Page
	@FXML
	private TextField edit_name;
	@FXML
	private TextArea edit_description;
	@FXML
	private Button edit_valid;
	

	private String task_Name = null;
	private String task_Content = null;
<<<<<<< HEAD
	
	
	
	Tache t = new Tache("bite",0,new Personne(0,"Guy","01"),new Personne(1,"marcel","01"),"blabla");
=======

	Tache t = new Tache("bite", 0, new Personne(0, "Guy", "01"), new Personne(1, "marcel", "01"), "blabla");
>>>>>>> branch 'master' of https://github.com/Azzorg/CroustyTaskManager.git

	@FXML
	protected void initialize() {
		client.connexionServer();
		if (vb1 != null) {
			for (int i = 0; i < 50; i++) {
				Button user = new Button("Utilisateur " + (i + 1));
				user.setMaxWidth(Double.MAX_VALUE);
				user.setMaxHeight(Double.MAX_VALUE);
				user.setId("user" + (i + 1));

				// Action sur le bouton user
				user.addEventHandler(ActionEvent.ACTION, event -> {
					// vide des taches
					given_task.getChildren().clear();
					todo_task.getChildren().clear();
<<<<<<< HEAD
					
					for (int j = 0; j <10000 ; j++)// ajout des taches assignées
					{						
=======

					for (int j = 0; j < 1 + (int) (Math.random() * 10); j++)// ajout
																			// des
																			// taches
																			// assignées
					{
>>>>>>> branch 'master' of https://github.com/Azzorg/CroustyTaskManager.git
						try {
							GridPane task = FXMLLoader.load(getClass().getResource("../Interface/task.fxml"));
<<<<<<< HEAD
							
							((Label)task.getChildren().get(0)).setText(t.getNomTache());
							((TextArea)task.getChildren().get(1)).setText(t.getDescriptif());
							((TextArea)task.getChildren().get(1)).setEditable(false);
							((ComboBox)task.getChildren().get(2)).getItems().addAll("à faire" , "en cours" , "arrêt" , "terminer");
							((ComboBox)task.getChildren().get(2)).getSelectionModel().selectFirst();
							((Label)task.getChildren().get(4)).setText("Assignées à " + t.getCreateur().getNomPersonne()); 
							((Label)task.getChildren().get(5)).setText("priorité"); 
							((Button)task.getChildren().get(6)).setText("Edit");
							
							((Button)task.getChildren().get(6)).setId("edit"+j);
							((Button)task.getChildren().get(6)).addEventHandler(ActionEvent.ACTION, event_2 -> 
							{
					            
=======

							((Label) task.getChildren().get(0)).setText(t.getNomTache());
							((TextArea) task.getChildren().get(1)).setText(t.getDescriptif());
							((TextArea) task.getChildren().get(1)).setEditable(false);
							((ComboBox) task.getChildren().get(2)).getItems().addAll("à faire", "en cours", "arrêt",
									"terminer");
							((ComboBox) task.getChildren().get(2)).getSelectionModel().selectFirst();
							((Label) task.getChildren().get(4))
									.setText("Assignées par " + t.getCreateur().getNomPersonne());
							((Label) task.getChildren().get(5)).setText("priorité");
							((Button) task.getChildren().get(6)).setText("bite" + j);

							((Button) task.getChildren().get(6)).setId("edit" + j);
							((Button) task.getChildren().get(6)).addEventHandler(ActionEvent.ACTION, event_2 -> {

>>>>>>> branch 'master' of https://github.com/Azzorg/CroustyTaskManager.git
								try {
<<<<<<< HEAD
							        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Interface/edit_page.fxml"));
					                Parent root1 = (Parent) fxmlLoader.load();
					                Stage stage = new Stage();
					                stage.setScene(new Scene(root1));  
					                stage.show();

=======
									FXMLLoader fxmlLoader = new FXMLLoader(
											getClass().getResource("../Interface/edit_page.fxml"));
									Parent root1 = (Parent) fxmlLoader.load();
									Stage stage = new Stage();
									stage.setScene(new Scene(root1));
									stage.show();
>>>>>>> branch 'master' of https://github.com/Azzorg/CroustyTaskManager.git
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							});

							given_task.getChildren().add(task);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					for (int j = 0; j < 1 + (int) (Math.random() * 10); j++) // ajout
																				// des
																				// taches
																				// à
																				// faire
					{
						{
							try {
								GridPane task = FXMLLoader.load(getClass().getResource("../Interface/task.fxml"));
<<<<<<< HEAD
								
								((Label)task.getChildren().get(0)).setText(t.getNomTache());
								((TextArea)task.getChildren().get(1)).setText("description ");
								((TextArea)task.getChildren().get(1)).setEditable(false);
								((ComboBox)task.getChildren().get(2)).getItems().addAll("à faire" , "en cours" , "arrêt" , "terminer");
								((ComboBox)task.getChildren().get(2)).getSelectionModel().selectFirst();
								((Label)task.getChildren().get(4)).setText("Créée par " + t.getCreateur().getNomPersonne());
								((Label)task.getChildren().get(5)).setText("priorité");
								((Label)task.getChildren().get(5)).setText("priorité"); 
								((Button)task.getChildren().get(6)).setText("Edit");
								
								((Button)task.getChildren().get(6)).setId("edit"+j);
								((Button)task.getChildren().get(6)).addEventHandler(ActionEvent.ACTION, event_2 -> 
								{
						            
									try {
								        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Interface/edit_page.fxml"));
						                Parent root1 = (Parent) fxmlLoader.load();
						                Stage stage = new Stage();
						                stage.setScene(new Scene(root1));  
						                stage.show();
						                
						                edit_name.setText(t.getNomTache());
						                edit_description.setText(t.getDescriptif());
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								});
								
								
=======

								((Label) task.getChildren().get(0)).setText("task " + j);
								((TextArea) task.getChildren().get(1)).setText("description ");
								((TextArea) task.getChildren().get(1)).setEditable(false);
								((ComboBox) task.getChildren().get(2)).getItems().addAll("à faire", "en cours", "arrêt", "terminer");
								((ComboBox) task.getChildren().get(2)).getSelectionModel().selectFirst();
								((Label) task.getChildren().get(4)).setText("Créée à ... ");
								((Label) task.getChildren().get(5)).setText("priorité");

>>>>>>> branch 'master' of https://github.com/Azzorg/CroustyTaskManager.git
								todo_task.getChildren().add(task);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

				});

				vb1.getChildren().add(user);
			}
		}
<<<<<<< HEAD
		
        if(edit_name!=null)
        {
            ((TextField)edit_name).setText(t.getNomTache());
            ((TextArea)edit_description).setText(t.getDescriptif());        	
        }


=======
>>>>>>> branch 'master' of https://github.com/Azzorg/CroustyTaskManager.git
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {

		// Button connect
		if (event.getSource() == btn1) {

			client.sendUserConnexionNotNew(userName.getText(), client.md5(password.getText())); // à
																								// désélectionner

			// Attente réponse
			while (!client.getIn().readLine().equals("CONNEXION"))
				System.out.println("Attente réponse serveur");
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

		// Sur la page register teste d'enregistrement
		if (event.getSource() == register) {
			// Le mot de passe et la répétition du mot de passe sont les mêmes
			if (password.getText().equals(password_repeat.getText())) {
				client.sendUserConnexionNew(username.getText(), client.md5(password.getText()));

				// Attente réponse
				while (!client.getIn().readLine().equals("CONNEXION"))
					System.out.println("Attente réponse serveur");
				Connex connex = Connex.valueOf(client.getIn().readLine());

				switch (connex) {
				// Le nom et le mot de passe corresponde
				case OK:
					Stage stage = null;
					Parent root = null;
					stage = (Stage) register.getScene().getWindow();
					root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
					break;
				// Le mot de passe ne correspond pas au nom
				case NOTOK:
					message.setText("Ce nom d'utilisateur est déjà utilisé");
					break;
				default:
					break;
				}
			}
			// Ils ne sont pas identiques
			else
				instruction.setText("Les 2 mots de passe doivent être identiques");
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
		if (event.getSource() == edit_valid) {
			System.out.println("azeazeaze");
			Stage stage = null;
			Parent root = null;
			stage = (Stage) log_out.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}

	}
}
