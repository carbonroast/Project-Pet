package ece.capstoneprojectpet;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import ece.capstoneprojectpet.Running.Difficulty_lvl;
import ece.capstoneprojectpet.WeightLifting.weight_difficulty;


public class Workout extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
    }
    public void switchRunning(View v) {
        Intent i = new Intent(getApplicationContext(), Difficulty_lvl.class);
        startActivity(i);
    }

    public void switchWeights(View v) {
        Intent i = new Intent(getApplicationContext(), weight_difficulty.class);
        startActivity(i);
    }
    /* Others to be implemented, also needs to carry arguement with it so can tell what exercise
    public void switchSports(View v) {
        Intent i = new Intent(getApplicationContext(), Difficulty_lvl.class);
        startActivity(i);
    }*/

}
