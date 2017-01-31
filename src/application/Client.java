package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Client {
	final public int PORT = 1501;

	public static Socket aClient;
	private InputStream input;
	private OutputStream output;
	private BufferedReader in;
	private PrintStream out;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private static ArrayList<Personne> listUser = new ArrayList<>();

	/**
	 * To receive the users list Modify the ArrayList<Personne>
	 * 
	 * @param in
	 *            : InputStream
	 */
	@SuppressWarnings("unchecked")
	public void ReceiveUserList(InputStream in) {
		try {
			System.out.println("Début receive user list");
			ObjectInputStream userObj = new ObjectInputStream(in);

			System.out.println("new");
			setListUser(((ArrayList<Personne>) userObj.readObject()));
			System.out.println("readobject");
			userObj.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Tache> ReceiveListTache(Client c) throws IOException, ClassNotFoundException{
		ObjectInputStream userObj = new ObjectInputStream(c.getInput());
		ArrayList<Tache> list = new ArrayList<Tache>();
		System.out.println(c);
		list = (ArrayList<Tache>) userObj.readObject();
		userObj.close();
		System.out.println("truc");
		
		return list;
	}
	
	

	/**
	 * To send a user to the server
	 * 
	 * @param p
	 *            : Personne
	 * @param out
	 *            : OutputStream
	 */
	public void SendUser(Personne p, OutputStream out) {
		try {
			ObjectOutputStream userTosend = new ObjectOutputStream(out);
			userTosend.writeObject(p);
			userTosend.flush();
			userTosend.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * MD5 cryptography
	 * 
	 * @param original
	 *            String
	 * @return String
	 */
	public String md5(String original) {
		String secret = "";

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(original.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			secret = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return secret;
	}

	/**
	 * Connexion to server
	 */
	public void connexionServer() {
		try {
			aClient = new Socket("localhost", this.PORT);
			this.setInput(aClient.getInputStream());
			this.setOutput(aClient.getOutputStream());
			this.setIn(new BufferedReader(new InputStreamReader(input)));
			this.setOut(new PrintStream(output));
			this.setObjOut(new ObjectOutputStream(output));
			//getObjOut().flush();
			this.setObjIn(new ObjectInputStream(input));
			System.out.println("Connection");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendUserConnexionNotNew(String name, String pw) {
		this.out.println("CONNEXION\nNOTNEW\n" + name + "\n" + pw);
	}

	public void sendUserConnexionNew(String name, String pw) {
		this.out.println("CONNEXION\nNEW\n" + name + "\n" + pw);
	}

	/**
	 * Getter in
	 * 
	 * @return in : BufferedReader
	 */
	public BufferedReader getIn() {
		return in;
	}

	/**
	 * Setter in
	 * 
	 * @param in
	 *            : BufferedReader
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}

	/**
	 * Getter listUser
	 * 
	 * @return listUser : ArrayList<Personne>
	 */
	public static ArrayList<Personne> getListUser() {
		return listUser;
	}

	/**
	 * Setter listUser
	 * 
	 * @param listUser
	 *            : ArrayList<Personne>
	 */
	public static void setListUser(ArrayList<Personne> listUser) {
		Client.listUser = listUser;
	}
	
	public InputStream getInput(){
		return input;
	}
	
	public void setInput(InputStream s){
		this.input = s;
	}
	
	public void setOutput(OutputStream s){
		this.output = s;
	}
		
	
	public OutputStream getOutput(){
		return output;
	}
	
	public PrintStream getOut(){
		return out;
	}
	
	public void setOut(PrintStream s){
		out = s;
	}

	public ObjectInputStream getObjIn() throws IOException {
		//objIn.close();
		objIn = new ObjectInputStream(input);
		return objIn;
	}

	public void setObjIn(ObjectInputStream objIn) {
		this.objIn = objIn;
	}

	public ObjectOutputStream getObjOut() throws IOException {
		objOut = new ObjectOutputStream(getOutput());
		return objOut;
	}

	public void setObjOut(ObjectOutputStream objOut) {
		this.objOut = objOut;
	}
}
