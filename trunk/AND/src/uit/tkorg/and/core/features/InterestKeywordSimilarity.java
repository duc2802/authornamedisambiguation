/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.core.features;

import uit.tkorg.and.core.similarity.JaccardSimilarity;
import uit.tkorg.and.models.Publication;

/**
 *
 * @author DucHuynh
 */
public class InterestKeywordSimilarity implements FeaturesBase{

    @Override
    public float makeJaccardSimilarity(Publication publicationA, Publication publicationB) {
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        return jaccardSimilarity.getSimilarity(publicationA.getMainAuthor().getInterest(), 
                                                publicationB.getMainAuthor().getInterest());
    }

    @Override
    public float makeLevenshteinSimilarity(Publication publicationA, Publication publicationB) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
