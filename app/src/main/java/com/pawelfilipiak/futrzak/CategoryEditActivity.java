package com.pawelfilipiak.futrzak;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoryEditActivity extends AppCompatActivity {

    String categoryName;
    ListView answersListView;
    DataBaseUtil dataBaseUtil;
    String typedString;
    String selected;
    Button editButton;
    ArrayAdapter<String> adapter;
    ArrayList list = new ArrayList<String>();
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_edit);
        dataBaseUtil = new DataBaseUtil(this);
        editButton = (Button) findViewById(R.id.editItemButton);
        answersListView = (ListView) findViewById(R.id.categoryListView);
        answersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = answersListView.getItemAtPosition(position).toString();
            }
        });
        editButton.setVisibility(View.INVISIBLE);

        //get category name from extras
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Intent intent = new Intent(this, DatabaseEditActivity.class);
                startActivity(intent);
                finish();
                System.err.println("########1");
            } else {
                categoryName = extras.getString("categoryName");
                System.err.println("########2");
            }
        } else {
            categoryName = (String) savedInstanceState.getSerializable("categoryName");
            System.err.println("########3");

        }

        //get answers and fill list
        for(String s : dataBaseUtil.getCategoryAnswers(categoryName).split(MainActivity.REGEX))
            if(s!=" " &&  s!= "" &&  s!= null)
                list.add(s);
        adapter = new ArrayAdapter<String>(this, R.layout.row,list);
        answersListView.setAdapter(adapter);
    }

    public void save(View view){
        dataBaseUtil.updateCategory(categoryName, getAnswers());
        Intent intent = new Intent(this, DatabaseEditActivity.class);
        startActivity(intent);
        finish();
    }

    public void addItem(View view){
        AlertDialog.Builder ADbuilder = new AlertDialog.Builder(this);
        ADbuilder.setTitle(getResources().getString(R.string.add_answer));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        ADbuilder.setView(input);

        ADbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input != null && input.getText().toString().trim() != null) {
                    typedString = input.getText().toString();
                    dataBaseUtil.updateCategory(categoryName, getAnswers() + typedString);
                    list.add(typedString);
                    adapter.notifyDataSetChanged();

                }
            }
        });
        ADbuilder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog = ADbuilder.create();
        alertDialog.show();

    }

    public void editItem(View view){
        //do nothing here, its invisible
    }

    public void deleteItem(View view){
        if(selected != null) {
            list.remove(selected);
            adapter.notifyDataSetChanged();
            dataBaseUtil.updateCategory(categoryName, getAnswers());
            selected = null;
        }
    }

    private String getAnswers(){
        String answers = "";
        for(Object o : list)
            answers = answers + o.toString() + MainActivity.REGEX;

        return answers;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DatabaseEditActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( alertDialog!=null && alertDialog.isShowing() ){

            alertDialog.cancel();
        }

    }
    @Override
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putString("categoryName", categoryName);
    }
}
