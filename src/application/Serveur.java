package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur 
{
	final public int PORT = 1501;				//Numéro du port utilisé
	
	public static void main (String[] Args)
	{
		Serveur serveur = new Serveur();
		ServerSocket welcomeSocket = null;
		ActiviteServeur thread;
		
		try
		{
			welcomeSocket = new ServerSocket(serveur.PORT);
			while(true){
				System.out.println("Attente du client...");
				Socket aClient = welcomeSocket.accept();	//Attente du client
				
				thread = new ActiviteServeur("act", aClient);
				thread.start();
			}
		}
		catch (IOException e){
			System.out.println("Error connexion");
			e.printStackTrace();
		}
		finally{
			try{
				welcomeSocket.close();
			}
			catch(Exception e){
				System.out.println(e);
			}
			
		}
	}

}
 