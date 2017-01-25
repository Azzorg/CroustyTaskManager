package application;

import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ParserUser extends DefaultHandler {

	private List<Personne> listUser = new ArrayList<Personne>();

	private Personne user;
	private boolean isIdUser = false;
	private boolean isNomUser = false;
	private boolean isPassword = false;

	public List<Personne> getListUser() {
		return listUser;
	}
	
	public boolean nameExist(String nom){
		List<Personne> list = getListUser();
		for(Personne p : list){
			if(p.getNomPersonne().equals(nom))
				return true;
		}
		return false;
	}
	
	public boolean correspondanceNamePassword(String name, String pass){
		
		List<Personne> list = getListUser();
		for(Personne p : list){
			if(p.getNomPersonne().equals(name) && p.getPassWord().equals(pass))
				return true;
		}
		return false;
	}
	
	public Personne getPersonneById(int id){
		List<Personne> list = getListUser();
		for(Personne p : list){
			if(p.getIdPersonne() == id)
				return p;
		}
		return null;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (localName.equals("user"))
			user = new Personne();

		if (localName.equals("idUser"))
			isIdUser = true;

		if (localName.equals("nomUser"))
			isNomUser = true;
		
		if (localName.equals("passWord"))
			isPassword = true;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equals("user")) 
			listUser.add(user);
		
		if (localName.equals("idUser"))
			isIdUser = false;

		if (localName.equals("nomUser"))
			isNomUser = false;
		
		if (localName.equals("passWord"))
			isPassword = false;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		String s = new String(ch, start, length).trim();
		if (s.trim().isEmpty()) 
			return;

		if (isIdUser) 
			user.setIdPersonne(Integer.parseInt(s));
		
		if(isNomUser)
			user.setNomPersonne(s);
		
		if(isPassword)
		user.setPassWord(s);
	}
}
