package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ActiviteServeur extends Thread{
	Socket clientSocket;
	
	public ActiviteServeur(String n, Socket s){
		super(n);
		clientSocket = s;
		
	}
	
	public void Run(){
		System.out.println("Nouveau client");
	
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
