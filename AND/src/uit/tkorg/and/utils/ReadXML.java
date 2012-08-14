/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.AbstractList;
import java.util.List;
import uit.tkorg.and.models.Author;
import uit.tkorg.and.models.Publication;

/**
 *
 * @author tiendv
 */
public class ReadXML {
    
    public Publication importPubFromXML (String fileName)
    {
        Publication result = null;
        Author mainAuthor = null;
       
         try {
 
		File fXmlFile = new File("c:\\"+fileName+".xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
                
                NodeList authorList = doc.getElementsByTagName("author");
               for (int temp = 0; temp < authorList.getLength(); temp++) {
                   
                   Node nNode = authorList.item(temp);
   
		   if (nNode.getParentNode().getNodeName().equals("main")){
		      Element eElement = (Element) nNode;
		      mainAuthor.setAuthorName(getTagValue("name", eElement));
		      mainAuthor.setAuthorAfflication(getTagValue("afflication", eElement));
                      mainAuthor.setAuthorEmail(getTagValue("email", eElement));
		      mainAuthor.setAuthorOrder(Integer.parseInt(getTagValue("order", eElement)));
                      mainAuthor.setAuthorResult(Integer.parseInt(getTagValue("result", eElement)));
                      mainAuthor.setInterest(getTagValue("interests", eElement));
		   }
                   else
               }
              
		  
	  } catch (Exception e) {
		e.printStackTrace();
	  }
        return result;
    }
    
      private static String getTagValue(String sTag, Element eElement) {
	NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
 
        Node nValue = (Node) nlList.item(0);
 
	return nValue.getNodeValue();
  }
  
    
}
