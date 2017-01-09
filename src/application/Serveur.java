package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur 
{
	final public int PORT = 1501;
	
	public static void main (String[] Args)
	{
		Serveur serveur = new Serveur();
		try
		{
			ServerSocket listener = new ServerSocket(serveur.PORT);
			System.out.println("Attente du client");
			Socket aClient = listener.accept();	
			System.out.println("Client" + aClient.getLocalPort() + "accept�");
			// La connexion est �tablie
			InputStream in = aClient.getInputStream();
			BufferedReader din = new BufferedReader(new InputStreamReader (in));
			System.out.println(din.read());
			listener.close();
		}
		catch (IOException e){
			System.out.println("Error connexion");
			e.printStackTrace();
		}
	}

}
 