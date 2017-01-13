package application;

import java.io.Serializable;

public class Tache implements Serializable{
	private String nom;
	private int idTache;
	private Personne createur;
	private Personne affecte;
	private String descriptif;
	
	public Tache(String nom, int id, Personne createur, Personne affecte, String des){
		setNom(nom);
		setIdTache(id);
		setCreateur(createur);
		setAffecte(affecte);
		setDescriptif(des);
	}

	/**
	 * Getter de la personne qui a créé la tache
	 * @return
	 */
	public Personne getCreateur() {
		return createur;
	}

	/**
	 * Setter de la personne qui a créé la tache
	 * @param createur
	 */
	public void setCreateur(Personne createur) {
		this.createur = createur;
	}

	/**
	 * Getter de la personne à qui est affecté la tache
	 * @return
	 */
	public Personne getAffecte() {
		return affecte;
	}

	/**
	 * Setter de la personne à qui est affecté la tache
	 * @param affecte
	 */
	public void setAffecte(Personne affecte) {
		this.affecte = affecte;
	}

	/**
	 * Getter nom de la tache
	 * @return
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter nom de la tache
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter Id de la tache
	 * @return
	 */
	public int getIdTache() {
		return idTache;
	}

	/**
	 * Setter Id de la tache
	 * @param idTache
	 */
	public void setIdTache(int idTache) {
		this.idTache = idTache;
	}

	/**
	 * Getter du descriptif de la tache
	 * @return
	 */
	public String getDescriptif() {
		return descriptif;
	}

	/**
	 * Setter du descriptif de la tache
	 * @param descriptif
	 */
	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}
	
	
}
