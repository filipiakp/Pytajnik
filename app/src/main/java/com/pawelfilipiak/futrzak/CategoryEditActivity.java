package com.pawelfilipiak.futrzak;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Arrays;

public class CategoryEditActivity extends AppCompatActivity {

    String categoryName;
    ListView answersListView;
    DataBaseUtil dataBaseUtil;
    String typedString;
    Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_edit);
        editButton = (Button) findViewById(R.id.editItemButton);
        editButton.setVisibility(View.INVISIBLE);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                categoryName = null;
            } else {
                categoryName = extras.getString("categoryName");
            }
        } else {
            categoryName = (String) savedInstanceState.getSerializable("categoryName");
        }
        System.err.println("############################# BIERZACA KATEGORIA " + categoryName);
        loadAnswers();
    }

    public void save(View view){
        dataBaseUtil.updateCategory(categoryName, getAnswers());
        Intent intent = new Intent(this, DatabaseEditActivity.class);
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
                if(input != null && input.getText().toString().trim() != null) {
                    typedString = input.getText().toString();
                    dataBaseUtil.updateCategory(categoryName, getAnswers() + typedString);
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
        loadAnswers();
    }

    public void editItem(View view){

    }

    public void deleteItem(View view){
        answersListView.removeView((View) answersListView.getSelectedItem());
        dataBaseUtil.updateCategory(categoryName, getAnswers());
    }

    private void loadAnswers(){
        answersListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.row,
                Arrays.asList(dataBaseUtil.getCategoryAnswers(categoryName).split(MainActivity.REGEX))));
    }
    private String getAnswers(){
        String answers = "";
        for(int i = 0; i<answersListView.getCount()-1;i++){
            answers += answersListView.getItemAtPosition(i).toString() + MainActivity.REGEX;
        }
        return answers;
    }
}
