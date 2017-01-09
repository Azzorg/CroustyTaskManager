package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur 
{
	final public int PORT = 1501;
	
	public static void main (String[] Args)
	{
		Serveur serveur = new Serveur();
		ServerSocket listener = null;
		try
		{
			listener = new ServerSocket(serveur.PORT);
			while(true){
				System.out.println("Attente du client...");
				Socket aClient = listener.accept();	//Attente du client
				System.out.println("Client" + aClient.getLocalPort() + "accepté");
				// La connexion est établie
				InputStream in = aClient.getInputStream();
				OutputStream out = aClient.getOutputStream();
				BufferedReader din = new BufferedReader(new InputStreamReader (in));
				System.out.println(din.readLine());
				out.write(43);
				PrintStream pout = new PrintStream(out);
				pout.println("Goodbye");
				aClient.close();
			}
		}
		catch (IOException e){
			System.out.println("Error connexion");
			e.printStackTrace();
		}
		finally{
			try{
				listener.close();
			}
			catch(Exception e){
				System.out.println(e);
			}
			
		}
	}

}
 