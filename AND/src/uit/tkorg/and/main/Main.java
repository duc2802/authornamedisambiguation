/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.main;

import uit.tkorg.and.core.similarity.JaccardSimilarity;

/**
 *
 * @author Admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String a ="hoang kiem";
        String b ="kiem H";
        JaccardSimilarity test = new JaccardSimilarity();
        float result= test.getSimilarity(a, b);
        System.out.println("JaccardSimilarity "+result);
    }
}
