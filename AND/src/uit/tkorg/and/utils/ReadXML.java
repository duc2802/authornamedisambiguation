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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import uit.tkorg.and.gui.Main;
import uit.tkorg.and.models.Author;
import uit.tkorg.and.models.PairPublication;
import uit.tkorg.and.models.Publication;

/**
 *
 * @author tiendv
 */
public class ReadXML {
    String pathSourceFile;
    /**
     * @param fileName
     * @return publicaiton from xml file
     */
        
    public static void main(String[] args) {
        String path = "c:\\a.xml";
        ReadXML reader = new ReadXML();
        PairPublication pair = reader.readPairPublicationFromXML(path);        
    }
    
    public PairPublication readPairPublicationFromXML(String path)
    {
        PairPublication pair = new PairPublication();        
        try{
            File fXmlFile = new File(path);          
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            
            NodeList paperList = doc.getElementsByTagName("paper");
            System.out.println("Count number node paper: " + paperList.getLength());
           
            Node paper = paperList.item(0);
            Publication publication = parseNodeToPublication(paper);
            pair.setPublicationA(publication);
            
            paper = paperList.item(1);
            publication = parseNodeToPublication(paper);
            pair.setPublicationB(publication);
            
            NodeList tagNode = doc.getElementsByTagName("tag");
            pair.setClassificationLabel(Integer.parseInt(tagNode.item(0).getTextContent()));   
            
            System.out.println("Tag value: " + Integer.parseInt(tagNode.item(0).getTextContent()));  
            
        } catch (ParserConfigurationException e) {          
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
           System.out.println(e.getMessage());
        }
        return pair;
    }
    
    public Publication parseNodeToPublication(Node paper)
    {
        Publication publication = new Publication();
        NodeList listChildNodeInPaper = paper.getChildNodes();
        for (int i = 0; i < listChildNodeInPaper.getLength(); i++) {
            Node node = listChildNodeInPaper.item(i);
            String nodeName = node.getNodeName();           
            if(nodeName.equals("title"))
            {
                publication.setPubTitle(node.getTextContent());
                System.out.println("Title: " + node.getTextContent());
            }
            else if(nodeName.equals("mainauthor"))
            {
                Author mainAuthor = this.parseNodeToAuthor(node);
                publication.setMainAuthor(mainAuthor);                
            }
            else if(nodeName.equals("coauthor"))
            {
                ArrayList<Author> listCoAuthorAuthor = new ArrayList<Author>();
                NodeList listCoAthor = node.getChildNodes();
                for (int j = 0; j < listCoAthor.getLength(); j++) {
                    Node nodeInCoAuthor = listCoAthor.item(j);                    
                    Author coAuthor = new Author();
                    if(nodeInCoAuthor.getChildNodes().item(0).getTextContent() != null){
                        coAuthor.setAuthorName(nodeInCoAuthor.getChildNodes().item(0).getTextContent());
                        System.out.println("Name co-author : " + nodeInCoAuthor.getChildNodes().item(0).getTextContent());
                    }else {
                        coAuthor.setAuthorName("");
                    }
                    if(nodeInCoAuthor.getChildNodes().item(1).getTextContent() != null){
                        coAuthor.setAuthorAffliation(nodeInCoAuthor.getChildNodes().item(1).getTextContent());
                        System.out.println("Aff co-author : " + nodeInCoAuthor.getChildNodes().item(1).getTextContent());
                    }else {
                        coAuthor.setAuthorAffliation("");
                    }
                    listCoAuthorAuthor.add(coAuthor);
                }
                publication.setCoAuthor(listCoAuthorAuthor);
            }
            else if(nodeName.equals("keywords"))
            {
                if(node.getTextContent() != null){
                    publication.setPubKeywords(node.getTextContent());
                    System.out.println("Keywords : " + node.getTextContent());
                }else {
                    publication.setPubKeywords("");
                }                
            }
        }
        return publication;
    }
    
