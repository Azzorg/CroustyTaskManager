package application;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserTache extends DefaultHandler {
	private List<Tache> listTache = new ArrayList<Tache>();
	private Tache task;
	private String balise;
	private ParserUser parser;

	/**
	 * Constructeur vide
	 */
	public ParserTache() {
	}

	/**
	 * Constructeur prenant un parser user en param�tre
	 * @param p
	 */
	public ParserTache(ParserUser p) {
		this.parser = p;
	}

	
	/**
	 * Return la liste des taches
	 * @return
	 */
	public List<Tache> getListTache() {
		return listTache;
	}
	
	/**
	 * Retourne la liste de toutes les tache cr��e par une personne (id)
	 * @param id
	 * @return
	 */
	public List<Tache> getListTacheCreateurById(int id){
		List<Tache> listTache = new ArrayList<>();
		List<Tache> list = getListTache();
		
		for(Tache task : list){
			if(task.getCreateur().getIdPersonne() == id)
				listTache.add(task);
		}
		
		return listTache;
	}
	
	/**
	 * Retourne la liste de toutes les tache cr��e par une personne (nom)
	 * @param name
	 * @return
	 */
	public List<Tache> getListTacheCreateurByName(String name){
		List<Tache> listTache = new ArrayList<>();
		List<Tache> list = getListTache();
		
		for(Tache task : list){
			if(task.getCreateur().getNomPersonne().equals(name))
				listTache.add(task);
		}
		
		return listTache;
	}
	
	/**
	 * Retourne la liste de toutes les tache affect�es � une personne (id)
	 * @param id
	 * @return
	 */
	public List<Tache> getListTacheAffecteById(int id){
		List<Tache> listTache = new ArrayList<>();
		List<Tache> list = getListTache();
		
		for(Tache task : list){
			if(task.getAffecte().getIdPersonne() == id)
				listTache.add(task);
		}
		
		return listTache;
	}
	
	/**
	 * Retourne la liste de toutes les tache affect�es � une personne (nom)
	 * @param name
	 * @return
	 */
	public List<Tache> getListTacheAffecteByName(String name){
		List<Tache> listTache = new ArrayList<>();
		List<Tache> list = getListTache();
		
		for(Tache task : list){
			if(task.getAffecte().getNomPersonne().equals(name))
				listTache.add(task);
		}
		
		return listTache;
	}
	
	/**
	 * Gestion de la balise start
	 * @param uri
	 * @param localName
	 * @param qName
	 * @param attributes
	 * @throws SAXException
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (localName.equals("Tache"))
			task = new Tache();

		balise = localName;
	}

	/**
	 * Gestion de la balise end
	 * @param uri
	 * @param localName
	 * @param qName
	 * @throws SAXException
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (balise.equals("Tache"))
			listTache.add(task);
	}

	/**
	 * Gestion des donn�es pr�sentes dans la balise
	 * @param ch
	 * @param start
	 * @param length
	 * @throws SAXException
	 */
	public void characters(char[] ch, int start, int length) throws SAXException {

		String s = new String(ch, start, length).trim();
		if (s.trim().isEmpty())
			return;

		// R�cup�ration de l'id de la tache
		if (balise.equals("idTache"))
			task.setIdTache(Integer.parseInt(s));

		// R�cup�ration du nom de la tache
		if (balise.equals("nomTache"))
			task.setNomTache(s);

		// R�cup�ration de l'id de la personne qui a cr�� la tache
		if (balise.equals("createur"))
			task.setCreateur(parser.getPersonneById(Integer.parseInt(s)));

		// R�cup�ration de l'id de le personne a qui est assign� la tache
		if (balise.equals("affecte"))
			task.setAffecte(parser.getPersonneById(Integer.parseInt(s)));

		// R�cup�ration de la priorit� de la tache
		if (balise.equals("priorite"))
			task.setPriorite(s);

		// R�cup�ration de l'�tat de la tache
		if (balise.equals("etat"))
			task.setEtat(s);

		// R�cup�ration du descriptif de la tache
		if (balise.equals("descriptif"))
			task.setDescriptif(s);
	}
}
