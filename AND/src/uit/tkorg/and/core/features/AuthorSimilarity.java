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
public class AuthorSimilarity implements FeaturesBase {    
    
    @Override
    public float makeJaccardSimilarity(Publication publicationA, Publication publicationB)
    {        
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        return jaccardSimilarity.getSimilarity(publicationA.getMainAuthor().getAuthorName(), 
                publicationB.getMainAuthor().getAuthorName());
    }
}
