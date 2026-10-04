package parking;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StreetFileReader {
    
    File file; 

    public ArrayList<Integer> readStreet(String filePath){
        this.file = new File(filePath);
        
        try{
            
            Scanner fileScanner = new Scanner(this.file);
            ArrayList<Integer> streetList = new ArrayList<>(); 
            
            while (fileScanner.hasNextLine()) {
                streetList.add(Integer.parseInt(fileScanner.nextLine()));
            }
            return streetList;
        }catch(IOException e){
            System.out.println("File reading didnt work!!!");
            return null;
        }
    }
}
