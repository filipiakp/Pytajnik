package com.pawelfilipiak.futrzak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView odpowiedzTV;
    Random random = new Random();
    String[] odpowiedzi =  {"tak","nie","być może","nie wiem"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        odpowiedzTV =(TextView) findViewById(R.id.textView);
    }
    public void losuj(View view){
        odpowiedzTV.setText(odpowiedzi[random.nextInt(odpowiedzi.length-1)]);
    }
}
