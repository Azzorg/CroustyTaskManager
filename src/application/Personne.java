package application;

import java.io.Serializable;

public class Personne implements Serializable{
	private int idPersonne;
	private String nom;
	
	/**
	 * Constructeur Personne
	 * @param n : nom
	 * @param id : idPersonne
	 */
	public Personne(String n, int id){
		setNom(n);
		setIdPersonne(id);
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
	public String getNom() {
		return nom;
	}
	
	/**
	 * Setter nom
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
}
