/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.core.features;

import java.util.ArrayList;
import uit.tkorg.and.core.similarity.JaccardSimilarity;
import uit.tkorg.and.core.similarity.Jaro;
import uit.tkorg.and.core.similarity.JaroWinkler;
import uit.tkorg.and.core.similarity.Levenshtein;
import uit.tkorg.and.core.similarity.MongeElkan;
import uit.tkorg.and.core.similarity.SmithWaterman;
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

    @Override
    public float makeLevenshteinSimilarity(Publication publicationA, Publication publicationB) {
        Levenshtein levenshteinSimilarity = new Levenshtein();
        return levenshteinSimilarity.getSimilarity(publicationA.getMainAuthor().getAuthorAfflication(), 
                publicationB.getMainAuthor().getAuthorAfflication());
    }

    @Override
    public float makeJaroSimilarity(Publication publicationA, Publication publicationB) {
        Jaro jaroSimilarity = new Jaro();
        return jaroSimilarity.getSimilarity(publicationA.getMainAuthor().getAuthorAfflication(), 
                publicationB.getMainAuthor().getAuthorAfflication());
    }

    @Override
    public float makeSmithWatermanSimilarity(Publication publicationA, Publication publicationB) {
       SmithWaterman swSimilarity = new SmithWaterman();
       return swSimilarity.getSimilarity(publicationA.getMainAuthor().getAuthorAfflication(), 
                publicationB.getMainAuthor().getAuthorAfflication());
    }

    @Override
    public float makeOverlapCoefficientSimilarity(Publication publicationA, Publication publicationB) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float makeJaroWinklerSimilarity(Publication publicationA, Publication publicationB) {
        JaroWinkler jaroWinklerSimilarity = new JaroWinkler(); 
        return jaroWinklerSimilarity.getSimilarity(publicationA.getMainAuthor().getAuthorAfflication(), 
                publicationB.getMainAuthor().getAuthorAfflication());
    }

    @Override
    public float makeMongeElkanSimilarity(Publication publicationA, Publication publicationB) {
        MongeElkan mongeElakan = new MongeElkan();
        return mongeElakan.getSimilarity(publicationA.getMainAuthor().getAuthorAfflication(), 
                publicationB.getMainAuthor().getAuthorAfflication());
    }
}
