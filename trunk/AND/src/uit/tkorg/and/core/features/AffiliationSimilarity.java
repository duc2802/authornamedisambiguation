/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.core.features;

import java.util.ArrayList;
import uit.tkorg.and.core.similarity.JaccardSimilarity;
import uit.tkorg.and.core.tokenisers.TokeniserWhitespace;
import uit.tkorg.and.models.Author;
import uit.tkorg.and.models.Publication;

/**
 *
 * @author DucHuynh
 */
public class AffiliationSimilarity implements FeaturesBase{

    @Override
    public float makeJaccardSimilarity(Publication publicationA, Publication publicationB) {
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        return jaccardSimilarity.getSimilarity(publicationA.getMainAuthor().getAuthorAfflication(), 
                publicationB.getMainAuthor().getAuthorAfflication());
    }
    
    /**
     * Duc Huynh
     * @param publicationA
     * @param publicationB
     * @return 
     */
    public float sumTFIDFWeightsOfSharedTerms(Publication publicationA, Publication publicationB)
    {
        TokeniserWhitespace tokeniserWhitespace = new TokeniserWhitespace();
        ArrayList<String> affATokens = tokeniserWhitespace.tokenizeToArrayList(publicationA.getMainAuthor().getAuthorAfflication());
        ArrayList<String> affBTokens = tokeniserWhitespace.tokenizeToArrayList(publicationB.getMainAuthor().getAuthorAfflication());
        ArrayList<String> intersectionTokens = tokeniserWhitespace.findIntersection(affATokens, affBTokens);
        //calculate TFIDF for each of term in intersectionTokens.
        return 0;
    }
}
