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
	private Personne me = new Personne();
	
	public ArrayList<Personne> listPersonne = new ArrayList<Personne>();
	public ArrayList<Tache> listeTacheAFaire = new ArrayList<Tache>();
	public ArrayList<Tache> listeTacheDonnees = new ArrayList<Tache>();

	/**
	 * To receive the users list Modify the ArrayList<Personne>
	 * 
	 * @param c
	 *            : Client
	 */
	public void ReceiveUserList() {
		String rec;
		Personne act;
		String [] sp = null;
		Personne p = null;
		this.listPersonne.clear();
		try {
			while((rec = this.getIn().readLine()) != null){
				
				System.out.println(rec);
				p = new Personne();
				sp = rec.split("§");
				this.listPersonne.add(p);
				act = this.listPersonne.get(this.listPersonne.size()-1);
				act.setIdPersonne(Integer.parseInt(sp[0]));
				act.setNomPersonne(sp[1]);
				act.setPassWord(sp[2]);
			}
			System.out.println("list user : " + listPersonne.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Tache> ReceiveListTache() {
		ArrayList<Tache> list = new ArrayList<Tache>();
		Tache t = null;
		Tache act = null;
		String [] sp = null;
		String rec;
		try {
			while((rec = this.getIn().readLine()) != null){
				t = new Tache();
				sp = rec.split("§");
				list.add(t);
				act = list.get(list.size()-1);
				act.setIdTache(Integer.parseInt(sp[0]));
				act.setNomTache(sp[1]);
				act.setEtat(sp[2]);
				act.setPriorite(sp[3]);
				act.setDescriptif(sp[4]);
				act.getAffecte().setIdPersonne(Integer.parseInt(sp[5]));
				act.getAffecte().setNomPersonne(sp[6]);
				act.getAffecte().setPassWord(sp[7]);
				act.getCreateur().setIdPersonne(Integer.parseInt(sp[8]));
				act.getCreateur().setNomPersonne(sp[9]);
				act.getCreateur().setPassWord(sp[9]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void SendUser(Personne p, PrintStream out) {
		out.println(p);
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
	public ArrayList<Personne> getListUser() {
		return listPersonne;
	}

	/**
	 * Setter listUser
	 * 
	 * @param listUser
	 *            : ArrayList<Personne>
	 */
	public void setListUser(ArrayList<Personne> listUser) {
		this.listPersonne = listUser;
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

	public Personne getMe() {
		return me;
	}

	public void setMe(Personne me) {
		this.me = me;
	}
}
