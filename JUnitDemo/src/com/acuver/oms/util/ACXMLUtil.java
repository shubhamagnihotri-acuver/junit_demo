package com.acuver.oms.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xpath.CachedXPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;

public class ACXMLUtil {
	private static YFCLogCategory logger = YFCLogCategory.instance(ACXMLUtil.class);
	private static XPathFactory factory = XPathFactory.newInstance();
	private static XPath xpath = factory.newXPath();

	/**
	 * Construct a document object.
	 * 
	 * @return empty document object
	 * @throws ParserConfigurationException for invalid format
	 */
	public static Document getDocument() throws ParserConfigurationException {
		// Create new document builder
		DocumentBuilder documentBuilder = getDocumentBuilder(false);
		return documentBuilder.newDocument();
	}

	/**
	 * Construct document builder object
	 * 
	 * @param requiredValidation indicating the parser needs to validate the
	 *                           document against a DTD or not.
	 * @return document builder object which can create new document
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created
	 *                                      which satisfies the configuration
	 *                                      requested.
	 */
	private static DocumentBuilder getDocumentBuilder(boolean requiredValidation) throws ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		if (requiredValidation) {
			documentBuilderFactory.setValidating(true);
		}
		return documentBuilderFactory.newDocumentBuilder();

	}

	/**
	 * Create a Document object with provided name as the root element tag.
	 * 
	 * @param docElementTag the document element name.
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created
	 *                                      which satisfies the configuration
	 *                                      requested.
	 * @return newly created document
	 */
	public static Document createDocument(String docElementTag) throws ParserConfigurationException {
		Document document = getDocument();
		Element element = document.createElement(docElementTag);
		document.appendChild(element);
		return document;
	}

	/**
	 * Get the specified attribute from the document.
	 * 
	 * @param Document .
	 * @param xpath,   Xpath from the document
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created
	 *                                      which satisfies the configuration
	 *                                      requested.
	 * @return string attribute value
	 */

	public static String extractAttribute(Document document, String xPath) {
		String attributeValue = null;
		try {
			if (document != null && xpath != null) {
				attributeValue = xpath.evaluate(xPath, document);
			}
		} catch (XPathExpressionException e) {
			logger.warn("extractAttribute - invalid xpath or xml: " + xPath + " :  document: " + document);
		}
		return attributeValue;
	}

	
	/**
	 * Return the element based on the XPath specified from the input XML. e.g. XPATH format :
	 * "ItemList/Item[ItemAliasList/ItemAlias/@AliasValue='ItemAlias1']"
	 * 
	 * @param inXML the document object for processing
	 * @param xPath the path which is identified in the input XML
	 * @return the element which has been identified in from the XPath
	 * @throws TransformerException if any transformation error occur
	 */
	public static YFCElement getElementByXPath(final YFCElement element, final String xPath) throws TransformerException {
		CachedXPathAPI oCachedXPathAPI = new CachedXPathAPI();
		return (YFCElement)YFCDocument.getNodeFor(oCachedXPathAPI.selectSingleNode(element.getDOMNode(), xPath));
	}

}
