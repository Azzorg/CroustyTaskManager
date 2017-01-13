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
			
			//R�ception tache
			ObjectInputStream obj = new ObjectInputStream(in);
			
			Tache t1 = (Tache)obj.readObject();
			
			//Affichage de la tache
			System.out.println("Nom de la t�che : " + t1.getNom());
			System.out.println("Personne cr�ateur : " + t1.getCreateur().getNom());
			System.out.println("Personne affect�e : " + t1.getAffecte().getNom());
			System.out.println("Descriptif de la t�che : \n" + t1.getDescriptif());
			
			aClient.close();
		}
		catch(UnknownHostException e){
			System.out.println("Can't find host");
		}
		catch(IOException e){
			System.out.println("Error connecting to host");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
