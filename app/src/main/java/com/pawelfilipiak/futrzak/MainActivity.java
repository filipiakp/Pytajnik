package com.pawelfilipiak.futrzak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView odpowiedzTV;
    Random random = new Random();
    String[] odpowiedzi =  {"tak","nie","być może","nie wiem"};
    File baza;
    boolean przejscie= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        odpowiedzTV =(TextView) findViewById(R.id.textView);
        registerForContextMenu(findViewById(R.id.wybierzZestawBttn));
        baza = new File(this.getFilesDir().getAbsolutePath() + "/db.txt");

        zaladujBazePytan();

    }
    public void losuj(View view){
        if(przejscie) {
            zaladujBazePytan();
            odpowiedzTV.setText(odpowiedzi[random.nextInt(odpowiedzi.length)]);
            przejscie = false;
        }else
            odpowiedzTV.setText(odpowiedzi[random.nextInt(odpowiedzi.length)]);
    }
    public void edytuj(View view){
        przejscie = true;
        Intent intent = new Intent(this, DatabaseEditActivity.class);
        startActivity(intent);
    }


    BufferedReader br;
    String line;
    private void zaladujBazePytan(){
        try {
            br = new BufferedReader(new FileReader(baza));
            line=br.readLine();
            if(line != null)
                odpowiedzi = line.split(",");

//            while ((line = br.readLine()) != null) {
//                text.append(line);
//                text.append('\n');
//            }
            //dodawaj do rozwijanego menu https://developer.android.com/guide/topics/ui/menus.html#checkable//contextual menu
            //ogarnij odczytywanie z pliku
            //skontroluj czy wszystkie ooperacje na pliku są wykonywane
            //podziel na wątki
            br.close();
        }
        catch (FileNotFoundException e){
            try {
                System.out.println("tworzenie nowego pliku");
                baza.createNewFile();
                PrintWriter pw = new PrintWriter(baza);
                pw.println("tak,nie,nie wiem,być może");
                pw.close();
                zaladujBazePytan();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void wybierzZestaw(View view) {
//        PopupMenu popup = new PopupMenu(this, view);//nie to
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate( R.menu.actions, popup.getMenu());
//        popup.show();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        odpowiedzi = item.getTitle().toString().split(",");
        return true;

    }
}
