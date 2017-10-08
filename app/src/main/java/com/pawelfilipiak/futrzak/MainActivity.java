package com.pawelfilipiak.futrzak;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**Main Activity
 * @description of app:
 * Furryball is simple app, that randomly answers your questions
 * eg. you have to choose between 6 colors of shoes
 * all of them are great, so it's hard to make decision
 * it doesn't matter which one you will buy
 * this app is created for a such situations
 * you can simply add category "colors" or "shoes"
 * click "edit"
 * then add these colors of shoes
 * save, save
 * and voil'a! shake your phone and get the right one!
 * @author Paweł Filipiak
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String REGEX = "#";

    private TextView answerTV;
    private Button randomButton;
    private Spinner spinner;
    private ImageView imageView;
    private Animation shakeAnim;
    private Random random = new Random();
    private String[] answers;
    private String[] dataBaseContent;
    private DataBaseUtil dataBaseUtil;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answers         = getResources().getString(R.string.default_answers).split(REGEX);
        imageView       = (ImageView)   findViewById(R.id.imageView);
        answerTV        = (TextView)    findViewById(R.id.answerTextView);
        randomButton    = (Button)      findViewById(R.id.randomButton);
        spinner         = (Spinner)     findViewById(R.id.spinner);
        dataBaseUtil    = new DataBaseUtil(this);//for operations on questions base file

        if(!dataBaseUtil.isCategoryExisting(getResources().getString(R.string.default_answers_name)))//it could've been created before
            dataBaseUtil.addCategory(getResources().getString(R.string.default_answers_name),getResources().getString(R.string.default_answers));
        dataBaseContent = dataBaseUtil.getCategories();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Arrays.asList(dataBaseContent));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                randomButton.performClick();
            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    public void randomQuestion(View view){
        //TODO: make that anim prettier and more chaotic
        playAnimation();
        answerTV.setText(answers[random.nextInt(answers.length)]);
    }
    public void edit(View view){
        Intent intent = new Intent(this, DatabaseEditActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        answers = dataBaseUtil.getCategoryAnswers(parent.getItemAtPosition(position).toString()).split(REGEX);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parent.setSelection(0);
        answers = dataBaseUtil.getCategoryAnswers(parent.getItemAtPosition(0).toString()).split(REGEX);
    }

    public void playAnimation(){
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        imageView.startAnimation(shakeAnim);

    }
}
