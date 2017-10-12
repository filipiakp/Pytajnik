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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class DatabaseEditActivity extends AppCompatActivity{

    ListView categoryListView;
    DataBaseUtil dataBaseUtil;
    String typedString;
    String selected;
    ArrayAdapter<String> adapter;
    ArrayList list = new ArrayList<String>();

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
        for(String s : dataBaseUtil.getCategories())
            list.add(s);

        adapter = new ArrayAdapter<String>(this, R.layout.row,list);
        categoryListView.setAdapter(adapter);
    }
    public void save(View view){
        if(categoryListView.getCount() ==0){
            dataBaseUtil.addCategory(getResources().getString(R.string.default_answers_name),getResources().getString(R.string.default_answers));
            showToast(getResources().getString(R.string.blank_database_dialog));
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void showToast(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_LONG).show();
    }

    public void addItem(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.add_category));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input != null && input.getText().toString().trim() != null){
                    typedString = input.getText().toString();
                    if(dataBaseUtil.isCategoryExisting(typedString)) {
                        showToast(getResources().getString(R.string.category_exists_dialog));
                    }else{
                        dataBaseUtil.addCategory(typedString, "");
                        list.add(typedString);
                        adapter.notifyDataSetChanged();
                    }
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
            list.remove(selected);
            adapter.notifyDataSetChanged();
            dataBaseUtil.removeCategory(selected);
            selected = null;
        }
    }




}
