package parking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class StreetFileGenerator {
    

    public static void generateFile(){

        Random r = new Random();
        int random;
        try {
            FileWriter writer = new FileWriter("Street.txt");

            for (int i = 0; i <= 500; i++) {
                random = r.nextInt(2);
                writer.write(random * 200 + "\n");
            }
            writer.close();;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        generateFile();

    }
}
