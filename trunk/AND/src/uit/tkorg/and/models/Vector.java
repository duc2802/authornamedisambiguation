/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.models;

import java.io.File;
import uit.tkorg.and.core.features.AffiliationSimilarity;
import uit.tkorg.and.core.features.AuthorSimilarity;
import uit.tkorg.and.core.features.CoAuthorSimilarity;
import uit.tkorg.and.core.features.InterestKeywordSimilarity;
import uit.tkorg.and.core.features.KeywordSimilarity;
import uit.tkorg.and.utils.ReadXML;
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
        
    public static Instances buildVector(int dimension, int numberVector)
    {
        Instances instances;
        // Value feature 
        FastVector vectorFeature = new FastVector(5);
        Attribute attAuthorSimilarity = new Attribute("AuthorSimilarity");
        Attribute attAffiliationSimilarity = new Attribute("AffiliationSimilarity");
        Attribute attCoauthorSimilarity = new Attribute("CoauthorSimilarity");
        Attribute attKeywordSimilarity = new Attribute("KeywordSimilarity");
        Attribute attInterestSimilarity = new Attribute("InterestSimilarity");
        
        // Declare the class attribute along with its values
        FastVector classValue = new FastVector(2);
        classValue.addElement("same");
        classValue.addElement("diff");
        Attribute classAttribute = new Attribute("Class", classValue);
        
        // Declare the feature vector
        FastVector vector = new FastVector(6);
        vector.addElement(attAuthorSimilarity);    
        vector.addElement(attAffiliationSimilarity);    
        vector.addElement(attCoauthorSimilarity);    
        vector.addElement(attKeywordSimilarity);
        vector.addElement(attInterestSimilarity);
        vector.addElement(classAttribute);
        
        instances = new Instances("AuthorName", vector, numberVector);
        instances.setClassIndex(5);
        return instances;
    }
    
    public static Instance calculateVector(Instances instancesData, Publication pubA, Publication pubB, int dimension, String label)
    {                
        AuthorSimilarity authorSimilarity = new AuthorSimilarity();
        AffiliationSimilarity affiliationSimilarity = new AffiliationSimilarity();
        CoAuthorSimilarity coAuthorSimilarity = new CoAuthorSimilarity();
        KeywordSimilarity keywordSimilarity = new KeywordSimilarity();
        InterestKeywordSimilarity interestKeywordSimilarity = new InterestKeywordSimilarity();
        
        Instance simple = new SparseInstance(dimension);
        simple.setValue((Attribute) instancesData.attribute(0), authorSimilarity.makeJaccardSimilarity(pubA, pubB));
        simple.setValue((Attribute) instancesData.attribute(1), affiliationSimilarity.makeJaccardSimilarity(pubA, pubB));
        simple.setValue((Attribute) instancesData.attribute(2), coAuthorSimilarity.makeJaccardSimilarity(pubA, pubB));
        simple.setValue((Attribute) instancesData.attribute(3), keywordSimilarity.makeJaccardSimilarity(pubA, pubB));
        simple.setValue((Attribute) instancesData.attribute(4), interestKeywordSimilarity.makeJaccardSimilarity(pubA, pubB));        
        simple.setValue((Attribute) instancesData.attribute(5), label);       
        return simple;
    }
    
    public static Instances buildVectorsFromFolder(String rootDirectory)
    {
        Instances instancesData = buildVector(6, 1000);
        
        ReadXML reader = new ReadXML();
        System.out.println("Root: -" + rootDirectory);
        File root = new File(rootDirectory);
        File[] listChild = root.listFiles();
        for (int i = 0; i < listChild.length; i++) {
            File child = listChild[i];
            if(child.isDirectory())
            {
                System.out.println("\t-" + child.getName());
                File[] listSubFolder = child.listFiles();
                int length = 0;                
                while(length < listSubFolder.length)
                {
                    System.out.println("\t \t-" + listSubFolder[length].getName());
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
                             Instance simple = calculateVector(instancesData, data[index], data[index2], 6, child.getName());
                                instancesData.add(simple);
                             System.out.println("\t\t\t-" + data[index].toString() + 
                                " vs " + data[index2].toString() + 
                                " label: " + child.getName());
                        }
                    }
                    length++;
                }                
            }            
        }
        return instancesData;
    }
    
    public static Instances buildDataVetor(String path, String label, int number)
    {
        Instances instancesData = buildVector(6, 100);  
        
        int NUMBER_OF_XML = number;
        Publication[] data = new Publication[NUMBER_OF_XML];
        ReadXML reader = new ReadXML(path);
        for(int i = 0; i < NUMBER_OF_XML; i++){
            Publication publication = reader.importPubFromXML(""+ (i + 1) +"");    
            data[i] = publication;
            System.out.println(i);
        }
        
        AuthorSimilarity authorSimilarity = new AuthorSimilarity();
        AffiliationSimilarity affiliationSimilarity = new AffiliationSimilarity();
        CoAuthorSimilarity coAuthorSimilarity = new CoAuthorSimilarity();
        KeywordSimilarity keywordSimilarity = new KeywordSimilarity();
        InterestKeywordSimilarity interestKeywordSimilarity = new InterestKeywordSimilarity();
        
        int count = 0;
        for (int i = 0; i < NUMBER_OF_XML - 1; i++) {
            for (int j = i + 1; j < NUMBER_OF_XML; j++) {
                count++;
                Instance simple = new SparseInstance(6);
                simple.setValue((Attribute) instancesData.attribute(0), authorSimilarity.makeJaccardSimilarity(data[i], data[j]));
                simple.setValue((Attribute) instancesData.attribute(1), affiliationSimilarity.makeJaccardSimilarity(data[i], data[j]));
                simple.setValue((Attribute) instancesData.attribute(2), coAuthorSimilarity.makeJaccardSimilarity(data[i], data[j]));
                simple.setValue((Attribute) instancesData.attribute(3), keywordSimilarity.makeJaccardSimilarity(data[i], data[j]));
                simple.setValue((Attribute) instancesData.attribute(4), interestKeywordSimilarity.makeJaccardSimilarity(data[i], data[j]));
                if (i < 10 && j < 10) {
                    simple.setValue((Attribute) instancesData.attribute(5), "same");
                } else if (i < 10 && j >= 10) {
                    simple.setValue((Attribute) instancesData.attribute(5), "diff");
                } else if (i >= 10) {
                    simple.setValue((Attribute) instancesData.attribute(5), "same");
                }
                instancesData.add(simple);
            }
        }
        System.out.println("Number vector : " +count);
        instancesData.setClassIndex(5);
       return instancesData;
    }
    
    
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
            Attribute attAuthorSimilarity = new Attribute("AuthorSimilarity");
            vector.addElement(attAuthorSimilarity);
        }
        // JaccardAffiliation
        if(selectFeature.getJcAffiliation()==true)
        {
           Attribute attAffiliationSimilarity = new Attribute("AffiliationSimilarity");
           vector.addElement(attAffiliationSimilarity);    
        }
        // Jaccard Coauthor name
        if(selectFeature.getJcCoAuthor()==true)
        {
           Attribute attCoauthorSimilarity = new Attribute("CoauthorSimilarity");
           vector.addElement(attCoauthorSimilarity);    
        }
        // Jaccard KeywordS 
        if(selectFeature.getJcKeyword()==true)
        {
           Attribute attKeywordSimilarity = new Attribute("KeywordSimilarity");
           vector.addElement(attKeywordSimilarity);    
        }
        // Jaccard KeywordS 
        if(selectFeature.getJcInterestingKeyword()==true)
        {
           Attribute attInterestSimilarity = new Attribute("InterestSimilarity");
           vector.addElement(attInterestSimilarity);    
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
        simple.setValue((Attribute) instancesData.attribute("JCAuthorName"), authorSimilarity.makeJaccardSimilarity(pubA, pubB));
        
        if(selectFeature.getJcAffiliation()==true)
        simple.setValue((Attribute) instancesData.attribute("JCAffiliation"), affiliationSimilarity.makeJaccardSimilarity(pubA, pubB));
        
        if(selectFeature.getJcCoAuthor()==true)
        simple.setValue((Attribute) instancesData.attribute("JCCoAuthor"), coAuthorSimilarity.makeJaccardSimilarity(pubA, pubB));
        
        if(selectFeature.getJcKeyword()==true)
        simple.setValue((Attribute) instancesData.attribute("JCKeyWord"), keywordSimilarity.makeJaccardSimilarity(pubA, pubB));
        
        if(selectFeature.getJcInterestingKeyword()==true)
        simple.setValue((Attribute) instancesData.attribute("JCInterestingKeyWord"), interestKeywordSimilarity.makeJaccardSimilarity(pubA, pubB));        
        
        if(selectFeature.getLevenshteinAuthorname()==true)
        simple.setValue((Attribute) instancesData.attribute("LevenshteinAuthorname"), authorSimilarity.makeLevenshteinSimilarity(pubA, pubB));        
        
        if(selectFeature.getLevenshteinAffiliaiton()==true)
        simple.setValue((Attribute) instancesData.attribute("LevenshteinAffiliation"), affiliationSimilarity.makeLevenshteinSimilarity(pubA, pubB));        
    
        // Add more feature here
        
        simple.setValue((Attribute) instancesData.attribute("Label"), label);       
        return simple;
    }
    /**
     * tiendv
     * @param rootDirectory
     * @param selectFeatures
     * @return 
     */
     public static Instances buildVectorsFromFolderWithSelectFeatures(String rootDirectory, Feature selectFeatures)
    {
        int dimension = selectFeatures.getNumberSelectFeature()+1;
        Instances instancesData = buildVector(dimension, 1000);
        
        ReadXML reader = new ReadXML();
        System.out.println("Root: -" + rootDirectory);
        File root = new File(rootDirectory);
        File[] listChild = root.listFiles();
        for (int i = 0; i < listChild.length; i++) {
            File child = listChild[i];
            if(child.isDirectory())
            {
                System.out.println("\t-" + child.getName());
                File[] listSubFolder = child.listFiles();
                int length = 0;                
                while(length < listSubFolder.length)
                {
                    System.out.println("\t \t-" + listSubFolder[length].getName());
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
                             System.out.println("\t\t\t-" + data[index].toString() + 
                                " vs " + data[index2].toString() + 
                                " label: " + child.getName());
                        }
                    }
                    length++;
                }                
            }            
        }
        return instancesData;
    }
    
}
