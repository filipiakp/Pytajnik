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

    EditText editText;
    String text;
    DataBaseUtil dataBaseUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_edit);
        editText =(EditText) findViewById(R.id.editText);

        dataBaseUtil = new DataBaseUtil(this.getFilesDir());
        text = dataBaseUtil.read();
        editText.setText(text);
    }
    public void ok(View view){
        dataBaseUtil.save(editText.getText().toString());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
