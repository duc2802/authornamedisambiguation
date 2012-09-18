/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.models;

import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import uit.tkorg.and.core.features.AffiliationSimilarity;
import uit.tkorg.and.core.features.AuthorSimilarity;
import uit.tkorg.and.core.features.CoAuthorSimilarity;
import uit.tkorg.and.core.features.InterestKeywordSimilarity;
import uit.tkorg.and.core.features.KeywordSimilarity;
import uit.tkorg.and.gui.Main;
import uit.tkorg.and.utils.ReadXML;
import uit.tkorg.and.utils.WriteXMLFile;
import weka.core.AbstractInstance;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.filters.Filter;

/**
 *
 * @author DucHuynh
 */
public class Vector {
    
    /**
     * 
     * tiendv
     * @param dimension : 
     * @param numberVector
     * @param selectFeature : Cac feature duoc chon de chay
     * @return 
     */
    
    public static Instances buildVectorWithFeatures (int numberVector, Feature selectFeature)
    {
        Instances instances;
        // Value feature 
        FastVector vectorFeature = new FastVector(selectFeature.getNumberSelectFeature());
        FastVector vector = new FastVector(selectFeature.getNumberSelectFeature()+1);
        
        // JaccardAuthorName
        if(selectFeature.getJcAuthorName()==true)
        {
            Attribute attAuthorSimilarity = new Attribute(Feature.JC_AUTHOR_NAME);
            vector.addElement(attAuthorSimilarity);
        }
        // JaccardAffiliation
        if(selectFeature.getJcAffiliation()==true)
        {
           Attribute attAffiliationSimilarity = new Attribute(Feature.JC_AFFILIATION);
           vector.addElement(attAffiliationSimilarity);    
        }
        // Jaccard Coauthor name
        if(selectFeature.getJcCoAuthor()==true)
        {
           Attribute attCoauthorSimilarity = new Attribute(Feature.JC_CO_AUTHOR);
           vector.addElement(attCoauthorSimilarity);    
        }
        // Jaccard KeywordS 
        if(selectFeature.getJcKeyword()==true)
        {
           Attribute attKeywordSimilarity = new Attribute(Feature.JC_KEYWORD);
           vector.addElement(attKeywordSimilarity);    
        }
        // Jaccard KeywordS 
        if(selectFeature.getJcInterestingKeyword()==true)
        {
           Attribute attInterestSimilarity = new Attribute(Feature.JC_INTERESTING_KEYWORD);
           vector.addElement(attInterestSimilarity);    
        }
        
        // Levenshtein Author Name 
        if(selectFeature.getLevenshteinAuthorname()==true)
        {
           Attribute attLevenshteinAuthorname = new Attribute(Feature.LEVENSHTEIN_AUTHOR_NAME);
           vector.addElement(attLevenshteinAuthorname);    
        }
        // Levenshtein Affiliation
        if(selectFeature.getLevenshteinAffiliaiton()==true)
        {
           Attribute attLevenshteinAffiliaiton = new Attribute(Feature.LEVENSHTEIN_AFFILIATION);
           vector.addElement(attLevenshteinAffiliaiton);    
        }
        // Jaro Author Name
        if(selectFeature.getJaroAuthorName()==true)
        {
           Attribute attJaroAuthorName = new Attribute(Feature.JARO_AUTHOR_NAME);
           vector.addElement(attJaroAuthorName);    
        }
        // Jaro Affiliation
        if(selectFeature.getJaroAffiliation()==true)
        {
           Attribute attJaroAffiliation = new Attribute(Feature.JARO_AFFILIATION);
           vector.addElement(attJaroAffiliation);    
        }
         // Jarowinkler Author Name
        if(selectFeature.getJarowinklerAuthorName()==true)
        {
           Attribute attJaroWinklerAuthorName = new Attribute(Feature.JAROWINKLER_AUTHOR_NAME);
           vector.addElement(attJaroWinklerAuthorName);    
        }
        // Jarowinkler Author Affiliation
        if(selectFeature.getJarowinklerAffiliaiton()==true)
        {
           Attribute attJaroWinklerAffiliation = new Attribute(Feature.JAROWIKLER_AFFILIATION);
           vector.addElement(attJaroWinklerAffiliation);    
        }
        // smithWaterman Author AuthorName
        if(selectFeature.getSmithWatermanAuthorName()==true)
        {
           Attribute attSmithWatermanAuthorName = new Attribute(Feature.SMITHWATERMAN_AUTHOR_NAME);
           vector.addElement(attSmithWatermanAuthorName);    
        }
        // smithWaterman Author Affiliation
        if(selectFeature.getSmithWatermanAffiliation()==true)
        {
           Attribute attSmithWatermanAffiliaiton= new Attribute(Feature.SMITHWATERMAN_AFFILIATION);
           vector.addElement(attSmithWatermanAffiliaiton);    
        }
         // Monge-Elkan Author Name
        if(selectFeature.getMongeElkanAuthorName()==true)
        {
           Attribute attSmithMongeElkanAuthorName= new Attribute(Feature.MONGEELKAN_AUTHOR_NAME);
           vector.addElement(attSmithMongeElkanAuthorName);    
        }
          // Monge-Elkan Author Affiliation
        if(selectFeature.getMongeElkanAuthorName()==true)
        {
           Attribute attSmithMongeElkanAuthorAffiliation= new Attribute(Feature.MONGEELKAN_AFFILIATION);
           vector.addElement(attSmithMongeElkanAuthorAffiliation);    
        }
        
        // add more feature here
        
        // Declare the class attribute along with its values
        FastVector classValue = new FastVector(2);
        classValue.addElement("same");
        classValue.addElement("diff");
        Attribute classAttribute = new Attribute("Class", classValue);
        vector.addElement(classAttribute);
        
        instances = new Instances("AuthorName", vector, numberVector);
        instances.setClassIndex(selectFeature.getNumberSelectFeature()); // cai nay xem co dung khong
        return instances;
    }
    /**
     * tiendv
     * @param instancesData
     * @param pubA
     * @param pubB
     * @param Feature : select features
     * @param label
     * @return 
     */
    public static Instance calculateVectorWithSelectFeatures(Instances instancesData, Publication pubA, Publication pubB, Feature selectFeature, String label)
    {                
        AuthorSimilarity authorSimilarity = new AuthorSimilarity();
        AffiliationSimilarity affiliationSimilarity = new AffiliationSimilarity();
        CoAuthorSimilarity coAuthorSimilarity = new CoAuthorSimilarity();
        KeywordSimilarity keywordSimilarity = new KeywordSimilarity();
        InterestKeywordSimilarity interestKeywordSimilarity = new InterestKeywordSimilarity();
        int dimension = selectFeature.getNumberSelectFeature()+1;
        Instance simple = new SparseInstance(dimension);
        
        if(selectFeature.getJcAuthorName()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JC_AUTHOR_NAME), authorSimilarity.makeJaccardSimilarity(pubA, pubB));
        
