package application;

import java.io.File;
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
	public void writeUser(Tache t) {
		try {
			System.out.println("XML WRITER");

			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setIgnoringComments(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(new File("src/user.xml"));

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

			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer;

			transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("src/user.xml"));
			transformer.transform(source, result);

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
