package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class Serveur {
	final public int PORT = 1501; // Numéro du port utilisé

	public static void main(String[] Args) {
		System.out.println("Début server");
		Serveur serveur = new Serveur();
		ServerSocket welcomeSocket = null;

		try {
			System.out.println("Création task");
			Tache t = new Tache("truc", 5, "A faire", "Haute", new Personne("a", 1), new Personne("c", 2),
					"Faire des truc");
			// Initialisation du parser XML pour le document user.xml
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			SAXParser sax = factory.newSAXParser();

			ParserUser handlerSAX = new ParserUser();
			sax.parse("src/user.xml", handlerSAX);

			// Initialisation du parser DOM pour écrire dans les documents xml
			WriterXMLUserDOM domUser = new WriterXMLUserDOM();
			WriterXMLTaskDOM domTask = new WriterXMLTaskDOM();

			System.out.println("Tentative ecriture dans file");
			System.out.println(t.getDescriptif());
			domTask.writeTask(t);
			domTask.removeTask("5");

			welcomeSocket = new ServerSocket(serveur.PORT);
			while (true) {
				System.out.println("Attente du client...");
				Socket aClient = welcomeSocket.accept(); // Attente du client

				new ActiviteServeur("act", aClient).start();
			}
		} catch (IOException e) {
			System.out.println("Error connexion");
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				welcomeSocket.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

}
