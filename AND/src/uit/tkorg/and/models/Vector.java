/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.models;

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
        
    public static Instances buildVector(int dimension)
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
        
        instances = new Instances("AuthorName", vector, 100);
        instances.setClassIndex(5);
        return instances;
    }
    
    public static Instances buildDataVetor(String path, String label)
    {
        Instances instancesData = buildVector(6);  
        
        int NUMBER_OF_XML = 10;
        Publication[] data = new Publication[NUMBER_OF_XML];
        ReadXML reader = new ReadXML(path);
        for(int i = 0; i < NUMBER_OF_XML; i++){
            Publication publication = reader.importPubFromXML(""+ (i + 1) +"");    
            data[i] = publication;
        }
          
        AuthorSimilarity authorSimilarity = new AuthorSimilarity();
        AffiliationSimilarity affiliationSimilarity = new AffiliationSimilarity();
        CoAuthorSimilarity coAuthorSimilarity = new CoAuthorSimilarity();
        KeywordSimilarity keywordSimilarity = new KeywordSimilarity();
        InterestKeywordSimilarity interestKeywordSimilarity = new InterestKeywordSimilarity();
        
        int count = 0;
        for(int i = 0; i < NUMBER_OF_XML - 1; i++){
            for(int j = i + 1; j < NUMBER_OF_XML; j++)
            {  
                count++;
                Instance simple = new SparseInstance(6);
                simple.setValue((Attribute)instancesData.attribute(0), authorSimilarity.makeJaccardSimilarity(data[i], data[j]));
                simple.setValue((Attribute)instancesData.attribute(1), affiliationSimilarity.makeJaccardSimilarity(data[i], data[j]));
                simple.setValue((Attribute)instancesData.attribute(2), coAuthorSimilarity.makeJaccardSimilarity(data[i], data[j]));
                simple.setValue((Attribute)instancesData.attribute(3), keywordSimilarity.makeJaccardSimilarity(data[i], data[j]));
                simple.setValue((Attribute)instancesData.attribute(4), interestKeywordSimilarity.makeJaccardSimilarity(data[i], data[j]));
                if(count % 2 == 0){
                    simple.setValue((Attribute)instancesData.attribute(5), "same");
                }else{
                    simple.setValue((Attribute)instancesData.attribute(5), "diff");
                }
                instancesData.add(simple);
            }            
        }
        System.out.println("Number vector : " +count);
        instancesData.setClassIndex(5);
       return instancesData;
    }
}
