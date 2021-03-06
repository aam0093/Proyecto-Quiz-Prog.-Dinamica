package exportar;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pregunta.Pregunta;


public class ExportarXml implements Exportar {
	Document doc;
	String path = "";
	Element quiz;


	@Override
	public void cerrarFichero() {
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			doc.appendChild(quiz);
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void abrirFichero() {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		doc = dBuilder.newDocument();
		quiz = (Element) doc.createElement("quiz");
	}

	@Override
	public void exportar(Pregunta pregunta, String path) {
		try {
		
			this.path = path;
			Element question = (Element) doc.createElement("question");

			// setting attribute to element
			Attr attr = doc.createAttribute("type");
			attr.setValue("cloze");
			question.setAttributeNode(attr);

			// name element
			Element name = doc.createElement("name");
			Element textName = doc.createElement("text");
			textName.setTextContent(pregunta.getTitulo() );
			name.appendChild(textName);
			question.appendChild(name);

			
			// questiontext element
			Element questionText = doc.createElement("questiontext");
			Attr attr2 = doc.createAttribute("format");
			attr2.setValue("HTML");
			questionText.setAttributeNode(attr2);
			
			Element textQuestionText = doc.createElement("text");
			textQuestionText.setTextContent(pregunta.getEnunciado() + "<br>" + pregunta.getContenido());
			/*
			 * String contenido = new String(pregunta.getContenido());
			 * ByteBuffer aux = Charset.forName("UTF-8").encode(contenido);
			 */
			questionText.appendChild(textQuestionText);
			question.appendChild(questionText);

			// feedback element
			Element generalfeedback = doc.createElement("generalfeedback");
			generalfeedback.setTextContent("feedback");
			Attr attr3 = doc.createAttribute("format");
			attr2.setValue("HTML");
			generalfeedback.setAttributeNode(attr3);
			Element textFeedback = doc.createElement("text");
			textFeedback.setTextContent(pregunta.getFeedback());
			generalfeedback.appendChild(textFeedback);
			question.appendChild(generalfeedback);
			
			Element penalty = doc.createElement("penalty");
			penalty.setNodeValue("0.333");
			question.appendChild(penalty);
			
			Element hidden = doc.createElement("hidden");
			hidden.setNodeValue("0");
			question.appendChild(hidden);

			quiz.appendChild(question);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
