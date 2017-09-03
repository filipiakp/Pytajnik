package com.pawelfilipiak.futrzak;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;


public class DatabaseEditActivity extends AppCompatActivity{

    ListView categoryListView;
    DataBaseUtil dataBaseUtil;
    String typedString;
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_edit);
        categoryListView =(ListView) findViewById(R.id.categoryListView);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = categoryListView.getItemAtPosition(position).toString();

            }
        });

        dataBaseUtil = new DataBaseUtil(this);
        loadCategories();
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
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input != null && input.getText().toString().trim() != null){
                    typedString = input.getText().toString();
                    dataBaseUtil.addCategory(typedString," ");
                }
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        loadCategories();

    }
    public void editItem(View view){
        if(selected != null){
            Intent intent = new Intent(this, CategoryEditActivity.class);
            intent.putExtra("categoryName",selected);
            startActivity(intent);
            finish();
        }

    }
    public void deleteItem(View view){

        if(selected != null) {
            dataBaseUtil.removeCategory(selected);
            loadCategories();
        }
    }

    private void loadCategories() {
        categoryListView.setAdapter( new ArrayAdapter<String>(this, R.layout.row, Arrays.asList(dataBaseUtil.getCategories())));
    }



}
