package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur 
{
	public void main (String[] Args)
	{
	try
	{
		ServerSocket listener = new ServerSocket(1500);
		Socket aClient = listener.accept();	
		// La connexion est établie
		InputStream in = aClient.getInputStream();
		BufferedReader din = new BufferedReader(new InputStreamReader (in));
		System.out.println(din.read());
		listener.close();
	}
	catch (IOException e){}
	}

}
 