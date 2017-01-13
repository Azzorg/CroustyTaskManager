package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ActiviteServeur extends Thread {
	Socket clientSocket;

	public ActiviteServeur(String n, Socket s) {
		super(n);
		clientSocket = s;
		System.out.println("Constructeur activité serveur");
	}

	public void run() {
		System.out.println("Nouveau client");
		try {
			System.out.println("Client" + clientSocket.getLocalPort() + "accepté");
			// La connexion est établie
			InputStream in = clientSocket.getInputStream();
			OutputStream out = clientSocket.getOutputStream();
			BufferedReader din = new BufferedReader(new InputStreamReader(in));
			System.out.println(din.readLine());
			out.write(43);
			PrintStream pout = new PrintStream(out);
			pout.println("Goodbye");

			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
