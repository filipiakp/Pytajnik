package com.pawelfilipiak.futrzak;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class DataBaseUtil{

    private final String BAZA = "db.txt";
    private BufferedReader bufferedReader;
    private FileOutputStream fileOutputStream;
    private String line, text = "";
    private File bazaFile;

    public DataBaseUtil(File filesDir){
        bazaFile = new File(filesDir, BAZA);

    }

    public void save(String s){
        try {
            fileOutputStream = new FileOutputStream(bazaFile);
            fileOutputStream.write(s.getBytes());
            fileOutputStream.close();
            System.out.println("Zapisano: " + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        try {
            bufferedReader = new BufferedReader(new FileReader(bazaFile));

            while ((line = bufferedReader.readLine()) != null)
                text = text + line + "\n";

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            try {
                bazaFile.createNewFile();
                fileOutputStream = new FileOutputStream(bazaFile);
                fileOutputStream.write("tak,nie,nie wiem,być może".getBytes());
                fileOutputStream.close();
                return "tak,nie,nie wiem,być może";

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
