/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.models;

/**
 *
 * @author DucHuynh
 */
public class PairPublication {
    
    private Publication publicationA;
    private Publication publicationB;
    
    private int classificationLabel;
    
    public PairPublication()
    {
        publicationA = new Publication();
        publicationB = new Publication();
        
        classificationLabel = 0;
    }

    public Publication getPublicationA() {
        return publicationA;
    }

    public void setPublicationA(Publication publicationA) {
        this.publicationA = publicationA;
    }

    public Publication getPublicationB() {
        return publicationB;
    }

    public void setPublicationB(Publication publicationB) {
        this.publicationB = publicationB;
    }

    public int getClassificationLabel() {
        return classificationLabel;
    }

    public void setClassificationLabel(int classificationLabel) {
        this.classificationLabel = classificationLabel;
    }
    
    
}
