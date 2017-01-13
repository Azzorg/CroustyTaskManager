package application;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
			System.out.println("Client " + clientSocket.getLocalAddress() + " accepté");
			// La connexion est établie
			OutputStream out = clientSocket.getOutputStream();

			// Creation d'un tache
			Tache t1 = new Tache("Faire les courses", 1, new Personne("Roger", 12), new Personne("Marcel", 30),
					"Liste : \n\t-pain\n\t-lait\n\t-lardon");

			// Envoi de l'objet tache
			ObjectOutputStream obj = new ObjectOutputStream(out);
			obj.writeObject(t1);
			obj.flush();

			//Fermeture socket
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
