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
    
}
