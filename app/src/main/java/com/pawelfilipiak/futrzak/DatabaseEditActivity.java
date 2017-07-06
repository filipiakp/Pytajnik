package com.pawelfilipiak.futrzak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class DatabaseEditActivity extends AppCompatActivity {

    EditText editText;
    String text;
    DataBaseUtil dataBaseUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_edit);
        editText =(EditText) findViewById(R.id.editText);

        dataBaseUtil = new DataBaseUtil(this);
        text = dataBaseUtil.read();
        editText.setText(text);
    }
    public void save(View view){
        text = editText.getText().toString();
        if(text.isEmpty() || text.trim().isEmpty()){
            Toast.makeText(this,getResources().getString(R.string.blank_database_dialog),Toast.LENGTH_LONG).show();
            text = getResources().getString(R.string.default_answers);
        }

        dataBaseUtil.save(text);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
