package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	final public int PORT = 1501;
	
	public static void main(String []args){
		Client client = new Client();
		
		try{
			Socket aClient = new Socket("toto", client.PORT);
			InputStream in = aClient.getInputStream();
			OutputStream out = aClient.getOutputStream();
			//ecrire un octet
			out.write(42);
			PrintStream pout = new PrintStream(out);
			pout.println("Hello!");
			//Byte back = (byte) in.read();
			BufferedReader din = new BufferedReader(new InputStreamReader(in));
			String response = din.readLine();
			System.out.println(response);
			aClient.close();
		}
		catch(UnknownHostException e){
			System.out.println("Can't find host");
		}
		catch(IOException e){
			System.out.println("Error connecting to host");
		}
	}
}
