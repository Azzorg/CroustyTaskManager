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
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Client {
	final public int PORT = 1501;

	private static Socket aClient;
	private InputStream input;
	private OutputStream output;
	private BufferedReader in;
	private PrintStream out;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private static ArrayList<Personne> listUser = new ArrayList<>();

	/**
	 * To receive the users list Modify the ArrayList<Personne>
	 * 
	 * @param in
	 *            : InputStream
	 */
	@SuppressWarnings("unchecked")
	public void ReceiveUserList(InputStream in) {
		try {
			ObjectInputStream userObj = new ObjectInputStream(in);
			setListUser(((ArrayList<Personne>) userObj.readObject()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	/**
	 * To send a user to the server
	 * 
	 * @param p
	 *            : Personne
	 * @param out
	 *            : OutputStream
	 */
	public void SendUser(Personne p, OutputStream out) {
		try {
			ObjectOutputStream userTosend = new ObjectOutputStream(out);
			userTosend.writeObject(p);
			userTosend.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * MD5 cryptography
	 * 
	 * @param original
	 *            String
	 * @return String
	 */
	public String md5(String original) {
		String secret = "";

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(original.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			secret = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return secret;
	}

	/**
	 * Connexion to server
	 */
	public void connexionServer() {
		try {
			aClient = new Socket("localhost", this.PORT);
			this.input = aClient.getInputStream();
			this.output = aClient.getOutputStream();
			this.setIn(new BufferedReader(new InputStreamReader(input)));
			this.out = new PrintStream(output);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendUserConnexionNotNew(String name, String pw) {
		this.out.println("CONNEXION\nNOTNEW\n" + name + "\n" + pw);
	}

	public void sendUserConnexionNew(String name, String pw) {
		this.out.println("CONNEXION\nNEW\n" + name + "\n" + pw);
	}

	public static void main(String[] args) {
		/*
		 * Client client = new Client();
		 * 
		 * try { client.connexionServer();
		 * 
		 * // Réception de la liste des Users client.ReceiveUserList(client.in);
		 * 
		 * System.out.println("Affichage de tous les users ..."); for (Personne
		 * user : listUser) { System.out.println("Id de la personne : " +
		 * user.getIdPersonne()); System.out.println("Nom de la personne : " +
		 * user.getNomPersonne()); }
		 * 
		 * Personne p = new Personne(4, "René", client.md5("troll"));
		 * 
		 * System.out.println("Envoi coordonnées nouveau user");
		 * client.SendUser(p, client.out);
		 * 
		 * // Réception tache /* ObjectInputStream obj = new
		 * ObjectInputStream(in);
		 * 
		 * Tache t1 = (Tache)obj.readObject();
		 * 
		 * //Affichage de la tache
		 * 
		 * System.out.println("Nom de la tâche : " + t1.getNomTache());
		 * System.out.println("Personne créateur : " +
		 * t1.getCreateur().getNomPersonne());
		 * System.out.println("Personne affectée : " +
		 * t1.getAffecte().getNomPersonne());
		 * System.out.println("Descriptif de la tâche : \n" +
		 * t1.getDescriptif());
		 */

		/*
		 * } finally { try { aClient.close(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 */
	}

	/**
	 * Getter in
	 * 
	 * @return in : BufferedReader
	 */
	public BufferedReader getIn() {
		return in;
	}

	/**
	 * Setter in
	 * 
	 * @param in
	 *            : BufferedReader
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}

	/**
	 * Getter listUser
	 * 
	 * @return listUser : ArrayList<Personne>
	 */
	public static ArrayList<Personne> getListUser() {
		return listUser;
	}

	/**
	 * Setter listUser
	 * 
	 * @param listUser
	 *            : ArrayList<Personne>
	 */
	public static void setListUser(ArrayList<Personne> listUser) {
		Client.listUser = listUser;
	}
	
	public InputStream getInput(){
		return input;
	}
	
	public OutputStream getOutput(){
		return output;
	}
	
	public PrintStream getOut(){
		return out;
	}



	public ObjectInputStream getObjIn() {
		return objIn;
	}



	public void setObjIn(ObjectInputStream objIn) {
		this.objIn = objIn;
	}



	public ObjectOutputStream getObjOut() {
		return objOut;
	}



	public void setObjOut(ObjectOutputStream objOut) {
		this.objOut = objOut;
	}
}
