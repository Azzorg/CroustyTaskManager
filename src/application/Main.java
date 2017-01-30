package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static Personne me = new Personne();

	public static Personne p0 = new Personne(0, "Guy", "123456");
	public static Personne p1 = new Personne(1, "Daniel", "123456");
	public static Personne p2 = new Personne(2, "Jean", "123456");

	public static Tache t0 = new Tache("manger", 0, "En cours", "haute", p0, p1, "Il faut manger");
	public static Tache t1 = new Tache("dormir", 1, "En cours", "faible", p1, p2, "Il faut dormir");
	public static Tache t2 = new Tache("Faire les courses", 2, "En cours", "moyenne", p2, p0,
			"Il faut faire les courses");

	public static Tache tActual = new Tache();

	public static ArrayList<Personne> listePersonne = new ArrayList<Personne>();
	public static ArrayList<Tache> listeTacheAFaire = new ArrayList<Tache>();
	public static ArrayList<Tache> listeTacheDonnees = new ArrayList<Tache>();

	@SuppressWarnings("unchecked")
	public static void ReceiveUserList(InputStream in) {
		try {
			ObjectInputStream userObj = new ObjectInputStream(in);
			listePersonne = (((ArrayList<Personne>) userObj.readObject()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void ReceiveUser(InputStream in) {
		try {
			ObjectInputStream userObj = new ObjectInputStream(in);
			me = (((Personne) userObj.readObject()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void ReceiveListTacheAfaire(InputStream in){
		try {
			ObjectInputStream userObj = new ObjectInputStream(in);
			listeTacheAFaire = (((ArrayList<Tache>) userObj.readObject()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void ReceiveListTacheDonnees(InputStream in){
		try {
			ObjectInputStream userObj = new ObjectInputStream(in);
			listeTacheDonnees = (((ArrayList<Tache>) userObj.readObject()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane accueil = (GridPane) FXMLLoader.load(Main.class.getResource("../Interface/login.fxml"));
			Scene scene = new Scene(accueil);
			scene.setRoot(accueil);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("CTM");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		me = p0;

		/*
		 * listePersonne.add(p0); listePersonne.add(p1); listePersonne.add(p2);
		 */

		/*
		 * listeTache.add(t0); listeTache.add(t1); listeTache.add(t2);
		 */
		launch(args);
	}
}
