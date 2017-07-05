package com.pawelfilipiak.futrzak;


import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class DataBaseUtil{

    private final String DATABASEFILE = "db.txt";
    private BufferedReader bufferedReader;
    private FileOutputStream fileOutputStream;
    private String line, text = "";
    private File dbFile;
    private AppCompatActivity context;

    public DataBaseUtil(AppCompatActivity context){
        dbFile = new File(context.getFilesDir(), DATABASEFILE);
        this.context = context;
    }

    public void save(String s){
        try {
            fileOutputStream = new FileOutputStream(dbFile);
            fileOutputStream.write(s.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        try {
            bufferedReader = new BufferedReader(new FileReader(dbFile));

            while ((line = bufferedReader.readLine()) != null)
                text = text + line + "\n";

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            try {
                dbFile.createNewFile();
                fileOutputStream = new FileOutputStream(dbFile);
                fileOutputStream.write(context.getResources().getString(R.string.default_answers).getBytes());
                fileOutputStream.close();
                return context.getResources().getString(R.string.default_answers);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
