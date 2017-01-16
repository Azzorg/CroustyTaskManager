package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	final public int PORT = 1501;
	
	public static void main(String []args){
		Client client = new Client();
		
		try{
			Socket aClient = new Socket("localhost", client.PORT);
			InputStream in = aClient.getInputStream();
			Personne user;
			
			//Réception de tous les users
			

			System.out.println("Affichage de tous les users ...");
			
			int i = 0;
			while(i<3){
				ObjectInputStream userObj = new ObjectInputStream(in);
				user = (Personne)userObj.readObject();
				System.out.println("Id de la personne : " + user.getIdPersonne());
				System.out.println("Nom de la personne : " + user.getNomPersonne());
				i++;
			}
			
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
			
			aClient.close();
		}
		catch(UnknownHostException e){
			System.out.println("Can't find host");
		}
		catch(IOException e){
			System.out.println("Error connecting to host : " + e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
