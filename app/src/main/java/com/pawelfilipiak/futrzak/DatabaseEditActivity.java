package com.pawelfilipiak.futrzak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DatabaseEditActivity extends AppCompatActivity {

    File baza;
    String text= "", line = "";
    BufferedReader br;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_edit);
        editText =(EditText) findViewById(R.id.editText);


        baza = new File(this.getFilesDir().getAbsolutePath() + "/db.txt");
        System.out.println("Plik z bazą: "+this.getFilesDir().getAbsolutePath() + "/db.txt");
        try {
            br = new BufferedReader(new FileReader(baza));//nie działa
            while ((line = br.readLine()) != null) {
                text.concat(line);
                text.concat("\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editText.setText(text);
    }
    public void ok(View view){
        try {
            PrintWriter pw = new PrintWriter(baza);
            pw.print(editText.getText());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();


    }
}
