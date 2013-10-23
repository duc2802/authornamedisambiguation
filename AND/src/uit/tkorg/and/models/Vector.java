/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.models;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * DucHuynh.
     * @param instancesData
     * @param pubA
     * @param pubB
     * @param Feature : select features
     * @param label
     * @return 
     */
    public static Instance calculateVectorOfPairPublication(Instances instancesData, PairPublication pair, Feature selectFeature)
    {                
        AuthorSimilarity authorSimilarity = new AuthorSimilarity();
        AffiliationSimilarity affiliationSimilarity = new AffiliationSimilarity();
        CoAuthorSimilarity coAuthorSimilarity = new CoAuthorSimilarity();
        KeywordSimilarity keywordSimilarity = new KeywordSimilarity();
        InterestKeywordSimilarity interestKeywordSimilarity = new InterestKeywordSimilarity();
        int dimension = selectFeature.getNumberSelectFeature()+1;
        Instance simple = new SparseInstance(dimension);
        
        if(selectFeature.getJcAuthorName()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JC_AUTHOR_NAME), authorSimilarity.makeJaccardSimilarity(pair.getPublicationA(), pair.getPublicationB()));
        
        if(selectFeature.getJcAffiliation()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JC_AFFILIATION), affiliationSimilarity.makeJaccardSimilarity(pair.getPublicationA(), pair.getPublicationB()));
        
        if(selectFeature.getJcCoAuthor()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JC_CO_AUTHOR), coAuthorSimilarity.makeJaccardSimilarity(pair.getPublicationA(), pair.getPublicationB()));
        
        if(selectFeature.getJcKeyword()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JC_KEYWORD), keywordSimilarity.makeJaccardSimilarity(pair.getPublicationA(), pair.getPublicationB()));
        
        if(selectFeature.getJcInterestingKeyword()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JC_INTERESTING_KEYWORD), interestKeywordSimilarity.makeJaccardSimilarity(pair.getPublicationA(), pair.getPublicationB()));        
        
        if(selectFeature.getLevenshteinAuthorname()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.LEVENSHTEIN_AUTHOR_NAME), authorSimilarity.makeLevenshteinSimilarity(pair.getPublicationA(), pair.getPublicationB()));        
        
        if(selectFeature.getLevenshteinAffiliaiton()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.LEVENSHTEIN_AFFILIATION), affiliationSimilarity.makeLevenshteinSimilarity(pair.getPublicationA(), pair.getPublicationB()));        
        
        if(selectFeature.getJaroAuthorName()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JARO_AUTHOR_NAME), authorSimilarity.makeJaroSimilarity(pair.getPublicationA(), pair.getPublicationB()));  
        
        if(selectFeature.getJaroAffiliation()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JARO_AFFILIATION), affiliationSimilarity.makeJaroSimilarity(pair.getPublicationA(), pair.getPublicationB()));     
        
        if(selectFeature.getJarowinklerAuthorName()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JAROWINKLER_AUTHOR_NAME), authorSimilarity.makeJaroWinklerSimilarity(pair.getPublicationA(), pair.getPublicationB()));        
        
        if(selectFeature.getJarowinklerAffiliaiton()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.JAROWIKLER_AFFILIATION), affiliationSimilarity.makeJaroWinklerSimilarity(pair.getPublicationA(), pair.getPublicationB()));        
        
        if(selectFeature.getSmithWatermanAuthorName()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.SMITHWATERMAN_AUTHOR_NAME), authorSimilarity.makeSmithWatermanSimilarity(pair.getPublicationA(), pair.getPublicationB())); 
        
        if(selectFeature.getSmithWatermanAffiliation()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.SMITHWATERMAN_AFFILIATION), affiliationSimilarity.makeSmithWatermanSimilarity(pair.getPublicationA(), pair.getPublicationB())); 
        
        if(selectFeature.getMongeElkanAuthorName()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.MONGEELKAN_AUTHOR_NAME), authorSimilarity.makeMongeElkanSimilarity(pair.getPublicationA(), pair.getPublicationB()));        
        
        if(selectFeature.getMongeElkanAffiliation()==true)
        simple.setValue((Attribute) instancesData.attribute(Feature.MONGEELKAN_AFFILIATION), affiliationSimilarity.makeMongeElkanSimilarity(pair.getPublicationA(), pair.getPublicationB())); 
    
        // Add more feature here
       
        if(pair.getClassificationLabel() == 1) 
            simple.setValue((Attribute)instancesData.attribute(selectFeature.getNumberSelectFeature()), "same"); 
        else 
            simple.setValue((Attribute) instancesData.attribute(selectFeature.getNumberSelectFeature()), "diff"); 
        
        return simple;
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
     public static PairPublication[] buildVectorsFromFolderPairPublication(String rootDirectory)
    {
        PairPublication[] pairList = null;
        final ReadXML reader = new ReadXML();
//        Main.taLog.append("Root: -" + rootDirectory);
//        Main.taLog.append("\n");
        File root = new File(rootDirectory);
        File[] listChild = root.listFiles();
        
        pairList = new PairPublication[listChild.length];
        
        for (int i = 0; i < listChild.length; i++) {
            File file = listChild[i];           
//            Main.taLog.append("\t \t-" + file.getName());
//            Main.taLog.append("\n");                                            
            PairPublication pair = reader.readPairPublicationFromXML(file.getAbsolutePath());    
            pairList[i] = pair;
        }
        return pairList;
    }
    
     public static Instances buildVectorTrain(PairPublication[] data, Feature selectFeatures, int start, int end)
     {                
        Instances instancesData = buildVectorWithFeatures(100000, selectFeatures);        
        for(int i = start; i < end; i++)
        {
            //System.out.println("File  : " + i);
            Instance simple = calculateVectorOfPairPublication(instancesData, data[i], selectFeatures);
            instancesData.add(simple);
        }         
        return instancesData;
        
     }
         
     public static Instances buildVectorTest(PairPublication[] data, Feature selectFeatures, int start, int end)
     {        
        Instances instancesData = buildVectorWithFeatures(100000, selectFeatures);
        for(int i = 0; i < data.length; i++)
        {
            if(i >= end || i < start)
            {
                //System.out.println("File: " + i);
                Instance simple = calculateVectorOfPairPublication(instancesData, data[i], selectFeatures);
                instancesData.add(simple);
            }
        } 
        return instancesData;
     }
     
     public static Instances buildVector(PairPublication[] data, Feature selectFeatures)
     {        
        Instances instancesData = buildVectorWithFeatures(100000, selectFeatures);
        for(int i = 0; i < data.length; i++)
        {  
            Instance simple = calculateVectorOfPairPublication(instancesData, data[i], selectFeatures);
            instancesData.add(simple);
        } 
        return instancesData;
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
        Instances instancesData = buildVectorWithFeatures(100000, selectFeatures);
        
        final ReadXML reader = new ReadXML();        
        File root = new File(rootDirectory);
        File[] listChild = root.listFiles();
        for (int i = 0; i < listChild.length; i++) {
            File child = listChild[i];
            if(child.isDirectory())
            { 
                File[] listFiles = child.listFiles();
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
                        
                         Instance simple = calculateVectorWithSelectFeatures(instancesData, data[index], data[index2], selectFeatures, child.getName());
                         
                         instancesData.add(simple);
//                         Main.taLog.append("\t\t\t -" + count + "\t" + data[index].toString() + 
//                            " \t " + data[index2].toString() + 
//                            " \t " + instancesData.instance(count - 1).stringValue(selectFeatures.getNumberSelectFeature()));
//                         Main.taLog.append("\n");
//                         // Make xml file
                         WriteXMLFile writeFile = new WriteXMLFile();
                         String name= Integer.toString(index)+Integer.toString(index2);
                         writeFile.makeXMLFile(data[index], data[index2], Integer.toString(count), child.getName());
                    }
                }
            }            
        }
        return instancesData;
    }    
     
    public void makePairFile(String rootDirectory) throws ParserConfigurationException, TransformerConfigurationException, TransformerException
    {
        final ReadXML reader = new ReadXML();        
        File root = new File(rootDirectory);
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
    
        public void makePairFileWithTwoDifferenceAuthor(String rootDirectory) throws ParserConfigurationException, TransformerConfigurationException, TransformerException
    {
        int lengthListFile = 0;
        int lengthListFile2 = 0;
        final ReadXML reader = new ReadXML();
        // Read root
        File root = new File(rootDirectory);
        File[] listChild = root.listFiles();
        Publication[] dataFoder2 = null;
        Publication[] data = null;
        // Data foder 1
            File child = listChild[0];
            System.out.println(child.getAbsolutePath());
            if(child.isDirectory())
            { 
                File[] listFiles = child.listFiles();
                
                // Xong phan doc du lieu cua forder dau tien
                data = new Publication[listFiles.length];
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
            }
            
            // Data foder 2
            
            File childForder2 = listChild[1];
            System.out.println(childForder2.getAbsolutePath());
            if(childForder2.isDirectory())
            { 
                File[] listFiles = childForder2.listFiles();
                // Xong phan doc du lieu cua forder dau tien
                dataFoder2 = new Publication[listFiles.length];
                while(lengthListFile2 < listFiles.length)
                {
                    File file = listFiles[lengthListFile2];
                    System.out.println(file.getAbsolutePath());
                    if(file.isFile())
                    {                            
                        Publication publication = reader.importPubFromXML(file.getAbsolutePath());
                        dataFoder2[lengthListFile2] = publication;                            
                    }                    
                    lengthListFile2++;                    
                } 
            }
            int count = 0;
            for(int indexfile1=0; indexfile1<lengthListFile-1; indexfile1++)
            {
                for(int indexfile2=0; indexfile2<lengthListFile2-1;indexfile2++ )
                {    count ++;
                     WriteXMLFile writeFile = new WriteXMLFile();                         
                     writeFile.makeXMLFileWithTwoDifferenceAuthors(data[indexfile1], dataFoder2[indexfile2], Integer.toString(count), child.getName());
                    
                }                  
            }
//                int count = 0;
//                int countSame = 0;
//                int countDiff = 0;
//                for (int index = 0; index < listFiles.length - 1; index++) {
//                    for (int index2 = index + 1; index2 < listFiles.length; index2++) {
//                         count++;
//                         int sum = data[index].getMainAuthor().getAuthorResult() + data[index2].getMainAuthor().getAuthorResult();        
//                        if(sum == 2) 
//                            countSame++;
//                        else 
//                            countDiff++;
//                        
//                         WriteXMLFile writeFile = new WriteXMLFile();                         
//                         writeFile.makeXMLFile(data[index], data[index2], Integer.toString(count), child.getName());
//                    }
//                }
//            }
//        }
    }
     
    public static void main(String[] args) {
        try {
            String pathFile = "c:\\DataLoad\\";
            Vector vector = new Vector();
            vector.makePairFileWithTwoDifferenceAuthor(pathFile);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Vector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Vector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Vector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
