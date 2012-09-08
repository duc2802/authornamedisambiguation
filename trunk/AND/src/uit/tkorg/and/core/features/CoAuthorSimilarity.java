/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.core.features;

import uit.tkorg.and.core.similarity.JaccardSimilarity;
import uit.tkorg.and.models.Author;
import uit.tkorg.and.models.Publication;

/**
 *
 * @author DucHuynh
 */
public class CoAuthorSimilarity implements FeaturesBase{

    @Override
    public float makeJaccardSimilarity(Publication publicationA, Publication publicationB) {
        StringBuilder coauthorAString = new StringBuilder();        
        for (Author author : publicationA.getCoAuthor()) {
            coauthorAString.append(author.getAuthorName());
            coauthorAString.append(" ");
        }
        
        StringBuilder coauthorBString = new StringBuilder();
        for (Author author : publicationB.getCoAuthor()) {
            coauthorBString.append(author.getAuthorName());
            coauthorBString.append(" ");
        }
        
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        return jaccardSimilarity.getSimilarity(coauthorAString.toString(), coauthorBString.toString());        
    }
    
    /**
     * Duc Huynh
     * @param publicationA
     * @param publicationB
     * @return 
     */
    public float makeCoauthorAffiliationJaccardSimilarity(Publication publicationA, Publication publicationB)
    { 
        StringBuilder affCoauthorAString = new StringBuilder();        
        for (Author author : publicationA.getCoAuthor()) {
            affCoauthorAString.append(author.getAuthorAfflication());
            affCoauthorAString.append(" ");
        }
        
        StringBuilder affCoauthorBString = new StringBuilder();
        for (Author author : publicationB.getCoAuthor()) {
            affCoauthorBString.append(author.getAuthorAfflication());
            affCoauthorBString.append(" ");
        }
        
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        return jaccardSimilarity.getSimilarity(affCoauthorAString.toString(), affCoauthorBString.toString());  
    }

    @Override
    public float makeLevenshteinSimilarity(Publication publicationA, Publication publicationB) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float makeJaroSimilarity(Publication publicationA, Publication publicationB) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float makeSmithWatermanSimilarity(Publication publicationA, Publication publicationB) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float makeOverlapCoefficientSimilarity(Publication publicationA, Publication publicationB) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float makeJaroWinklerSimilarity(Publication publicationA, Publication publicationB) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
