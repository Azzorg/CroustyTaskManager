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

public class WriterXMLUserDOM {
	public void writeUser(Personne p) {
		try {
			System.out.println("XML WRITER");

			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setIgnoringComments(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(new File("user.xml"));

			NodeList nodes = doc.getElementsByTagName("user");

			Element idElt = doc.createElement("idUser");
			Element nomElt = doc.createElement("nomUser");
			Element passElt = doc.createElement("passWord");

			Text idValue = doc.createTextNode(Integer.toString(p.getIdPersonne()));
			Text nomValue = doc.createTextNode(p.getNomPersonne());
			Text passValue = doc.createTextNode(p.getPassWord());

			Element userElt = doc.createElement("user");

			idElt.appendChild(idValue);
			nomElt.appendChild(nomValue);
			passElt.appendChild(passValue);

			userElt.appendChild(idElt);
			userElt.appendChild(nomElt);
			userElt.appendChild(passElt);

			nodes.item(0).appendChild(userElt);

			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer;

			transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("user.xml"));
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
