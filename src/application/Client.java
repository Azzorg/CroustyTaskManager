package application;

import java.io.EOFException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.security.jca.GetInstance;
import sun.security.provider.MD5;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	final public int PORT = 1501;
	private static Socket aClient;
	
	public static void main(String []args){
		Client client = new Client();
		
		try{
			aClient = new Socket("localhost", client.PORT);
			InputStream in = aClient.getInputStream();
			OutputStream out = aClient.getOutputStream();
			Personne user;
			
			//Réception de tous les users
			

			System.out.println("Affichage de tous les users ...");
			
			ObjectInputStream userObj = new ObjectInputStream(in);
			
			//userObj.mark(1000);
			
			
			//https://coderanch.com/t/277986/java/detect-file voir ce site
			while(((user = (Personne)userObj.readObject()) != null) && (user != null)){
				System.out.println("Id de la personne : " + user.getIdPersonne());
				System.out.println("Nom de la personne : " + user.getNomPersonne());
				
				userObj = new ObjectInputStream(in);
			}
			
			String original = "troll";
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(original.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			
			Personne p = new Personne(4,"René", sb.toString());
			
			ObjectOutputStream userTosend = new ObjectOutputStream(out);
			userTosend.writeObject(p);
			userTosend.flush();
			
			
			
			//Réception tache
			/*
			ObjectInputStream obj = new ObjectInputStream(in);
			
			Tache t1 = (Tache)obj.readObject();
			
			//Affichage de la tache
			
			System.out.println("Nom de la tâche : " + t1.getN	omTache());
			System.out.println("Personne créateur : " + t1.getCreateur().getNomPersonne());
			System.out.println("Personne affectée : " + t1.getAffecte().getNomPersonne());
			System.out.println("Descriptif de la tâche : \n" + t1.getDescriptif());
			*/
			
			
		}
		catch(UnknownHostException e){
			System.out.println("Can't find host");
		}
		catch(EOFException e){
			System.out.println("entre dans cette exception : " + e);
		}
		catch(IOException e){
			System.out.println("Error connecting to host : " + e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				aClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
