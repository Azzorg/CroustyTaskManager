package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Serveur 
{
	final public int PORT = 1501;				//Num�ro du port utilis�
	private Vector listClient = new Vector();	//Liste des clients connect�s
	
	public static void main (String[] Args)
	{
		
		Serveur serveur = new Serveur();
		ServerSocket welcomeSocket = null;
		
		
		try
		{
			welcomeSocket = new ServerSocket(serveur.PORT);
			while(true){
				System.out.println("Attente du client...");
				Socket aClient = welcomeSocket.accept();	//Attente du client
				
				new ActiviteServeur("act", aClient).start();
				
				/**
				 * Truc � mettre dans le thread
				System.out.println("Client" + aClient.getLocalPort() + "accept�");
				// La connexion est �tablie
				InputStream in = aClient.getInputStream();
				OutputStream out = aClient.getOutputStream();
				BufferedReader din = new BufferedReader(new InputStreamReader (in));
				System.out.println(din.readLine());
				out.write(43);
				PrintStream pout = new PrintStream(out);
				pout.println("Goodbye");
				aClient.close();*/
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
 