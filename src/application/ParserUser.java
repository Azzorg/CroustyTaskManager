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

	public List<Personne> getListUser() {
		return listUser;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (localName.equals("user"))
			user = new Personne();

		if (localName.equals("idUser"))
			isIdUser = true;

		if (localName.equals("nomUser"))
			isNomUser = true;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equals("user")) 
			listUser.add(user);
		
		if (localName.equals("idUser"))
			isIdUser = false;

		if (localName.equals("nomUser"))
			isNomUser = false;
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
	}
}
