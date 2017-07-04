package com.pawelfilipiak.futrzak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView odpowiedzTV;
    private Spinner spinner;
    private Random random = new Random();
    private String[] odpowiedzi =  {"tak","nie","być może","nie wiem"};

    String[] dataBaseContent;
    List<String> list;
    DataBaseUtil dataBaseUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        odpowiedzTV =(TextView) findViewById(R.id.textView);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        dataBaseUtil = new DataBaseUtil(this.getFilesDir());
        list = new ArrayList<>();
        dataBaseContent = dataBaseUtil.read().split("\n");

        for(String s : dataBaseContent)
            list.add(s);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(dataAdapter);

    }
    public void losuj(View view){
            odpowiedzTV.setText(odpowiedzi[random.nextInt(odpowiedzi.length)]);
    }
    public void edytuj(View view){
        Intent intent = new Intent(this, DatabaseEditActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        odpowiedzi = parent.getItemAtPosition(position).toString().split(",");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parent.setSelection(0);
        odpowiedzi = parent.getItemAtPosition(0).toString().split(",");
    }
}
