/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.utils;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import uit.tkorg.and.models.Publication;

/**
 *
 * @author tiendv
 */
public class WriteXMLFile {

    /**
     * DTD
     * <pair>
     *     <Paper id=1>
     *          <title> </title>
     *          <mainauthor>
     *              <name></name>
     *              <email></email>
     *              <interest></interest>
     *              <affiliation></affiliaciton>
     *              <result></result>
     *          </mainauthor>
     *          <coauthorn> </coauthor>
     *          <keywords> </keywords>
     *     </paper>
     * 
     *      <Paper id=2>
     *          <title> </title>
     *          <mainauthor>
     *              <name></name>
     *              <email></email>
     *              <interest></interest>
     *              <affiliation></affiliaciton>
     *              <result></result>
     *          </mainauthor>
     *          <coauthorn> </coauthor>
     *          <keywords> </keywords>
     *     </paper>
     * 
     *     <tag></tag>
     * <pair> 
     * 
     */
    /**
     * 
     * @param publicaitonA
     * @param publicaitonB
     * @param name : File name
     * @param path : path to save file
     */
    public void makeXMLFile(Publication publicaitonA, Publication publicaitonB, String name, String path) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("pair");
        doc.appendChild(rootElement);

        //Element paper A
        Element paperA = makePaperElement(doc, publicaitonA, 1);
        rootElement.appendChild(paperA);

        //Element paper B
        Element paperB = makePaperElement(doc, publicaitonB, 2);
        rootElement.appendChild(paperB);

        String compare = "0";
        Element tag = doc.createElement("tag");
        int sum = publicaitonA.getMainAuthor().getAuthorResult() + publicaitonB.getMainAuthor().getAuthorResult();
        if (sum == 2) {
            compare = "1";
        } else {
            compare = "0";
        }
        tag.appendChild(doc.createTextNode(compare));
        rootElement.appendChild(tag);

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        String file = name + ".xml";
        boolean forder = new File("c:\\Export\\" + path).mkdir();
        StreamResult result = new StreamResult(new File("c:\\Export\\" + path, file));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
    }

    /** 
     * @param paper
     * @return Paper element.
     */
    public static Element makePaperElement(Document doc, Publication paper, int id) {

        Element rootElement = doc.createElement("paper");
        Attr attr = doc.createAttribute("id");
        attr.setValue(Integer.toString(id));
        rootElement.setAttributeNode(attr);

        // title elements
        Element title = doc.createElement("title");
        title.appendChild(doc.createTextNode(paper.getPubTitle()));
        rootElement.appendChild(title);

        // author elements
        Element mainAuthor = doc.createElement("mainauthor");
        rootElement.appendChild(mainAuthor);

        // author name elements
        Element authorName = doc.createElement("name");
        authorName.appendChild(doc.createTextNode(paper.getMainAuthor().getAuthorName()));
        mainAuthor.appendChild(authorName);

        // email elements

        Element email = doc.createElement("email");
        email.appendChild(doc.createTextNode(paper.getMainAuthor().getAuthorEmail()));
        mainAuthor.appendChild(email);


        // interest elements
        Element interest = doc.createElement("interest");
        interest.appendChild(doc.createTextNode(paper.getMainAuthor().getAuthorInterests()));
        mainAuthor.appendChild(interest);

        // affiliation elements
        Element affiliation = doc.createElement("affiliation");
        affiliation.appendChild(doc.createTextNode(paper.getMainAuthor().getAuthorAfflication()));
        mainAuthor.appendChild(affiliation);

        // affiliation elements
        Element tagresult = doc.createElement("result");
        tagresult.appendChild(doc.createTextNode(Integer.toString(paper.getMainAuthor().getAuthorResult())));
        mainAuthor.appendChild(tagresult);

        // Coauthors name elements
        Element coAuthor = doc.createElement("coauthor");
        String nameCoauthor = "";
        for (int i = 0; i < paper.getCoAuthor().size(); i++) {
            Element author = doc.createElement("author");
            
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(paper.getCoAuthor().get(i).getAuthorName()));
            author.appendChild(name);

            Element affilation = doc.createElement("aff");
            affilation.appendChild(doc.createTextNode(paper.getCoAuthor().get(i).getAuthorAfflication()));
            author.appendChild(affilation);
            
            coAuthor.appendChild(author);
        }

        coAuthor.appendChild(doc.createTextNode(nameCoauthor));
        rootElement.appendChild(coAuthor);

        // Keyword elements
        Element keywords = doc.createElement("keywords");
        keywords.appendChild(doc.createTextNode(paper.getPubKeywords()));
        rootElement.appendChild(keywords);

        return rootElement;
    }
}
