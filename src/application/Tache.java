package application;

import java.io.Serializable;

public class Tache implements Serializable{
	
	private String nomTache;
	private int idTache;
	private String Etat;
	private String priorite;
	private Personne createur;
	private Personne affecte;
	private String descriptif;
	
	public Tache(String nom, int id, Personne createur, Personne affecte, String des){
		setNomTache(nom);
		setIdTache(id);
		setCreateur(createur);
		setAffecte(affecte);
		setDescriptif(des);
	}
	
	public Tache(){
		
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
	public String getNomTache() {
		return nomTache;
	}

	/**
	 * Setter nom de la tache
	 * @param nom
	 */
	public void setNomTache(String nom) {
		this.nomTache = nom;
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

	/**
	 * Setter de l'état
	 * @return
	 */
	public String getEtat() {
		return Etat;
	}

	/**
	 * Getter de l'état
	 * @param etat
	 */
	public void setEtat(String etat) {
		Etat = etat;
	}

	/**
	 * Getter de la priorité
	 * @return
	 */
	public String getPriorite() {
		return priorite;
	}

	/**
	 * Setter de la priorite
	 * @param priorite
	 */
	public void setPriorite(String priorite) {
		this.priorite = priorite;
	}
	
	
}
