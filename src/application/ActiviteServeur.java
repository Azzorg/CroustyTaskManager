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
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ActiviteServeur extends Thread {
	Socket clientSocket;
	boolean isConnected;
	

	private enum Connexion {
		NEW, NOTNEW;
	}
	
	private enum Action{
		LIST_TASK, NEW_TASK, SUPPRESS_TASK, DISCONNECT;
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
		Action action;
		List<Tache> tacheCreate = new ArrayList<Tache>();
		List<Tache> tacheAff = new ArrayList<Tache>();
		
		boolean goOn = false;
		System.out.println("Nouveau client");
		try {
			Thread.sleep(1000);
			System.out.println("Client " + clientSocket.getLocalAddress() + " accept�");

			// Initialisation du parser XML pour le document user.xml
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			SAXParser sax = factory.newSAXParser();

			ParserUser handlerSAX = new ParserUser();
			sax.parse("src/user.xml", handlerSAX);
			
			//Initialisation du parser pour le document tache.xml
			SAXParserFactory factoryTask = SAXParserFactory.newInstance();
			factoryTask.setNamespaceAware(true);
			SAXParser saxTask = factoryTask.newSAXParser();
			
			ParserTache taskXML = new ParserTache();
			saxTask.parse("src/tache.xml", taskXML);

			// Initialisation du parser DOM pour �crire dans les documents xml
			WriterXMLUserDOM domUser = new WriterXMLUserDOM();
			WriterXMLTaskDOM domTask = new WriterXMLTaskDOM();

			// La connexion est �tablie
			OutputStream output = clientSocket.getOutputStream();
			InputStream input = clientSocket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			PrintStream out = new PrintStream(output);
			ObjectInputStream objIn = new ObjectInputStream(input);
			ObjectOutputStream objOut = new ObjectOutputStream(output);

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

				// Cr�ation d'un nouveau compte
				case NEW:

					// V�rification si le nom existe d�j� dans le document XML
					// des users
					if (handlerSAX.nameExist(nom))
						out.println("CONNEXION\nNOTOK");
					else {
						domUser.writeUser(p);
						out.println("CONNEXION\nOK");
						isConnected = true;
						goOn = true;
					}
					break;

				// Connexion avec un compte d�j� existant
				case NOTNEW:
					// V�rification que le nom correspond au mot de passe envoy�
					if (handlerSAX.correspondanceNamePassword(nom, pass)) {
						System.out.println("ok");
						out.println("CONNEXION\nOK");
						isConnected = true;
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

			// R�cup�ration de tous les users dans le
			List<Personne> listUser = handlerSAX.getListUser();

			// Send the user list
			SendUserList(listUser, output);
			
			//
			while(isConnected){
				while(!in.readLine().equals("ACTION")){
					System.out.println("Attente client");
				}
				
				action = Action.valueOf(in.readLine());
				switch(action){
				//D�connexion du client 
				case DISCONNECT:
					isConnected = false;
					out.println("ACTION\nOK");
					break;
				//Cr�ation d'une nouvelle tache
				case NEW_TASK:
					Tache newTask = (Tache) objIn.readObject(); 
					newTask.setIdTache(taskXML.getListTache().get(taskXML.getListTache().size()).getIdTache() + 1);
					domTask.writeTask(newTask);
					break;
				//Envoie d'une liste de tache en fonction de l'utilisateur
				case LIST_TASK:
					//Envoie de la liste createur
					objOut.writeObject(taskXML.getListTacheCreateurById(Integer.parseInt(in.readLine())));
					objOut.flush();
					//Attente de la bonne r�ception du client
					while(!in.readLine().equals("OK"))
						System.out.println("Attente du client");
					//Attente de la liste affecte
					objOut.writeObject(taskXML.getListTacheAffecteById(Integer.parseInt(in.readLine())));
					objOut.flush();
					break;
				//Suppression d'une tache 
				case SUPPRESS_TASK:
					domTask.removeTask(in.readLine());
				default:
					System.out.println("Action non prise en compte");
					break;
				}
			}
			
			

			// Creation d'un tache
			/*
			 * Tache t1 = new Tache("Faire les courses", 1, new
			 * Personne("Roger", 12), new Personne("Marcel", 30),
			 * "Liste : \n\t-pain\n\t-lait\n\t-lardon");
			 * 
			 * // Envoi de l'objet tache ObjectOutputStream obj = new
			 * ObjectOutputStream(output); obj.writeObject(t1); obj.flush();
			 */

			
		} catch (IOException | InterruptedException | ParserConfigurationException | SAXException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			// Fermeture socket
			try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
