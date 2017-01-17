package application;

import java.io.Serializable;

public class Personne implements Serializable{
	private int idPersonne;
	private String nomPersonne;
	private String passWord;
	
	/**
	 * Constructeur Personne, seul le nom et l'id
	 * @param n : nomPersonne
	 * @param id : idPersonne
	 */
	public Personne(String n, int id){
		setNomPersonne(n);
		setIdPersonne(id);
	}
	
	/**
	 * Constructeur de Personne avec tous les champs remplis
	 * @param id : idPersonne
	 * @param n : nomPersonne
	 * @param p : passWord
	 */
	public Personne(int id, String n, String p){
		this(n,id);
		setPassWord(p);
	}
	
	/**
	 * Constructeur personne vide
	 */
	public Personne(){
		this(-1, "", "");
	}
	
	/**
	 * Getter idPersonne
	 * @return
	 */
	public int getIdPersonne() {
		return idPersonne;
	}
	
	/**
	 * Setter idPersonne
	 * @param idPersonne
	 */
	public void setIdPersonne(int idPersonne) {
		this.idPersonne = idPersonne;
	}
	
	/**
	 * Getter nom
	 * @return
	 */
	public String getNomPersonne() {
		return nomPersonne;
	}
	
	/**
	 * Setter nom
	 * @param nom
	 */
	public void setNomPersonne(String nom) {
		this.nomPersonne = nom;
	}

	/**
	 * Getter passWord
	 * @return
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * Setter passWord
	 * @param passWord
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	
}
