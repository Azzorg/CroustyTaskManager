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
		System.out.println("Lancement serveur");
		Serveur serveur = new Serveur();
		ServerSocket welcomeSocket = null;

		try {
<<<<<<< HEAD
			/*System.out.println("Création task");
			Tache t = new Tache("truc", 5, new Personne("a", 1), new Personne("c", 2), "Faire des truc");

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
			domTask.writeTask(t);*/

=======
>>>>>>> branch 'master' of https://github.com/Azzorg/CroustyTaskManager.git
			welcomeSocket = new ServerSocket(serveur.PORT);
			while (true) {
				Socket aClient = welcomeSocket.accept(); // Attente du client
				new ActiviteServeur("act", aClient).start();
			}
<<<<<<< HEAD
		} catch (IOException /*| ParserConfigurationException | SAXException*/ e) {
=======
		} catch (IOException e) {
>>>>>>> branch 'master' of https://github.com/Azzorg/CroustyTaskManager.git
			System.out.println("Error connexion");
			e.printStackTrace();
		} finally {
			try {
				System.out.println("Fermeture socket serveur");
				welcomeSocket.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
