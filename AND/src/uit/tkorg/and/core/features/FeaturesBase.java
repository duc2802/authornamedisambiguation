/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.core.features;

import uit.tkorg.and.models.Publication;

/**
 *
 * @author DucHuynh
 */
public interface FeaturesBase {    
    public float makeJaccardSimilarity(Publication publicationA, Publication publicationB);
}
