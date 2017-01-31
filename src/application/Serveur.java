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
			
			welcomeSocket = new ServerSocket(serveur.PORT);
			while (true) {
				Socket aClient = welcomeSocket.accept(); // Attente du client
				System.out.println("Nouveau thread");
				new ActiviteServeur("act", aClient).start();
			}

		} catch (IOException /*| ParserConfigurationException | SAXException*/ e) {

		} /*finally {
			try {
				System.out.println("Fermeture socket serveur");
				welcomeSocket.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}*/
	}

}
