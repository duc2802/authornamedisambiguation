/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.application;

import java.io.File;
import uit.tkorg.and.models.Publication;
import uit.tkorg.and.utils.ReadXML;

/**
 *
 * @author DucHuynh
 */
public class StatisticDataSet {
    
     public static void main(String[] args) {
        String pathFile = "c:\\DataLoad\\";
        StatisticDataSet statistic = new StatisticDataSet();
        statistic.countData(pathFile);
    }
     
     public void countData(String rootDirectory)
     {
        final ReadXML reader = new ReadXML();        
        File root = new File(rootDirectory);
        File[] listChild = root.listFiles();
        for (int i = 0; i < listChild.length; i++) {
            File child = listChild[i];            
            if(child.isDirectory())
            { 
                File[] listFiles = child.listFiles();
                int lengthListFile = 0;
                Publication[] data = new Publication[listFiles.length];
                while(lengthListFile < listFiles.length)
                {
                    File file = listFiles[lengthListFile];                    
                    if(file.isFile())
                    {                            
                        Publication publication = reader.importPubFromXML(file.getAbsolutePath());
                        data[lengthListFile] = publication;                            
                    }                    
                    lengthListFile++;                    
                } 
                int count = 0;
                int countSame = 0;
                int countDiff = 0;
                for (int index = 0; index < listFiles.length - 1; index++) {
                    for (int index2 = index + 1; index2 < listFiles.length; index2++) {
                         count++;
                         int sum = data[index].getMainAuthor().getAuthorResult() + data[index2].getMainAuthor().getAuthorResult();        
                        if(sum == 2) 
                            countSame++;
                        else 
                            countDiff++;
                    }
                }
                
                System.out.println(child.getAbsolutePath() + ": \t Same: \t " + countSame + " \t Diff: \t " + countDiff + " \t Sum: \t " + count);                
            }
        }
     }
}
