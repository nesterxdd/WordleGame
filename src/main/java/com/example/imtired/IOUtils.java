package com.example.imtired;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class IOUtils {
    public static void ReadWords(HashMap<String, String> hashMap, String pathToFile) throws IOException {
        try{
            FileReader reader = new FileReader(pathToFile);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while((line = bufferedReader.readLine()) != null){
                hashMap.put(line, line);
            }
            System.out.println("Data was successfully read");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
