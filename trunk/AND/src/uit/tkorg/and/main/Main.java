/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.main;

import java.util.ArrayList;
import uit.tkorg.and.core.features.AffiliationSimilarity;
import uit.tkorg.and.core.features.AuthorSimilarity;
import uit.tkorg.and.core.features.CoAuthorSimilarity;
import uit.tkorg.and.core.features.KeywordSimilarity;
import uit.tkorg.and.models.Publication;
import uit.tkorg.and.utils.ReadXML;
import weka.classifiers.trees.RandomForest;

/**
 *
 * @author Admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int NUMBER_OF_XML = 10;
        Publication[] data = new Publication[NUMBER_OF_XML];
        for(int i = 0; i < NUMBER_OF_XML; i++){
            System.out.println(i);
            Publication publication = ReadXML.importPubFromXML(""+ (i + 1) +"");    
            data[i] = publication;
        }
          
        AuthorSimilarity authorSimilarity = new AuthorSimilarity();
        AffiliationSimilarity affiliationSimilarity = new AffiliationSimilarity();
        CoAuthorSimilarity coAuthorSimilarity = new CoAuthorSimilarity();
        KeywordSimilarity keywordSimilarity = new KeywordSimilarity();
        
        for(int i = 0; i < NUMBER_OF_XML - 1; i++){
            for(int j = i + 1; j < NUMBER_OF_XML; j++)
            {                
                System.out.print(authorSimilarity.makeJaccardSimilarity(data[i], data[j]));
                System.out.print("\t");
                System.out.print(affiliationSimilarity.makeJaccardSimilarity(data[i], data[j]));
                System.out.print("\t");
                System.out.print(coAuthorSimilarity.makeJaccardSimilarity(data[i], data[j]));
                System.out.print("\t");
                System.out.print(keywordSimilarity.makeJaccardSimilarity(data[i], data[j]));
                System.out.print("\t");                
                System.out.println();
            }            
        }
    }
}
