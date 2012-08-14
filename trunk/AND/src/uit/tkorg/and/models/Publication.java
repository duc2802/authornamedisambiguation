/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.models;

import java.util.List;

/**
 *
 * @author tiendv
 */
public class Publication {
    String digitalName;
    String nameSeach;
    String pubTitle;
    String pubKeywords;
    String pubAbstract;
    String pubYearPublish;
    String publisher;

    String journal;
    Author mainAuthor;
    List <Author> coAuthor;

    public Publication(String digitalName, String nameSeach, String pubTitle, String pubKeywords, String pubAbstract, String pubYearPublish, String publisher, String journal, Author mainAuthor, List<Author> coAuthor) {
        this.digitalName = digitalName;
        this.nameSeach = nameSeach;
        this.pubTitle = pubTitle;
        this.pubKeywords = pubKeywords;
        this.pubAbstract = pubAbstract;
        this.pubYearPublish = pubYearPublish;
        this.publisher = publisher;
        this.journal = journal;
        this.mainAuthor = mainAuthor;
        this.coAuthor = coAuthor;
    }

    public void setDigitalName(String digitalName) {
        this.digitalName = digitalName;
    }

    public void setNameSeach(String nameSeach) {
        this.nameSeach = nameSeach;
    }
    
    public String getDigitalName() {
        return digitalName;
    }

    public String getNameSeach() {
        return nameSeach;
    }
    public List<Author> getCoAuthor() {
        return coAuthor;
    }

    public String getJournal() {
        return journal;
    }

    public Author getMainAuthor() {
        return mainAuthor;
    }

    public String getPubAbstract() {
        return pubAbstract;
    }

    public String getPubKeywords() {
        return pubKeywords;
    }

    public String getPubTitle() {
        return pubTitle;
    }

    public String getPubYearPublish() {
        return pubYearPublish;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setCoAuthor(List<Author> coAuthor) {
        this.coAuthor = coAuthor;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public void setMainAuthor(Author mainAuthor) {
        this.mainAuthor = mainAuthor;
    }

    public void setPubAbstract(String pubAbstract) {
        this.pubAbstract = pubAbstract;
    }

    public void setPubKeywords(String pubKeywords) {
        this.pubKeywords = pubKeywords;
    }

    public void setPubTitle(String pubTitle) {
        this.pubTitle = pubTitle;
    }

    public void setPubYearPublish(String pubYearPublish) {
        this.pubYearPublish = pubYearPublish;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
}
    