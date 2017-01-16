package application;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ActiviteServeur extends Thread {
	Socket clientSocket;

	public ActiviteServeur(String n, Socket s) {
		super(n);
		clientSocket = s;
		System.out.println("Constructeur activité serveur");
	}

	public void run() {
		System.out.println("Nouveau client");
		try {
			int i =0;
			Thread.sleep(1000);
			System.out.println("Client " + clientSocket.getLocalAddress() + " accepté");
			// La connexion est établie
			OutputStream out = clientSocket.getOutputStream();

			// Parser user.xml
			System.out.println("Début parser user");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			SAXParser sax = factory.newSAXParser();

			ParserUser handlerSAX = new ParserUser();

			sax.parse("src/user.xml", handlerSAX);

			List<Personne> listUser = handlerSAX.getListUser();

			// Envoi de tous les users lu dans le fichier xml
			for (Personne user : listUser) {
				System.out.println("Envoie du user : " + user.getIdPersonne());
				ObjectOutputStream userObj = new ObjectOutputStream(out);
				userObj.writeObject(user);
				userObj.flush();
				i++;
			}

			// Creation d'un tache
			/*
			 * Tache t1 = new Tache("Faire les courses", 1, new
			 * Personne("Roger", 12), new Personne("Marcel", 30),
			 * "Liste : \n\t-pain\n\t-lait\n\t-lardon");
			 * 
			 * // Envoi de l'objet tache ObjectOutputStream obj = new
			 * ObjectOutputStream(out); obj.writeObject(t1); obj.flush();
			 */

			// Fermeture socket
			clientSocket.close();
		} catch (IOException | InterruptedException | ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
