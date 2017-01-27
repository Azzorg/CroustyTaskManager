package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ActiviteServeur extends Thread {
	Socket clientSocket;

	private enum Connexion {
		NEW, NOTNEW;
	}

	/**
	 * Constuctor of ActiviteServeur with 2 params
	 * 
	 * @param n
	 *            : String : Name of the Thread
	 * @param s
	 *            : Socket
	 */
	public ActiviteServeur(String n, Socket s) {
		super(n);
		clientSocket = s;
	}

	/**
	 * Send the user list to the client
	 * 
	 * @param listUser
	 *            : List<Personne>
	 * @param out
	 *            : OutputStream
	 */
	public void SendUserList(List<Personne> listUser, OutputStream out) {
		try {
			ObjectOutputStream userObj = new ObjectOutputStream(out);
			userObj.writeObject(listUser);
			userObj.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param in
	 *            : InputStream
	 * @return
	 */
	public Personne ReceiveNewUser(InputStream in) {
		Personne user = null;
		ObjectInputStream userObj;
		try {
			userObj = new ObjectInputStream(in);
			user = (Personne) userObj.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * Run method
	 */
	public void run() {
		Connexion connexion;
		boolean goOn = false;
		System.out.println("Nouveau client");
		try {
			Thread.sleep(1000);
			System.out.println("Client " + clientSocket.getLocalAddress() + " accepté");

			// Initialisation du parser XML pour le document user.xml
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			SAXParser sax = factory.newSAXParser();

			ParserUser handlerSAX = new ParserUser();
			sax.parse("src/user.xml", handlerSAX);

			// Initialisation du parser DOM pour écrire dans les documents xml
			WriterXMLUserDOM domUser = new WriterXMLUserDOM();
			WriterXMLTaskDOM domTask = new WriterXMLTaskDOM();

			// La connexion est établie
			OutputStream output = clientSocket.getOutputStream();
			InputStream input = clientSocket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			PrintStream out = new PrintStream(output);

			while (!goOn) {
				// Attente des instructions de connection
				while (!in.readLine().equals("CONNEXION")) {
					System.out.println("Attente du client " + clientSocket.getLocalAddress());
				}
				connexion = Connexion.valueOf(in.readLine());
				String nom = in.readLine();
				String pass = in.readLine();
				Personne p = new Personne(handlerSAX.getListUser().size() + 1, nom, pass);

				// Options en fonction du type de connexion
				switch (connexion) {

				// Création d'un nouveau compte
				case NEW:

					// Vérification si le nom existe déjà dans le document XML
					// des users
					if (handlerSAX.nameExist(nom))
						out.println("CONNEXION\nNOTOK");
					else {
						domUser.writeUser(p);
						out.println("CONNEXION\nOK");
						goOn = true;
					}
					break;

				// Connexion avec un compte déjà existant
				case NOTNEW:
					// Vérification que le nom correspond au mot de passe envoyé
					if (handlerSAX.correspondanceNamePassword(nom, pass)) {
						System.out.println("ok");
						out.println("CONNEXION\nOK");
						goOn = true;
					} else
						out.println("CONNEXION\nNOTOK");

					break;
				default:
					System.out.println("Erreur du message de connexion");
					break;
				}
			}

			goOn = false;

			// Récupération de tous les users dans le
			List<Personne> listUser = handlerSAX.getListUser();

			// Send the user list
			SendUserList(listUser, output);

			// Receive a new user
			Personne user = ReceiveNewUser(input);

			System.out.println("Id personne : " + user.getIdPersonne());
			System.out.println("Nom personne : " + user.getNomPersonne());
			System.out.println("Mot de passe : " + user.getPassWord());

			// Creation d'un tache
			/*
			 * Tache t1 = new Tache("Faire les courses", 1, new
			 * Personne("Roger", 12), new Personne("Marcel", 30),
			 * "Liste : \n\t-pain\n\t-lait\n\t-lardon");
			 * 
			 * // Envoi de l'objet tache ObjectOutputStream obj = new
			 * ObjectOutputStream(output); obj.writeObject(t1); obj.flush();
			 */

			// Fermeture socket
			clientSocket.close();
		} catch (IOException | InterruptedException | ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
