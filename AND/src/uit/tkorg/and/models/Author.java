/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.models;

/**
 *
 * @author tiendv
 */
public class Author {
    
    String authorName="";
    String authorAffliation="";
    String authorEmail="";
    int authorOrder=0;
    String authorInterests="";
    int authorResult = 0;
    String interest="";

    public Author() {
    }
    
    public Author(String authorName, String authorAfflication, String authorEmail, int authorOrder, String authorInterests) {
        this.authorName = authorName;
        this.authorAffliation = authorAfflication;
        this.authorEmail = authorEmail;
        this.authorOrder = authorOrder;
        this.authorInterests = authorInterests;        
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getInterest() {
        return interest;
    }

    
    public String getAuthorAffliation() {
        return authorAffliation;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public String getAuthorInterests() {
        return authorInterests;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getAuthorOrder() {
        return authorOrder;
    }

    public int getAuthorResult() {
        return authorResult;
    }

    public void setAuthorAffliation(String authorAffliation) {
        this.authorAffliation = authorAffliation;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public void setAuthorInterests(String authorInterests) {
        this.authorInterests = authorInterests;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setAuthorOrder(int authorOrder) {
        this.authorOrder = authorOrder;
    }

    public void setAuthorResult(int authorResult) {
        this.authorResult = authorResult;
    }
    
}
