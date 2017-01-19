package application;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Client {
	final public int PORT = 1501;

	private static Socket aClient;
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
			listUser = ((ArrayList<Personne>) userObj.readObject());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SendUser(Personne p, OutputStream out){
		try{
			ObjectOutputStream userTosend = new ObjectOutputStream(out);
			userTosend.writeObject(p);
			userTosend.flush();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	

	public static void main(String[] args) {
		Client client = new Client();

		try {
			aClient = new Socket("localhost", client.PORT);
			InputStream in = aClient.getInputStream();
			OutputStream out = aClient.getOutputStream();

			// Réception de la liste des Users
			client.ReceiveUserList(in);

			System.out.println("Affichage de tous les users ...");
			for (Personne user : listUser) {
				System.out.println("Id de la personne : " + user.getIdPersonne());
				System.out.println("Nom de la personne : " + user.getNomPersonne());
			}

			String original = "troll";
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(original.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}

			Personne p = new Personne(4, "René", sb.toString());

			System.out.println("Envoi coordonnées nouveau user");
			client.SendUser(p, out);

			// Réception tache
			/*
			 * ObjectInputStream obj = new ObjectInputStream(in);
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

		} catch (UnknownHostException e) {
			System.out.println("Can't find host");
		} catch (EOFException e) {
			System.out.println("entre dans cette exception : " + e);
		} catch (IOException e) {
			System.out.println("Error connecting to host : " + e);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				aClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
