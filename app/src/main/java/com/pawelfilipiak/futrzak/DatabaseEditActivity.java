package com.pawelfilipiak.futrzak;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;


public class DatabaseEditActivity extends AppCompatActivity {

    ListView categoryListView;
    ListAdapter adapter;
    DataBaseUtil dataBaseUtil;
    String typedString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_edit);
        categoryListView =(ListView) findViewById(R.id.categoryListView);

        dataBaseUtil = new DataBaseUtil(this);
        adapter = new ArrayAdapter<String>(this, R.layout.row, Arrays.asList(dataBaseUtil.getCategories()));
        categoryListView.setAdapter(adapter);
    }
    public void save(View view){
        if(categoryListView.getCount() ==0){
            dataBaseUtil.addCategory(getResources().getString(R.string.default_answers_name),getResources().getString(R.string.default_answers));
            Toast.makeText(this,getResources().getString(R.string.blank_database_dialog),Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void addItem(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.add_category));

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                typedString = input.getText().toString();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        dataBaseUtil.addCategory(typedString,"");
        categoryListView.setAdapter(new ArrayAdapter<String>(this, R.layout.row, Arrays.asList(dataBaseUtil.getCategories())));
    }
    public void editItem(View view){
        Intent intent = new Intent(this, CategoryEditActivity.class);
        intent.putExtra("categoryName",categoryListView.getSelectedItem().toString());
        intent.putExtra("answers",dataBaseUtil.getCategoryAnswers(categoryListView.getSelectedItem().toString()).split(MainActivity.REGEX));
        startActivity(intent);
        finish();
    }
    public void deleteItem(View view){
        categoryListView.removeView((View) categoryListView.getSelectedItem());
        dataBaseUtil.removeCategory(categoryListView.getSelectedItem().toString());
    }
}
