/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.models;

/**
 *
 * @author tiendv
 * Doi tuong dung de config cac feature duoc chon de chay thuat toan
 * 
 * 
 */
public class Feature {
    
    
    // List of Feature 
    int numberSelectFeature=0;
    Boolean jcAuthorName =false;
    Boolean jcAffiliation =false;
    Boolean jcInterestingKeyword =false;
    Boolean jcCoAuthor = false;
    Boolean jcKeyword = false;
    Boolean levenshteinAuthorname=false;
    Boolean levenshteinAffiliaiton =false;

    public Feature() {
    }

    public Boolean getJcAffiliation() {
        return jcAffiliation;
    }

    public Boolean getJcAuthorName() {
        return jcAuthorName;
    }

    public Boolean getJcCoAuthor() {
        return jcCoAuthor;
    }

    public Boolean getJcInterestingKeyword() {
        return jcInterestingKeyword;
    }

    public Boolean getJcKeyword() {
        return jcKeyword;
    }

    public void setJcAffiliation(Boolean jcAffiliation) {
        this.jcAffiliation = jcAffiliation;
    }

    public void setJcAuthorName(Boolean jcAuthorName) {
        this.jcAuthorName = jcAuthorName;
    }

    public void setJcCoAuthor(Boolean jcCoAuthor) {
        this.jcCoAuthor = jcCoAuthor;
    }

    public void setJcInterestingKeyword(Boolean jcInterestingKeyword) {
        this.jcInterestingKeyword = jcInterestingKeyword;
    }

    public void setJcKeyword(Boolean jcKeyword) {
        this.jcKeyword = jcKeyword;
    }

    public int getNumberSelectFeature() {
        return numberSelectFeature;
    }

    public void setNumberSelectFeature(int numberSelectFeature) {
        this.numberSelectFeature = numberSelectFeature;
    }

    public Boolean getLevenshteinAffiliaiton() {
        return levenshteinAffiliaiton;
    }

    public Boolean getLevenshteinAuthorname() {
        return levenshteinAuthorname;
    }

    public void setLevenshteinAffiliaiton(Boolean levenshteinAffiliaiton) {
        this.levenshteinAffiliaiton = levenshteinAffiliaiton;
    }

    public void setLevenshteinAuthorname(Boolean levenshteinAuthorname) {
        this.levenshteinAuthorname = levenshteinAuthorname;
    }
    
}
