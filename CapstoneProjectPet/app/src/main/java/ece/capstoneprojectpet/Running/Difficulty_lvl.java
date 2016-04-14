package ece.capstoneprojectpet.Running;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import ece.capstoneprojectpet.R;
import ece.capstoneprojectpet.Running.Exercise_Running;

public class Difficulty_lvl extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_lvl);
    }
    public void switchFreeLance(View v){

            Intent i = new Intent(getApplicationContext(), Exercise_Running.class);
            startActivity(i);

    }
    public void switchBeginner(View v){
        //create beginner lvl activity
        //Intent i = new Intent(getApplicationContext(), Exercise_Running.class);
        //startActivity(i);
    }
}
