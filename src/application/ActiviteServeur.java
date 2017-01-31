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
	
	public void SendUser(Personne me, OutputStream out){
		try {
			ObjectOutputStream userObj = new ObjectOutputStream(out);
			userObj.writeObject(me);
			userObj.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SendListTaskToDo(Personne p, OutputStream out){
		try {
			ParserTache parser = new ParserTache();
			List <Tache> list = parser.getListTacheAffecteById(p.getIdPersonne());
			ObjectOutputStream userObj = new ObjectOutputStream(out);
			userObj.writeObject(list);
			userObj.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SendListTaskGiven(Personne p, OutputStream out){
		try {
			ParserTache parser = new ParserTache();
			List <Tache> list = parser.getListTacheCreateurById(p.getIdPersonne());
			ObjectOutputStream userObj = new ObjectOutputStream(out);
			userObj.writeObject(list);
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
	 * Run method no parameters needed :)
	 */
	public void run() {
		Connexion connexion;
		Action action;
		List<Tache> tacheCreate = new ArrayList<Tache>();
		List<Tache> tacheAff = new ArrayList<Tache>();
		Personne me = null;
		
		boolean goOn = false;
		System.out.println("Nouveau client");
		try {
			System.out.println("Client " + clientSocket.getLocalAddress() + " accepté");

			// La connexion est établie
			OutputStream output = clientSocket.getOutputStream();
			InputStream input = clientSocket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			PrintStream out = new PrintStream(output);
			ObjectOutputStream objOut = new ObjectOutputStream(output);
			//objOut.flush();
			ObjectInputStream objIn = new ObjectInputStream(input);

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
			
			ParserTache taskXML = new ParserTache(handlerSAX);
			saxTask.parse("src/tache.xml", taskXML);

			// Initialisation du parser DOM pour écrire dans les documents xml
			WriterXMLUserDOM domUser = new WriterXMLUserDOM();
			WriterXMLTaskDOM domTask = new WriterXMLTaskDOM();

			
			//Connexion
			while (!goOn) {
				// Attente des instructions de connection
				System.out.println("Attente demande connexion");
				while (!in.readLine().equals("CONNEXION")) 
					System.out.println("Attente du client " + clientSocket.getLocalAddress());
				
				System.out.println("renvoi connexion");
				connexion = Connexion.valueOf(in.readLine());
				String nom = in.readLine();
				String pass = in.readLine();

				// Options en fonction du type de connexion
				switch (connexion) {

				// Création d'un nouveau compte
				case NEW:
					// Vérification si le nom existe déjà dans le document XML des users
					if (handlerSAX.nameExist(nom))
						out.println("CONNEXION\nNOTOK");
					else {
						me = new Personne(handlerSAX.getListUser().get(handlerSAX.getListUser().size()-1).getIdPersonne()+1, nom, pass);
						domUser.writeUser(me);
						out.println("CONNEXION\nOK");
						isConnected = true;
						goOn = true;
					}
					break;

				// Connexion avec un compte déjà existant
				case NOTNEW:
					// Vérification que le nom correspond au mot de passe envoyé
					if (handlerSAX.correspondanceNamePassword(nom, pass)) {
						me = handlerSAX.getPersonneByName(nom);
						System.out.println(me);
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
			
			while(!in.readLine().equals("OK"))
				System.out.println("Attente client");

			// Récupération de tous les users dans le
			List<Personne> listUser = handlerSAX.getListUser();
			// Send the user list
			SendUserList(listUser, output);
			
			System.out.println("envoi 1 fait : " + listUser.size());
			
			while(!in.readLine().equals("ME"))
				System.out.println("Attente client");

			//Thread.sleep(1000);
			System.out.println("ME reçu");
			
			//Envoi de la personne connectée
			SendUser(me, output);
			
			System.out.println("envoi 2 fait");
			
			while(!in.readLine().equals("OK"))
				System.out.println("Attente client");
			
			System.out.println(me);
			
			//Envoi de la liste de tache à faire par la personne connectée
			if(taskXML.getListTacheCreateurById(me.getIdPersonne()) == null)
				out.println("LISTEAFAIRE\nNULL");
			else{
				out.println("LISTEAFAIRE\nENVOI");
				while(!in.readLine().equals("OK"))
					System.out.println("attente du client");
				SendListTaskToDo(me, output);
			}
			
			
			while(!in.readLine().equals("OK"))
				System.out.println("Attente client");
			
			//Envoi de la liste de tache créées par la personne connectée
			if(taskXML.getListTacheCreateurById(me.getIdPersonne()) == null)
				out.println("LISTECREEE\nNULL");
			else{
				out.println("LISTECREEE\nENVOI");
				while(!in.readLine().equals("OK"))
					System.out.println("attente du client");
				SendListTaskGiven(me, output);
			}
			
			while(!in.readLine().equals("OK"))
				System.out.println("Attente client");
			
			// Une fois la personne connectée
			while(isConnected){
				System.out.println("isConnected " + isConnected);
				while(!in.readLine().equals("ACTION")){
					System.out.println("Attente client");
				}
				action = Action.valueOf(in.readLine());
				switch(action){
				//Déconnexion du client 
				case DISCONNECT:
					isConnected = false;
					out.println("ACTION\nOK");
					break;
				//Création d'une nouvelle tache
				case NEW_TASK:
					/*Tache newTask = (Tache) objIn.readObject(); 
					newTask.setIdTache(taskXML.getListTache().get(taskXML.getListTache().size()).getIdTache() + 1);
					domTask.writeTask(newTask);*/
					break;
					
				// Envoie d'une liste de tache en fonction de l'utilisateur
				case LIST_TASK:
					System.out.println("list ache");
					int idPers = Integer.parseInt(in.readLine());
					System.out.println("Envoie liste des taches" + idPers);
					//Envoie de la liste createur
					objOut.writeObject(taskXML.getListTacheCreateurById(idPers));
					System.out.println("Envoi OK 1 ");
					objOut.flush();
					System.out.println("Attente ///");
					//Attente de la bonne réception du client
					while(!in.readLine().equals("OK"))
						System.out.println("Attente du client");
					System.out.println("Attente OK");
					//Attente de la liste affecte
					System.out.println("Envpi 2 ");
					objOut.writeObject(taskXML.getListTacheAffecteById(idPers));
					System.out.println("envoi 2 ok");
					objOut.flush();
					break;
					
				//Suppression d'une tache 
				case SUPPRESS_TASK:
					domTask.removeTask(in.readLine());
					out.println("ACTION\nOK");
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

			
		} catch (IOException | ParserConfigurationException | SAXException /*| ClassNotFoundException*/ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
