package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class WriterXMLTaskDOM {

	DocumentBuilderFactory domFactory;
	DocumentBuilder builder;
	Document doc;

	public WriterXMLTaskDOM() {
		try {
			domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setIgnoringComments(true);
			builder = domFactory.newDocumentBuilder();
			doc = builder.parse(new File("src/tache.xml"));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void writeTask(Tache t) {

		try {
			System.out.println("XML WRITER");
			
			NodeList nodes = doc.getElementsByTagName("root");

			Element idElt = doc.createElement("idTache");
			Element nomElt = doc.createElement("nomTache");
			Element creatElt = doc.createElement("createur");
			Element affElt = doc.createElement("affecte");
			Element prioElt = doc.createElement("priorite");
			Element etatElt = doc.createElement("etat");
			Element descElt = doc.createElement("descriptif");

			Text idValue = doc.createTextNode(Integer.toString(t.getIdTache()));
			Text nomValue = doc.createTextNode(t.getNomTache());
			Text creatValue = doc.createTextNode(Integer.toString(t.getCreateur().getIdPersonne()));
			Text affValue = doc.createTextNode(Integer.toString(t.getAffecte().getIdPersonne()));
			Text prioValue = doc.createTextNode(t.getPriorite());
			Text etatValue = doc.createTextNode(t.getEtat());
			Text descValue = doc.createTextNode(t.getDescriptif());

			Element userElt = doc.createElement("Tache");

			idElt.appendChild(idValue);
			nomElt.appendChild(nomValue);
			creatElt.appendChild(creatValue);
			affElt.appendChild(affValue);
			prioElt.appendChild(prioValue);
			etatElt.appendChild(etatValue);
			descElt.appendChild(descValue);

			userElt.appendChild(idElt);
			userElt.appendChild(nomElt);
			userElt.appendChild(creatElt);
			userElt.appendChild(affElt);
			userElt.appendChild(prioElt);
			userElt.appendChild(etatElt);
			userElt.appendChild(descElt);

			nodes.item(0).appendChild(userElt);

			System.out.println("Création transformer");
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer;

			System.out.println("Ecriture doc");
			transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("src/tache.xml"));
			transformer.transform(source, result);
			System.out.println("Fin ecriture doc");

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void removeTask(String idTask) {
		NodeList nodes = doc.getElementsByTagName("Tache");
		
		for(int i = 0; i<nodes.getLength(); i++){
			Element tache = (Element) nodes.item(i);
			Element id = (Element) tache.getElementsByTagName("idTache").item(0);
			String idTache = id.getTextContent();
			if(idTache.equals(idTask))
				tache.getParentNode().removeChild(tache);
		}
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("src/tache.xml"));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