    private Author parseNodeToAuthor(Node author)
    {
        NodeList listChildMainAuthor = author.getChildNodes();
        Author mainAuthor = new Author();
        for (int j = 0; j < listChildMainAuthor.getLength(); j++) {            
            Node nodeInMainAuthor = listChildMainAuthor.item(j);
            String nodeNameInMainAuthor = nodeInMainAuthor.getNodeName();
            if(nodeNameInMainAuthor.equals("name")){
                if(nodeInMainAuthor.getTextContent() != null){
                    mainAuthor.setAuthorName(nodeInMainAuthor.getTextContent());
                    System.out.println("Name main author : " + nodeInMainAuthor.getTextContent());
                }else {
                    mainAuthor.setAuthorName("");
                }                
            }else if(nodeNameInMainAuthor.equals("affiliation")){   
                if(nodeInMainAuthor.getTextContent() != null){
                    mainAuthor.setAuthorAffliation(nodeInMainAuthor.getTextContent());
                    System.out.println("Affiliation main author: " + nodeInMainAuthor.getTextContent());
                }else {
                    mainAuthor.setAuthorAffliation("");
                }                
            }else if(nodeNameInMainAuthor.equals("interest")){
                if(nodeInMainAuthor.getTextContent() != null){
                    mainAuthor.setAuthorInterests(nodeInMainAuthor.getTextContent());
                    System.out.println("Interest main author: " + nodeInMainAuthor.getTextContent());
                }else {
                    mainAuthor.setAuthorInterests("");
                }
            }
        }
        return mainAuthor;
    }
    
    public ReadXML(String path)
    {
        this.pathSourceFile = path;
    }
    
    public ReadXML()
    {
       
    }
    
    public Publication importPubFromXML(String fileName) {
        Publication result = null;
        List<Author> coauthor = null;
        Author mainAuthor = null;

        try {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            result = new Publication();
            //Read Paper info
            Element rootElement = doc.getDocumentElement();
            result.setDigitalName(getTagValue("digital", rootElement));
            result.setNameSeach(getTagValue("nameseach", rootElement));
            result.setJournal(getTagValue("journal", rootElement));
            result.setPubAbstract(getTagValue("abstract", rootElement));
            result.setPubKeywords(getTagValue("keywords", rootElement));
            result.setPubTitle(getTagValue("title", rootElement));
            result.setPubYearPublish(getTagValue("year", rootElement));
            result.setPublisher(getTagValue("publisher", rootElement));
            result.setConference(getTagValue("conference", rootElement));
            int test = 0;
            if(!"".equals(getTagValue("result", rootElement)))
            {
               test = Integer.parseInt(getTagValue("result", rootElement));
            }            
            // Read author info

            coauthor = new ArrayList<Author>();
            NodeList authorList = doc.getElementsByTagName("author");
            for (int temp = 0; temp < authorList.getLength(); temp++) {
                Node nNode = authorList.item(temp);
                Element eElement = (Element) nNode;
                if (!getTagValue("name", eElement).equals("")) {
                    mainAuthor = new Author();
                    mainAuthor.setAuthorName(getTagValue("name", eElement));
                    mainAuthor.setAuthorAffliation(getTagValue("afflication", eElement)); 
                                  
                    //if("main".equals(eElement.getParentNode().getNodeName())){
                        //System.out.print("");
                   //      mainAuthor.setAuthorResult(Integer.parseInt(getTagValue("result", eElement)));
                    //}
                    if (eElement.hasAttribute("email")) {
                        mainAuthor.setAuthorEmail(getTagValue("email", eElement));
                    }
                    if (eElement.hasAttribute("order") && getTagValue("order", eElement) != null) {
                        mainAuthor.setAuthorOrder(Integer.parseInt(getTagValue("order", eElement)));
                    }
                    if (eElement.hasAttribute("result") && getTagValue("result", eElement) != null) {
                        mainAuthor.setAuthorResult(Integer.parseInt(getTagValue("result", eElement)));
                    }

                    mainAuthor.setInterest(getTagValue("interests", eElement));
                    if (nNode.getParentNode().getNodeName().equals("main")) {
                        mainAuthor.setAuthorResult(test);
                        result.setMainAuthor(mainAuthor);
                    } else {
                        coauthor.add(mainAuthor);
                    }
                }
            }
            result.setCoAuthor(coauthor);

        } catch (Exception e) {
            Main.taLog.append(e.getMessage());
        }
        return result;
    }

    private String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue != null) {
            return nValue.getNodeValue().trim();
        } else {
            return "";
        }
    }
    
    

}
