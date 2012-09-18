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
    Boolean jaroAuthorName=false;
    Boolean jaroAffiliation =false;
    Boolean jarowinklerAuthorName=false;
    Boolean jarowinklerAffiliaiton=false;
    Boolean smithWatermanAffiliation=false;
    Boolean smithWatermanAuthorName=false;
    Boolean mongeElkanAuthorName=false;
    Boolean mongeElkanAffiliation=false;
    

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

    public Boolean getJaroAuthorName() {
        return jaroAuthorName;
    }

    public void setJaroAuthorName(Boolean jaroAuthorName) {
        this.jaroAuthorName = jaroAuthorName;
    }

    public Boolean getJaroAffiliation() {
        return jaroAffiliation;
    }

    public void setJaroAffiliation(Boolean jaroAffiliation) {
        this.jaroAffiliation = jaroAffiliation;
    }

    public Boolean getJarowinklerAuthorName() {
        return jarowinklerAuthorName;
    }

    public void setJarowinklerAuthorName(Boolean jarowinklerAuthorName) {
        this.jarowinklerAuthorName = jarowinklerAuthorName;
    }

    public Boolean getJarowinklerAffiliaiton() {
        return jarowinklerAffiliaiton;
    }

    public void setJarowinklerAffiliaiton(Boolean jarowinklerAffiliaiton) {
        this.jarowinklerAffiliaiton = jarowinklerAffiliaiton;
    }

    public Boolean getSmithWatermanAffiliation() {
        return smithWatermanAffiliation;
    }

    public Boolean getSmithWatermanAuthorName() {
        return smithWatermanAuthorName;
    }

    public void setSmithWatermanAffiliation(Boolean smithWatermanAffiliation) {
        this.smithWatermanAffiliation = smithWatermanAffiliation;
    }

    public void setSmithWatermanAuthorName(Boolean smithWatermanAuthorName) {
        this.smithWatermanAuthorName = smithWatermanAuthorName;
    }

    public Boolean getMongeElkanAffiliation() {
        return mongeElkanAffiliation;
    }

    public Boolean getMongeElkanAuthorName() {
        return mongeElkanAuthorName;
    }

    public void setMongeElkanAffiliation(Boolean mongeElkanAffiliation) {
        this.mongeElkanAffiliation = mongeElkanAffiliation;
    }

    public void setMongeElkanAuthorName(Boolean mongeElkanAuthorName) {
        this.mongeElkanAuthorName = mongeElkanAuthorName;
    }
    
    public static final String JC_AUTHOR_NAME = "jcAuthorName";
    public static final String JC_AFFILIATION = "jcAffiliation";
    public static final String JC_INTERESTING_KEYWORD = "jcInterestingKeyword";
    public static final String JC_CO_AUTHOR = "jcCoAuthor";
    public static final String JC_KEYWORD = "jcKeyword";
    public static final String LEVENSHTEIN_AUTHOR_NAME = "levenshteinAuthorname";
    public static final String LEVENSHTEIN_AFFILIATION = "levenshteinAffiliaiton";   
    public static final String JARO_AUTHOR_NAME = "jaroAuthorname";
    public static final String JARO_AFFILIATION ="jaroAffiliation";
    public static final String JAROWINKLER_AUTHOR_NAME="jarowinklerAuhorName"; 
    public static final String JAROWIKLER_AFFILIATION ="jarowinklerAffiliation";
    public static final String SMITHWATERMAN_AFFILIATION ="smithWatermanAffiliation";
    public static final String SMITHWATERMAN_AUTHOR_NAME="smithWatermanAuthorName";
    public static final String MONGEELKAN_AUTHOR_NAME = "mongeElkanAuthorName";
    public static final String MONGEELKAN_AFFILIATION = "mongeElkanAffiliation";
}
