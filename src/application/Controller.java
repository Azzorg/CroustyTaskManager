package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.plaf.SeparatorUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {
	Client client = new Client();

	public Tache tActual = new Tache();

	private enum Connex {
		OK, NOTOK;
	}

	@FXML
	protected Text actiontarget;
	@FXML
	private PasswordField password;
	@FXML
	private Label message;
	@FXML
	private TextField userName;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password_repeat;
	@FXML
	private Label instruction;
	@FXML
	private Button btn1;
	@FXML
	private Button btn2;
	@FXML
	private Button task_valid;
	@FXML
	private Button edit_button;
	@FXML
	private VBox vb1;
	@FXML
	private VBox created_task;
	@FXML
	private VBox todo_task;
	@FXML
	private Button register;
	@FXML
	private Button register_page;
	@FXML
	private Button log_out;

	// Edit Page
	@FXML
	private TextField edit_name;
	@FXML
	private TextArea edit_content;
	@FXML
	private Button edit_valid;

	@FXML
	private ComboBox edit_state;
	@FXML
	private ComboBox edit_priority;
	@FXML
	private ComboBox edit_affecte;

	// Creation Page
	@FXML
	private TextField task_name;
	@FXML
	private TextArea task_content;
	@FXML
	private ComboBox task_affecte;
	@FXML
	private ComboBox task_state;
	@FXML
	private ComboBox task_priority;

	private String task_Name = null;
	private String task_Content = null;

	// La personne sélectionné
	private Personne pActual = new Personne();
	
	public Personne ReceiveUser(Client c) throws IOException {
		Personne p = new Personne();
		String [] sp = c.getIn().readLine().split("§");
		p.setIdPersonne(Integer.parseInt(sp[0]));
		p.setNomPersonne(sp[1]);
		p.setPassWord(sp[2]);
		return p;
	}

	@SuppressWarnings("unchecked")
	@FXML
	// Document FXML chargé
	protected void initialize() throws IOException {
		if(btn1 != null)
			client.connexionServer();
		
		System.out.println("Initialisation pour les objects");
		
		// On est dans la page accueil
		if (vb1 != null) {
			System.out.println("Page d'accueil");
			// ajout des boutons users
			for (int i = 0; i < client.listPersonne.size(); i++) {
				Button user = new Button(client.listPersonne.get(i).getNomPersonne());
				user.setStyle ("-fx-background-color : rgb(25,25,25); -fx-border-radius : 0px;");
				user.setMaxWidth(Double.MAX_VALUE);
				user.setMaxHeight(Double.MAX_VALUE);
				user.setId(Integer.toString(client.listPersonne.get(i).getIdPersonne()));

				// Action sur le bouton user
				user.addEventHandler(ActionEvent.ACTION, event -> {
					try {
						System.out.println("Action bouton user");						
						
						// on stock la personne cliqué
						pActual = client.listPersonne.get(Integer.parseInt(user.getId()));
						// vide des taches
						todo_task.getChildren().clear();
						created_task.getChildren().clear();

						// Envoie de demande de liste des taches
						client.setOutput(client.aClient.getOutputStream());
						client.setOut(new PrintStream(client.getOutput()));
						client.setInput(client.aClient.getInputStream());
						client.setIn(new BufferedReader(new InputStreamReader(client.getInput())));
						client.getOut().println("ACTION\nLIST_TASK\n" + pActual.getIdPersonne());
						
						System.out.println("Initialisation faite");
						
						// Réception de la liste de tache données
						client.listeTacheDonnees = client.ReceiveListTache();
						System.out.println(client.listeTacheDonnees);

						client.getOut().println("OK");

						// Réception de la liste de tache à faire
						client.listeTacheAFaire = client.ReceiveListTache();
						System.out.println(client.listeTacheAFaire);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// ajout des taches à faire
					for (int j = 0; j < client.listeTacheAFaire.size(); j++) {
						try {
							if (client.listeTacheAFaire.get(j).getAffecte().getIdPersonne() == pActual.getIdPersonne()) {
								// Chargement du document FXML pour les taches
								GridPane task = FXMLLoader.load(getClass().getResource("../Interface/task.fxml"));
								task.setId(Integer.toString(client.listeTacheAFaire.get(j).getIdTache()));

								((Label) task.getChildren().get(0)).setText(client.listeTacheAFaire.get(j).getNomTache());
								((TextArea) task.getChildren().get(1))
										.setText(client.listeTacheAFaire.get(j).getDescriptif());
								((TextArea) task.getChildren().get(1)).setEditable(false);
								((Label) task.getChildren().get(2)).setText(client.listeTacheAFaire.get(j).getEtat());
								((Label) task.getChildren().get(3)).setText(
										"Crée par : " + client.listeTacheAFaire.get(j).getCreateur().getNomPersonne());
								((Label) task.getChildren().get(5))
										.setText("Priorité : " + client.listeTacheAFaire.get(j).getPriorite());

								// action sur le bouton edit
								((Button) task.getChildren().get(4)).addEventHandler(ActionEvent.ACTION, event_2 -> {
									for (int k = 0; k < client.listeTacheAFaire.size(); k++) {
										if (Integer.parseInt(task.getId()) == client.listeTacheAFaire.get(k).getIdTache())
											tActual = client.listeTacheAFaire.get(k);
									}

									try {
										System.out.println(
												"La tache en cours d'édition est la tache" + tActual.getIdTache());
										Stage stage = null;
										Parent root = null;
										stage = (Stage) log_out.getScene().getWindow();
										root = FXMLLoader.load(getClass().getResource("../Interface/edit_page.fxml"));
										Scene scene = new Scene(root);
										scene.getStylesheets().add(getClass().getResource("/Interface/style.css").toExternalForm());
										stage.setScene(scene);
										stage.show();
									} catch (IOException e) {
										// created Auto-generated catch block
										e.printStackTrace();
									}

								});
								if (client.listeTacheAFaire.get(j).getCreateur() == client.getMe()
										|| client.listeTacheAFaire.get(j).getAffecte() == client.getMe())
									((Button) task.getChildren().get(4)).setDisable(false);
								else
									((Button) task.getChildren().get(4)).setDisable(true);
								todo_task.getChildren().add(task);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					// ajout des taches crées
					for (int j = 0; j < client.listeTacheDonnees.size(); j++) {
						try {
							if (client.listeTacheDonnees.get(j).getCreateur().getIdPersonne() == pActual
									.getIdPersonne()) {
								// Chargement du document FXML
								GridPane task = FXMLLoader.load(getClass().getResource("../Interface/task.fxml"));
								task.setId(Integer.toString(client.listeTacheDonnees.get(j).getIdTache()));
								((Label) task.getChildren().get(0))
										.setText(client.listeTacheDonnees.get(j).getNomTache());
								((TextArea) task.getChildren().get(1))
										.setText(client.listeTacheDonnees.get(j).getDescriptif());
								((TextArea) task.getChildren().get(1)).setEditable(false);
								((Label) task.getChildren().get(2)).setText(client.listeTacheDonnees.get(j).getEtat());
								((Label) task.getChildren().get(3)).setText(
										"Affectée à : " + client.listeTacheDonnees.get(j).getAffecte().getNomPersonne());
								((Label) task.getChildren().get(5))
										.setText("Priorité : " + client.listeTacheDonnees.get(j).getPriorite());
								// action sur le bouton edit
								((Button) task.getChildren().get(4)).addEventHandler(ActionEvent.ACTION, event_2 -> {

									for (int k = 0; k < client.listeTacheDonnees.size(); k++) {
										if (Integer.parseInt(task.getId()) == client.listeTacheDonnees.get(k)
												.getIdTache())
											tActual = client.listeTacheDonnees.get(k);
									}
									try {

										System.out.println(
												"La tache en cours d'édition est la tache" + tActual.getIdTache());
										Stage stage = null;
										Parent root = null;
										stage = (Stage) log_out.getScene().getWindow();
										root = FXMLLoader.load(getClass().getResource("../Interface/edit_page.fxml"));
										Scene scene = new Scene(root);
										scene.getStylesheets().add(getClass().getResource("/Interface/style.css").toExternalForm());
										stage.setScene(scene);
										stage.show();
									} catch (IOException e) {
										// created Auto-generated catch block
										e.printStackTrace();
									}

								});

								if (client.listeTacheDonnees.get(j).getCreateur() == client.getMe()
										|| client.listeTacheDonnees.get(j).getAffecte() == client.getMe())
									((Button) task.getChildren().get(4)).setDisable(false);
								else
									((Button) task.getChildren().get(4)).setDisable(true);

								created_task.getChildren().add(task);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				});

				vb1.getChildren().add(user);
			}
		}

		// Création de la page d'édition
		if (edit_name != null) {
			edit_name.setText(tActual.getNomTache());
			edit_content.setText(tActual.getDescriptif());
			edit_priority.getItems().addAll("Basse", "Moyenne", "Haute");
			edit_state.getItems().addAll("A faire", "En cours", "Arret", "Terminée");
			for (int i = 0; i < client.listPersonne.size(); i++) {
				edit_affecte.getItems().add(client.listPersonne.get(i).getNomPersonne());
			}
			for (int j = 0; j < client.listPersonne.size(); j++) {
				if (tActual.getAffecte() == client.listPersonne.get(j)) {
					edit_affecte.getSelectionModel().select(j);
				}
			}
			edit_state.getSelectionModel().select(tActual.getEtat());
			edit_priority.getSelectionModel().select(tActual.getPriorite());

		}

		// Création de la page de création de tache
		if (task_content != null) {
			task_priority.getItems().addAll("Basse", "Moyenne", "Haute");
			task_state.getItems().addAll("A faire", "En cours", "Arret");
			for (int i = 0; i < client.listPersonne.size(); i++) {
				task_affecte.getItems().add(client.listPersonne.get(i).getNomPersonne());
			}
		}
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException, InterruptedException {

		// Button connect
		if (event.getSource() == btn1) {
			System.out.println("username : " + userName.getText());
			System.out.println("pw : " + password.getText());
			client.sendUserConnexionNotNew(userName.getText(), client.md5(password.getText()));
			System.out.println("Demande connexion");

			// Attente réponse
			while (!client.getIn().readLine().equals("CONNEXION"))
				System.out.println("Attente réponse serveur");

			System.out.println("Réponse connexion");
			Connex connex = Connex.valueOf(client.getIn().readLine());
			
			client.getOut().println("OK");
			switch (connex) {
			// Le nom et le mot de passe corresponde
			case OK:
				client.ReceiveUserList();
				System.out.println("receive 1 ok : " + client.listPersonne.size());
				client.getOut().println("ME");
				System.out.println("ME envoyé");
				client.setMe(ReceiveUser(client));
				Thread.sleep(10);
				System.out.println("Receive 2 ok");
				client.getOut().println("OK");
				System.out.println("Recu ok");
				// Réception de la liste à faire par me
				while (!client.getIn().readLine().equals("LISTEAFAIRE"))
					System.out.println("Attente du serveur");
				if (client.getIn().equals("ENVOI"))
					client.listeTacheAFaire = client.ReceiveListTache();
				client.getOut().println("OK");

				// Réception de la liste des taches créées par me
				while (!client.getIn().readLine().equals("LISTECREEE"))
					System.out.println("Attente du serveur");
				if (client.getIn().equals("ENVOI"))
					client.listeTacheDonnees = client.ReceiveListTache();
				client.getOut().println("OK");

				Stage stage = null;
				Parent root = null;
				stage = (Stage) btn1.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/Interface/style.css").toExternalForm());
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

		// Cliquer sur le bouton pour ajouter une tache
		if (event.getSource() == btn2) {
			Stage stage = null;
			Parent root = null;
			stage = (Stage) btn2.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("../Interface/task_page.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/Interface/style.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}

		// Cliquer sur le bouton pour créer une tache
		if (event.getSource() == task_valid) {
			if (task_name.getText() == null || task_content.getText() == null
					|| task_state.getSelectionModel().isEmpty() || task_priority.getSelectionModel().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Erreur");
				alert.setHeaderText("Erreur");
				alert.setContentText("Tous les champs doivent êrte remplis");
				alert.showAndWait();
			} else {
				Tache t = new Tache();
				t.setCreateur(client.getMe());
				t.setNomTache(task_name.getText());
				t.setDescriptif(task_content.getText());
				int id = 0;

				if (client.listeTacheDonnees.size() == 0)
					t.setIdTache(0);
				else {
					id = client.listeTacheDonnees.get(client.listeTacheDonnees.size() - 1).getIdTache();
					System.out.println(id);
					t.setIdTache(id + 1);
				}
				t.setEtat((task_state.getSelectionModel().getSelectedItem().toString()));
				t.setPriorite((task_priority.getSelectionModel().getSelectedItem().toString()));

				// Get the personne
				for (int i = 0; i < client.listPersonne.size(); i++) {
					if (task_affecte.getSelectionModel().getSelectedItem() == client.listPersonne.get(i)
							.getNomPersonne())
						t.setAffecte(client.listPersonne.get(i));
				}
				client.listeTacheDonnees.add(t);

				Stage stage = null;
				Parent root = null;
				stage = (Stage) task_valid.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/Interface/style.css").toExternalForm());
				stage.setScene(scene);
				stage.show();
			}

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
					client.ReceiveUserList();
					client.getOut().println("ME");
					client.setMe(ReceiveUser(client));
					client.getOut().println("OK");

					// Réception de la liste à faire par me
					while (!client.getIn().readLine().equals("LISTEAFAIRE"))
						System.out.println("Attente du serveur");
					if (client.getIn().equals("ENVOI"))
						client.listeTacheAFaire = client.ReceiveListTache();
					client.getOut().println("OK");

					// Réception de la liste des taches créées par me
					while (!client.getIn().readLine().equals("LISTECREEE"))
						System.out.println("Attente du serveur");
					if (client.getIn().equals("ENVOI"))
						client.listeTacheDonnees = client.ReceiveListTache();
					client.getOut().println("OK");

					Stage stage = null;
					Parent root = null;
					stage = (Stage) register.getScene().getWindow();
					root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("/Interface/style.css").toExternalForm());
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
			scene.getStylesheets().add(getClass().getResource("/Interface/style.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}
		if (event.getSource() == log_out) {
			Stage stage = null;
			Parent root = null;
			stage = (Stage) log_out.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("../Interface/login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/Interface/style.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}

		// Cliquer sur le bouton pour valider une tache en cours d'edition
		if (event.getSource() == edit_valid) {
			System.out.println("azeaze");
			tActual.setNomTache(edit_name.getText());
			tActual.setDescriptif(edit_content.getText());
			tActual.setEtat((edit_state.getSelectionModel().getSelectedItem().toString()));
			tActual.setPriorite((edit_priority.getSelectionModel().getSelectedItem().toString()));

			// Get the personne
			for (int i = 0; i < client.listPersonne.size(); i++) {
				if (edit_affecte.getSelectionModel().getSelectedItem() == client.listPersonne.get(i).getNomPersonne())
					tActual.setAffecte(client.listPersonne.get(i));
			}

			for (int j = 0; j < client.listeTacheAFaire.size(); j++) {
				if (tActual.getIdTache() == client.listeTacheAFaire.get(j).getIdTache())
					client.listeTacheAFaire.set(j, tActual);
			}

			if (tActual.getEtat() == "Terminée") {
				for (int i = 0; i < client.listeTacheAFaire.size(); i++) {
					if (client.listeTacheAFaire.get(i).getIdTache() == tActual.getIdTache()) {
						client.listeTacheAFaire.remove(i);
					}
				}
			}
			Stage stage = null;
			Parent root = null;
			stage = (Stage) edit_valid.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/Interface/style.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}

	}
}
