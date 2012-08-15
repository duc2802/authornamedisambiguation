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
    
    String authorName;
    String authorAfflication;
    String authorEmail;
    int authorOrder;
    String authorInterests;
    int authorResult=0;
    String interest;

    public Author() {
    }
    
    public Author(String authorName, String authorAfflication, String authorEmail, int authorOrder, String authorInterests) {
        this.authorName = authorName;
        this.authorAfflication = authorAfflication;
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

    
    public String getAuthorAfflication() {
        return authorAfflication;
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

    public void setAuthorAfflication(String authorAfflication) {
        this.authorAfflication = authorAfflication;
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