        if(selectFeature.getJcAffiliation()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JC_AFFILIATION), affiliationSimilarity.makeJaccardSimilarity(pubA, pubB));
        
        if(selectFeature.getJcCoAuthor()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JC_CO_AUTHOR), coAuthorSimilarity.makeJaccardSimilarity(pubA, pubB));
        
        if(selectFeature.getJcKeyword()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JC_KEYWORD), keywordSimilarity.makeJaccardSimilarity(pubA, pubB));
        
        if(selectFeature.getJcInterestingKeyword()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JC_INTERESTING_KEYWORD), interestKeywordSimilarity.makeJaccardSimilarity(pubA, pubB));        
        
        if(selectFeature.getLevenshteinAuthorname()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.LEVENSHTEIN_AUTHOR_NAME), authorSimilarity.makeLevenshteinSimilarity(pubA, pubB));        
        
        if(selectFeature.getLevenshteinAffiliaiton()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.LEVENSHTEIN_AFFILIATION), affiliationSimilarity.makeLevenshteinSimilarity(pubA, pubB));        
        
        if(selectFeature.getJaroAuthorName()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JARO_AUTHOR_NAME), authorSimilarity.makeJaroSimilarity(pubA, pubB));  
        
        if(selectFeature.getJaroAffiliation()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JARO_AFFILIATION), affiliationSimilarity.makeJaroSimilarity(pubA, pubB));     
        
        if(selectFeature.getJarowinklerAuthorName()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JAROWINKLER_AUTHOR_NAME), authorSimilarity.makeJaroWinklerSimilarity(pubA, pubB));        
        
        if(selectFeature.getJarowinklerAffiliaiton()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JAROWIKLER_AFFILIATION), affiliationSimilarity.makeJaroWinklerSimilarity(pubA, pubB));        
        
        if(selectFeature.getSmithWatermanAuthorName()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.SMITHWATERMAN_AUTHOR_NAME), authorSimilarity.makeSmithWatermanSimilarity(pubA, pubB)); 
        
        if(selectFeature.getSmithWatermanAffiliation()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.SMITHWATERMAN_AFFILIATION), affiliationSimilarity.makeSmithWatermanSimilarity(pubA, pubB)); 
        
        if(selectFeature.getMongeElkanAuthorName()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.MONGEELKAN_AUTHOR_NAME), authorSimilarity.makeMongeElkanSimilarity(pubA, pubB));        
        
        if(selectFeature.getMongeElkanAffiliation()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.MONGEELKAN_AFFILIATION), affiliationSimilarity.makeMongeElkanSimilarity(pubA, pubB)); 
    
        // Add more feature here
        int sum = pubA.getMainAuthor().getAuthorResult() + pubB.getMainAuthor().getAuthorResult();
        
        if(sum == 2) 
            simple.setValue((Attribute)instancesData.attribute(selectFeature.getNumberSelectFeature()), "same"); 
        else 
            simple.setValue((Attribute) instancesData.attribute(selectFeature.getNumberSelectFeature()), "diff"); 
        
        return simple;
    }
    /**
     * tiendv
     * @param rootDirectory
     * @param selectFeatures
     * @return 
     */
     public static Instances buildVectorsFromFolderWithSelectFeatures(String rootDirectory, Feature selectFeatures) throws ParserConfigurationException, TransformerConfigurationException, TransformerException
    {
        int dimension = selectFeatures.getNumberSelectFeature() + 1;
        Instances instancesData = buildVectorWithFeatures(1000, selectFeatures);
        
        final ReadXML reader = new ReadXML();
        Main.taLog.append("Root: -" + rootDirectory);
        Main.taLog.append("\n");
        File root = new File(rootDirectory);
        File[] listChild = root.listFiles();
        for (int i = 0; i < listChild.length; i++) {
            File child = listChild[i];
            if(child.isDirectory())
            {
               Main.taLog.append("\t-" + child.getName());
               Main.taLog.append("\n");
                File[] listSubFolder = child.listFiles();
                int length = 0;                
                while(length < listSubFolder.length)
                {
                    //Main.taLog.append(rootDirectory);
                    Main.taLog.append("\t \t-" + listSubFolder[length].getName());
                    Main.taLog.append("\n");
                    File[] listFiles = listSubFolder[length].listFiles();
                    int lengthListFile = 0;
                    Publication[] data = new Publication[listFiles.length];
                    while(lengthListFile < listFiles.length)
                    {
                        File file = listFiles[lengthListFile];
                        if(file.isFile())
                        {                            
                            Publication publication = reader.importPubFromXML(file.getAbsolutePath());
                            data[lengthListFile] = publication;                            
                        }
                        lengthListFile++;
                    } 
                    int count = 0;
                    for (int index = 0; index < listFiles.length - 1; index++) {
                        for (int index2 = index + 1; index2 < listFiles.length; index2++) {
                             count++;
                             Instance simple = calculateVectorWithSelectFeatures(instancesData, data[index], data[index2], selectFeatures, child.getName());
                                instancesData.add(simple);
                             Main.taLog.append("\t\t\t-" + data[index].toString() + 
                                " vs " + data[index2].toString() + 
                                " label: " + instancesData.get(count - 1).stringValue(selectFeatures.getNumberSelectFeature()));
                             Main.taLog.append("\n");
                             // Make xml file
                             
                             WriteXMLFile writeFile = new WriteXMLFile();
                             String name= Integer.toString(index)+Integer.toString(index2);
                             writeFile.makeXMLFile(data[index], data[index2],name, listSubFolder[length].getName());
                        }
                    }
                    length++;
                }                
            }            
        }
        return instancesData;
    }
    
}
