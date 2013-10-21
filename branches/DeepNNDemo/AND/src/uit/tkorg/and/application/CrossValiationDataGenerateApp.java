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
import javax.swing.JFileChooser;

/**
 *
 * @author DucHuynh
 */
public class CrossValiationDataGenerateApp {
    public static void main(String[] args) {     
        JFileChooser fileChoose = new JFileChooser();
        fileChoose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChoose.showOpenDialog(null); 
        
        String pathFile = fileChoose.getSelectedFile().getAbsolutePath();  
        if(pathFile.equals("")) 
        {
            return;        
        }
        
        File root = new File(pathFile);
        File[] listChild = root.listFiles();
        
        int allOfFile = listChild.length;
                
        int unit = allOfFile / 10;

        int start = 0;
        int end = 0;

        int loop = 0;
        
        // <editor-fold defaultstate="collapsed" desc="mkdir : Folder">
        
        if(new File("C:\\CrossValidate").exists())
        {
            new File("C:\\CrossValidate").deleteOnExit();
        }
        
        if(new File("C:\\CrossValidate").mkdir()){
                System.out.println("Directory is created! : C:\\CrossValidate");
        }else{
                System.out.println("Failed to create directory! C:\\CrossValidate");
        }        
        
        // </editor-fold>

        while(loop < 10)
        {
            System.out.println("===========================Loop " + (loop + 1) + "================================="); 
            
            // <editor-fold defaultstate="collapsed" desc="mkdir : Folder">            
            File file = new File("C:\\CrossValidate\\" + (loop + 1));
            if(file.mkdir()){
                    System.out.println("Directory is created! : " + file.getAbsolutePath());
            }else{
                    System.out.println("Failed to create directory!" + file.getAbsolutePath());
            }
            
            if(new File(file.getAbsoluteFile() + "\\test").mkdir()){
                System.out.println("Directory is created! : C:\\CrossValidate\\..\\test");
            }else{
                System.out.println("Failed to create directory! C:\\CrossValidate\\..\\test");
            }

            if(new File(file.getAbsoluteFile() + "\\train").mkdir()){
                System.out.println("Directory is created! : C:\\CrossValidate\\..\\train");
            }else{
                System.out.println("Failed to create directory! C:\\CrossValidate\\..\\train");
            }        
            //</editor-fold>
            
            start = loop * unit;
            end = (int)((float)unit * 1) + start;
            
            //select test
            for(int i = 0; i < allOfFile; i++)
            {
                if(i >= end || i < start)
                {
                    copy(listChild[i].getAbsolutePath().toString(), file.getAbsolutePath().toString() + "\\train\\" + (i + 1) + ".xml");
                }
                else
                {
                    copy(listChild[i].getAbsolutePath().toString(), file.getAbsolutePath().toString() + "\\test\\" + (i + 1) + ".xml");
                }
            }    
            loop++;
            
            System.out.println("Please go to C:\\CrossValidate check it again.");
        }
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
