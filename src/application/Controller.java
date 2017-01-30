package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

	// La personne s�lectionn�
	private Personne pActual = new Personne();

	Tache t = new Tache("bite", 0, new Personne(0, "Guy", "01"), new Personne(1, "marcel", "01"), "blabla");

	@SuppressWarnings("unchecked")
	@FXML
	protected void initialize() { // Document FXML charg�
		client.connexionServer();

		if (vb1 != null) { // On est dans la page accueil
			for (int i = 0; i < Main.listePersonne.size(); i++) { // ajout des
																	// boutons
																	// users
				Button user = new Button(Main.listePersonne.get(i).getNomPersonne());
				user.setMaxWidth(Double.MAX_VALUE);
				user.setMaxHeight(Double.MAX_VALUE);
				user.setId(Integer.toString(Main.listePersonne.get(i).getIdPersonne()));

				// Action sur le bouton user
				user.addEventHandler(ActionEvent.ACTION, event -> {
					try {
						client.setObjIn(new ObjectInputStream(client.getInput()));
						client.setObjOut(new ObjectOutputStream(client.getOutput()));
						pActual = Main.listePersonne.get(Integer.parseInt(user.getId())); // on stock la personne cliqu�
						// vide des taches
						todo_task.getChildren().clear();
						created_task.getChildren().clear();

						// Envoie de demande de liste des taches
						client.getOut().println("ACTION\nLIST_TASK\n" + pActual.getIdPersonne());
						
						//R�ception de la liste de tache donn�es
						Main.listeTacheDonnees = (ArrayList<Tache>) client.getObjIn().readObject();
						
						client.getOut().println("OK");
						
						//R�ception de la liste de tache � faire
						Main.listeTacheAFaire = (ArrayList<Tache>) client.getObjIn().readObject();
						
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					for (int j = 0; j < Main.listeTacheAFaire.size(); j++)// ajout des taches � faire
					{
						try {
							if (Main.listeTacheAFaire.get(j).getAffecte().getIdPersonne() == pActual.getIdPersonne()) {
								GridPane task = FXMLLoader.load(getClass().getResource("../Interface/task.fxml")); // Chargement
																													// du
																													// document
																													// FXML
																													// pour
																													// les
																													// taches
								task.setId(Integer.toString(Main.listeTacheAFaire.get(j).getIdTache()));

								((Label) task.getChildren().get(0)).setText(Main.listeTacheAFaire.get(j).getNomTache());
								((TextArea) task.getChildren().get(1))
										.setText(Main.listeTacheAFaire.get(j).getDescriptif());
								((TextArea) task.getChildren().get(1)).setEditable(false);
								((Label) task.getChildren().get(2)).setText(Main.listeTacheAFaire.get(j).getEtat());
								((Label) task.getChildren().get(3)).setText(
										"Cr�e par : " + Main.listeTacheAFaire.get(j).getCreateur().getNomPersonne());
								((Label) task.getChildren().get(5))
										.setText("Priorit� : " + Main.listeTacheAFaire.get(j).getPriorite());
								((Button) task.getChildren().get(4)).addEventHandler(ActionEvent.ACTION, event_2 -> // action
																													// sur
																													// le
																													// bouton
																													// edit
								{
									for (int k = 0; k < Main.listeTacheAFaire.size(); k++) {
										if (Integer.parseInt(task.getId()) == Main.listeTacheAFaire.get(k).getIdTache())
											Main.tActual = Main.listeTacheAFaire.get(k);
									}

									try {
										System.out.println(
												"La tache en cours d'�dition est la tache" + Main.tActual.getIdTache());
										Stage stage = null;
										Parent root = null;
										stage = (Stage) log_out.getScene().getWindow();
										root = FXMLLoader.load(getClass().getResource("../Interface/edit_page.fxml"));
										Scene scene = new Scene(root);
										stage.setScene(scene);
										stage.show();
									} catch (IOException e) {
										// created Auto-generated catch block
										e.printStackTrace();
									}

								});
								if (Main.listeTacheAFaire.get(j).getCreateur() == Main.me
										|| Main.listeTacheAFaire.get(j).getAffecte() == Main.me)
									((Button) task.getChildren().get(4)).setDisable(false);
								else
									((Button) task.getChildren().get(4)).setDisable(true);
								todo_task.getChildren().add(task);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					for (int j = 0; j < Main.listeTacheDonnees.size(); j++) // ajout
																			// des
																			// taches
																			// cr�es
					{
						try {
							if (Main.listeTacheDonnees.get(j).getCreateur().getIdPersonne() == pActual
									.getIdPersonne()) {
								GridPane task = FXMLLoader.load(getClass().getResource("../Interface/task.fxml")); // Chargement
																													// du
																													// document
																													// FXML
								task.setId(Integer.toString(Main.listeTacheDonnees.get(j).getIdTache()));
								((Label) task.getChildren().get(0))
										.setText(Main.listeTacheDonnees.get(j).getNomTache());
								((TextArea) task.getChildren().get(1))
										.setText(Main.listeTacheDonnees.get(j).getDescriptif());
								((TextArea) task.getChildren().get(1)).setEditable(false);
								((Label) task.getChildren().get(2)).setText(Main.listeTacheDonnees.get(j).getEtat());
								((Label) task.getChildren().get(3)).setText(
										"Affect�e � : " + Main.listeTacheDonnees.get(j).getAffecte().getNomPersonne());
								((Label) task.getChildren().get(5))
										.setText("Priorit� : " + Main.listeTacheDonnees.get(j).getPriorite());
								((Button) task.getChildren().get(4)).addEventHandler(ActionEvent.ACTION, event_2 -> // action
																													// sur
																													// le
																													// bouton
																													// edit
								{

									for (int k = 0; k < Main.listeTacheDonnees.size(); k++) {
										if (Integer.parseInt(task.getId()) == Main.listeTacheDonnees.get(k)
												.getIdTache())
											Main.tActual = Main.listeTacheDonnees.get(k);
									}
									try {

										System.out.println(
												"La tache en cours d'�dition est la tache" + Main.tActual.getIdTache());
										Stage stage = null;
										Parent root = null;
										stage = (Stage) log_out.getScene().getWindow();
										root = FXMLLoader.load(getClass().getResource("../Interface/edit_page.fxml"));
										Scene scene = new Scene(root);
										stage.setScene(scene);
										stage.show();
									} catch (IOException e) {
										// created Auto-generated catch block
										e.printStackTrace();
									}

								});

								if (Main.listeTacheDonnees.get(j).getCreateur() == Main.me
										|| Main.listeTacheDonnees.get(j).getAffecte() == Main.me)
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

		// Cr�ation de la page d'�dition
		if (edit_name != null) {
			edit_name.setText(Main.tActual.getNomTache());
			edit_content.setText(Main.tActual.getDescriptif());
			edit_priority.getItems().addAll("Basse", "Moyenne", "Haute");
			edit_state.getItems().addAll("A faire", "En cours", "Arret", "Termin�e");
			for (int i = 0; i < Main.listePersonne.size(); i++) {
				edit_affecte.getItems().add(Main.listePersonne.get(i).getNomPersonne());
			}
			for (int j = 0; j < Main.listePersonne.size(); j++) {
				if (Main.tActual.getAffecte() == Main.listePersonne.get(j)) {
					edit_affecte.getSelectionModel().select(j);
				}
			}
			edit_state.getSelectionModel().select(Main.tActual.getEtat());
			edit_priority.getSelectionModel().select(Main.tActual.getPriorite());

		}

		// Cr�ation de la page de cr�ation de tache
		if (task_content != null) {
			task_priority.getItems().addAll("Basse", "Moyenne", "Haute");
			task_state.getItems().addAll("A faire", "En cours", "Arret");
			for (int i = 0; i < Main.listePersonne.size(); i++) {
				task_affecte.getItems().add(Main.listePersonne.get(i).getNomPersonne());
			}
		}
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {

		// Button connect
		if (event.getSource() == btn1) {
			System.out.println("username : " + userName.getText());
			System.out.println("pw : " + password.getText());
			client.sendUserConnexionNotNew(userName.getText(), client.md5(password.getText())); // �
																								// d�s�lectionner
			System.out.println("Demande connexion");

			// Attente r�ponse
			while (!client.getIn().readLine().equals("CONNEXION"))
				System.out.println("Attente r�ponse serveur");

			System.out.println("R�ponse connexion");
			Connex connex = Connex.valueOf(client.getIn().readLine());

			switch (connex) {
			// Le nom et le mot de passe corresponde
			case OK:
				Main.ReceiveUserList(client.getInput());
				client.getOut().println("ME");
				Main.ReceiveUser(client.getInput());
				client.getOut().println("OK");

				// R�ception de la liste � faire par me
				while (!client.getIn().readLine().equals("LISTEAFAIRE"))
					System.out.println("Attente du serveur");
				if (client.getIn().equals("ENVOI"))
					Main.ReceiveListTacheAfaire(client.getInput());
				client.getOut().println("OK");

				// R�ception de la liste des taches cr��es par me
				while (!client.getIn().readLine().equals("LISTECREEE"))
					System.out.println("Attente du serveur");
				if (client.getIn().equals("ENVOI"))
					Main.ReceiveListTacheDonnees(client.getInput());
				client.getOut().println("OK");

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

		// Cliquer sur le bouton pour ajouter une tache
		if (event.getSource() == btn2) {
			Stage stage = null;
			Parent root = null;
			stage = (Stage) btn2.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("../Interface/task_page.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}

		// Cliquer sur le bouton pour cr�er une tache
		if (event.getSource() == task_valid) {
			if (task_name.getText() == null || task_content.getText() == null
					|| task_state.getSelectionModel().isEmpty() || task_priority.getSelectionModel().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Erreur");
				alert.setHeaderText("Erreur");
				alert.setContentText("Tous les champs doivent �rte remplis");
				alert.showAndWait();
			} else {
				Tache t = new Tache();
				t.setCreateur(Main.me);
				t.setNomTache(task_name.getText());
				t.setDescriptif(task_content.getText());
				int id = 0;

				if (Main.listeTacheDonnees.size() == 0)
					t.setIdTache(0);
				else {
					id = Main.listeTacheDonnees.get(Main.listeTacheDonnees.size() - 1).getIdTache();
					System.out.println(id);
					t.setIdTache(id + 1);
				}
				t.setEtat((task_state.getSelectionModel().getSelectedItem().toString()));
				t.setPriorite((task_priority.getSelectionModel().getSelectedItem().toString()));

				// Get the personne
				for (int i = 0; i < Main.listePersonne.size(); i++) {
					if (task_affecte.getSelectionModel().getSelectedItem() == Main.listePersonne.get(i)
							.getNomPersonne())
						t.setAffecte(Main.listePersonne.get(i));
				}
				Main.listeTacheDonnees.add(t);

				Stage stage = null;
				Parent root = null;
				stage = (Stage) task_valid.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}

		}

		// Sur la page register teste d'enregistrement
		if (event.getSource() == register) {
			// Le mot de passe et la r�p�tition du mot de passe sont les m�mes
			if (password.getText().equals(password_repeat.getText())) {
				client.sendUserConnexionNew(username.getText(), client.md5(password.getText()));

				// Attente r�ponse
				while (!client.getIn().readLine().equals("CONNEXION"))
					System.out.println("Attente r�ponse serveur");
				Connex connex = Connex.valueOf(client.getIn().readLine());

				switch (connex) {
				// Le nom et le mot de passe corresponde
				case OK:
					Main.ReceiveUserList(client.getInput());
					client.getOut().println("ME");
					Main.ReceiveUser(client.getInput());
					client.getOut().println("OK");

					// R�ception de la liste � faire par me
					while (!client.getIn().readLine().equals("LISTEAFAIRE"))
						System.out.println("Attente du serveur");
					if (client.getIn().equals("ENVOI"))
						Main.ReceiveListTacheAfaire(client.getInput());
					client.getOut().println("OK");

					// R�ception de la liste des taches cr��es par me
					while (!client.getIn().readLine().equals("LISTECREEE"))
						System.out.println("Attente du serveur");
					if (client.getIn().equals("ENVOI"))
						Main.ReceiveListTacheDonnees(client.getInput());
					client.getOut().println("OK");

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
					message.setText("Ce nom d'utilisateur est d�j� utilis�");
					break;
				default:
					break;
				}
			}
			// Ils ne sont pas identiques
			else
				instruction.setText("Les 2 mots de passe doivent �tre identiques");
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

		// Cliquer sur le bouton pour valider une tache en cours d'edition
		if (event.getSource() == edit_valid) {
			System.out.println("azeaze");
			Main.tActual.setNomTache(edit_name.getText());
			Main.tActual.setDescriptif(edit_content.getText());
			Main.tActual.setEtat((edit_state.getSelectionModel().getSelectedItem().toString()));
			Main.tActual.setPriorite((edit_priority.getSelectionModel().getSelectedItem().toString()));

			// Get the personne
			for (int i = 0; i < Main.listePersonne.size(); i++) {
				if (edit_affecte.getSelectionModel().getSelectedItem() == Main.listePersonne.get(i).getNomPersonne())
					Main.tActual.setAffecte(Main.listePersonne.get(i));
			}

			for (int j = 0; j < Main.listeTacheAFaire.size(); j++) {
				if (Main.tActual.getIdTache() == Main.listeTacheAFaire.get(j).getIdTache())
					Main.listeTacheAFaire.set(j, Main.tActual);
			}

			if (Main.tActual.getEtat() == "Termin�e") {
				for (int i = 0; i < Main.listeTacheAFaire.size(); i++) {
					if (Main.listeTacheAFaire.get(i).getIdTache() == Main.tActual.getIdTache()) {
						Main.listeTacheAFaire.remove(i);
					}
				}
			}
			Stage stage = null;
			Parent root = null;
			stage = (Stage) edit_valid.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("../Interface/accueil.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}

	}
}
