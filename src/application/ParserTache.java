package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserTache extends DefaultHandler {
	private ArrayList<Tache> listTache = new ArrayList<Tache>();
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
	public ArrayList<Tache> getListTache() {
		return listTache;
	}
	
	/**
	 * Retourne la liste de toutes les tache cr��e par une personne (id)
	 * @param id
	 * @return
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public ArrayList<Tache> getListTacheCreateurById(int id) throws SAXException, IOException, ParserConfigurationException{
		/*SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		SAXParser sax = factory.newSAXParser();

		ParserUser handlerSAX = new ParserUser();
		sax.parse("src/user.xml", handlerSAX);
		
		SAXParserFactory factoryTask = SAXParserFactory.newInstance();
		factoryTask.setNamespaceAware(true);
		SAXParser saxTask = factoryTask.newSAXParser();
		
		ParserTache p = new ParserTache(handlerSAX);
		saxTask.parse("src/tache.xml", p);*/
		
		
		
		ArrayList<Tache> listT =  new ArrayList<>();
		ArrayList<Tache> list = (ArrayList<Tache>) getListTache().clone();
		
		System.out.println("list In Parser : " + list);
		System.out.println("listTache In Parser : " + listT);
		
		for(Tache task : list){
			if(task.getCreateur().getIdPersonne() == id)
				listT.add(task);
		}
		

		System.out.println("listTache In Parser 2 : " + listT);
		
		return listT;
	}
	
	/**
	 * Retourne la liste de toutes les tache cr��e par une personne (nom)
	 * @param name
	 * @return
	 */
	public List<Tache> getListTacheCreateurByName(String name){
		ArrayList<Tache> listT = new ArrayList<>();
		ArrayList<Tache> list = (ArrayList<Tache>) getListTache().clone();
		
		for(Tache task : list){
			if(task.getCreateur().getNomPersonne().equals(name))
				listT.add(task);
		}
		
		return listT;
	}
	
	/**
	 * Retourne la liste de toutes les tache affect�es � une personne (id)
	 * @param id
	 * @return
	 */
	public ArrayList<Tache> getListTacheAffecteById(int id){
		ArrayList<Tache> listT = new ArrayList<>();
		ArrayList<Tache> list = (ArrayList<Tache>) getListTache().clone();
		
		for(Tache task : list){
			if(task.getAffecte().getIdPersonne() == id)
				listT.add(task);
		}
		
		return listT;
	}
	
	/**
	 * Retourne la liste de toutes les tache affect�es � une personne (nom)
	 * @param name
	 * @return
	 */
	public ArrayList<Tache> getListTacheAffecteByName(String name){
		ArrayList<Tache> listT = new ArrayList<>();
		ArrayList<Tache> list = (ArrayList<Tache>) getListTache().clone();
		
		for(Tache task : list){
			if(task.getAffecte().getNomPersonne().equals(name))
				listT.add(task);
		}
		
		return listT;
	}
	
	/**
	 * Gestion de la balise start
	 * @param uri
	 * @param localName
	 * @param qName
	 * @param attributes
	 * @throws SAXException
	 */
	@Override
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
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (balise.equals("Tache")){
			
		}
			
	}

	/**
	 * Gestion des donn�es pr�sentes dans la balise
	 * @param ch
	 * @param start
	 * @param length
	 * @throws SAXException
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		String s = new String(ch, start, length).trim();
		if (s.trim().isEmpty())
			return;
		
		System.out.println("balise : " + balise +" et str : " + s);

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
		if (balise.equals("descriptif")){
			task.setDescriptif(s);
			listTache.add(task);
			System.out.println("tache : " + task);
		}
	}
}
