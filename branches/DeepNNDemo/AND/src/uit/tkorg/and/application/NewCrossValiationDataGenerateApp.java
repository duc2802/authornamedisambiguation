/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFileChooser;
import uit.tkorg.and.models.*;
import uit.tkorg.and.utils.ReadXML;

/**
 *
 * @author DucHuynh
 */
public class NewCrossValiationDataGenerateApp {
    public static void main(String[] args) {     
        JFileChooser fileChoose = new JFileChooser();
        fileChoose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChoose.showOpenDialog(null); 
        
        final ReadXML reader = new ReadXML();
        
        String pathFile = fileChoose.getSelectedFile().getAbsolutePath();  
        if(pathFile.equals("")) 
        {
            return;        
        }
        
        File root = new File(pathFile);
        File[] listChild = root.listFiles();
   
       int allOfFile = listChild.length;
        
       int indexListFile=0;
        // Đọc và ghi lại những file có tag = 1 vào 1 mảng.
      List<Integer> listFileIDTage0 = new ArrayList<>();
      List<Integer> listFileIDTage1 = new ArrayList<>();
       
        while(indexListFile < listChild.length)
        {
            File file = listChild[indexListFile];                    
            if(file.isFile())
            {                            
                PairPublication publication = reader.readPairPublicationFromXML(file.getAbsolutePath());  
                if(publication.getClassificationLabel() == 0)
                    listFileIDTage0.add(indexListFile);
                else
                    listFileIDTage1.add(indexListFile);
            }                    
            indexListFile++;                    
        } 

       // Tạo forder voi tag 0
      File fileRoot = new File("C:\\Data");
       
       if(new File("C:\\Data\\TestData\\20PercentWithTag0").exists())
        {
            new File("C:\\Data\\TestData\\20PercentWithTag0").deleteOnExit();
        }
        else {
            File file = new File("C:\\Data\\TestData\\" +"20PercentWithTag0" );
            if(file.mkdir()){
                    System.out.println("Directory is created! : " + file.getAbsolutePath());
            }else{
                    System.out.println("Failed to create directory!" + file.getAbsolutePath());
            }
        }
       
       if(new File("C:\\Data\\TestData\\20PercentWithTag1").exists())
        {
            new File("C:\\Data\\TestData\\20PercentWithTag1").deleteOnExit();
        }
        else {
            File file = new File("C:\\Data\\TestData\\" +"20PercentWithTag1" );
            if(file.mkdir()){
                    System.out.println("Directory is created! : " + file.getAbsolutePath());
            }else{
                    System.out.println("Failed to create directory!" + file.getAbsolutePath());
            }
        }
       
        int unitfortag0 = listFileIDTage0.size() / 10;
        int unitfortag1 = listFileIDTage1.size() / 10;
        int endIndexWithTag0=0;
        int endIndexWithTag1=0;
        int startIndexWithTag0 =0;
        int startIndexWithTag1 =0;
        
        for( startIndexWithTag0 =0;startIndexWithTag0 < unitfortag0*2; startIndexWithTag0++ ) {
            if (listFileIDTage0.size() > startIndexWithTag0)
            {
                copy(listChild[listFileIDTage0.get(startIndexWithTag0)].getAbsolutePath().toString(),fileRoot.getAbsolutePath().toString() + "\\TestData\\20PercentWithTag0\\" + (startIndexWithTag0 + 1) + ".xml");
                 endIndexWithTag0++;
            }
        }
        
         for( startIndexWithTag1 =0;startIndexWithTag1 < unitfortag1*2; startIndexWithTag1++ ) {
            if (listFileIDTage1.size() > startIndexWithTag1)
            {
                copy(listChild[listFileIDTage1.get(startIndexWithTag1)].getAbsolutePath().toString(),fileRoot.getAbsolutePath().toString() + "\\TestData\\20PercentWithTag1\\" + (startIndexWithTag1 + 1) + ".xml");
                 endIndexWithTag0++;
            }
        }
        
        int numberoffpair0 = listFileIDTage0.size() - endIndexWithTag0;
        int numberoffpair1= listFileIDTage1.size() - endIndexWithTag1 ;
        
        int unitforoneforderwithtag0 =numberoffpair0/5;
        int unitforoneforderwithtag1= numberoffpair1/5;
        
        // Chia so con lai vao 5 forder
        int loop=0;
        while(loop < 5)
        {
            System.out.println("===========================Loop " + (loop + 1) + "================================="); 
            
            // <editor-fold defaultstate="collapsed" desc="mkdir : Folder">            
            File file = new File("C:\\Data\\TrainData\\" + (loop + 1));
            if(file.mkdir()){
                    System.out.println("Directory is created! : " + file.getAbsolutePath());
            }else{
                    System.out.println("Failed to create directory!" + file.getAbsolutePath());
            }
            
            for (int indexfor5forderwithtag0 =0;indexfor5forderwithtag0<unitforoneforderwithtag0; indexfor5forderwithtag0++)
            {
                  copy(listChild[listFileIDTage0.get(endIndexWithTag0)].getAbsolutePath().toString(),file.getAbsolutePath().toString() +"\\"+"Tag0"+ (indexfor5forderwithtag0 + 1) + ".xml"); 
                  endIndexWithTag0++;
            }
            for (int indexfor5forderwithtag1 =0;indexfor5forderwithtag1<unitforoneforderwithtag1; indexfor5forderwithtag1++)
            {
                  copy(listChild[listFileIDTage1.get(endIndexWithTag1)].getAbsolutePath().toString(),file.getAbsolutePath().toString() +"\\"+"Tag1"+(indexfor5forderwithtag1 + 1) + ".xml"); 
                  endIndexWithTag1++;
            }
            //select test  
            loop++;       
        }
        System.out.println("Please go to C:\\Data check your data");
    }
    
    public static void copy(String source, String dest)
    {
        InputStream inStream = null;
	OutputStream outStream = null; 
    	try{ 
    	    File afile =new File(source);
    	    File bfile =new File(dest); 
    	    inStream = new FileInputStream(afile);
    	    outStream = new FileOutputStream(bfile); 
    	    byte[] buffer = new byte[1024]; 
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
    	    	outStream.write(buffer, 0, length);
    	    }
    	    inStream.close();
    	    outStream.close();
    	    System.out.println("Successful: " + source + " to " + dest);
    	}catch(IOException e){
            System.out.println("Cannot copy: " + source + " to " + dest + " : " + e.getMessage());
    	}
    }
}
