/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.application;

import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import uit.tkorg.and.models.Publication;
import uit.tkorg.and.utils.ReadXML;
import uit.tkorg.and.utils.WriteXMLFile;

/**
 *
 * @author DucHuynh
 */
public class GeneratePairPublicationFile {
    public static void main(String[] args) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        String pathFile = "c:\\DataLoad\\";
        GeneratePairPublicationFile generatePairPublicationFile = new GeneratePairPublicationFile();
        generatePairPublicationFile.generatePairPubFile(pathFile);
    }
    
    public void generatePairPubFile(String path) throws ParserConfigurationException, TransformerConfigurationException, TransformerException
    {
        final ReadXML reader = new ReadXML();        
        File root = new File(path);
        File[] listChild = root.listFiles();
        for (int i = 0; i < listChild.length; i++) {
            File child = listChild[i];
            System.out.println(child.getAbsolutePath());
            if(child.isDirectory())
            { 
                File[] listFiles = child.listFiles();
                int lengthListFile = 0;
                Publication[] data = new Publication[listFiles.length];
                while(lengthListFile < listFiles.length)
                {
                    File file = listFiles[lengthListFile];
                    System.out.println(file.getAbsolutePath());
                    if(file.isFile())
                    {                            
                        Publication publication = reader.importPubFromXML(file.getAbsolutePath());
                        data[lengthListFile] = publication;                            
                    }                    
                    lengthListFile++;                    
                } 
                int count = 0;
                int countSame = 0;
                int countDiff = 0;
                for (int index = 0; index < listFiles.length - 1; index++) {
                    for (int index2 = index + 1; index2 < listFiles.length; index2++) {
                         count++;
                         int sum = data[index].getMainAuthor().getAuthorResult() + data[index2].getMainAuthor().getAuthorResult();        
                        if(sum == 2) 
                            countSame++;
                        else 
                            countDiff++;
                        
                         WriteXMLFile writeFile = new WriteXMLFile();                         
                         writeFile.makeXMLFile(data[index], data[index2], Integer.toString(count), child.getName());
                    }
                }
            }
        }
    }
}
