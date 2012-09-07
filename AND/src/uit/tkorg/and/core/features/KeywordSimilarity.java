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
public class KeywordSimilarity implements FeaturesBase
{
    @Override
    public float makeJaccardSimilarity(Publication publicationA, Publication publicationB) {
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        return jaccardSimilarity.getSimilarity(publicationA.getPubKeywords(), publicationB.getPubKeywords());
    }

    @Override
    public float makeLevenshteinSimilarity(Publication publicationA, Publication publicationB) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
