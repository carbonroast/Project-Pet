package ece.capstoneprojectpet.WeightLifting;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import ece.capstoneprojectpet.R;

public class WeightLifting_Beginner extends Activity {

    /* part of the code to swap views
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_lifting__beginner);



    }
    */

    //The Timer
    TextView timerTextView;
    long startTime = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_lifting__beginner);


        timerTextView = (TextView) findViewById(R.id.B_Timer);

        Button b = (Button) findViewById(R.id.StartButton);
        b.setText("Start");
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("Stop")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    //Intent i = new Intent(getApplicationContext(), BreakScreen.class);
                    //startActivity(i);
                    b.setText("Start");
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("Stop");
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button b = (Button)findViewById(R.id.StartButton);
        b.setText("Start");
    }



    /*
    // Works for swapping pages after timer
    public void startRun(View v){
        //create beginner lvl activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(WeightLifting_Beginner.this, BreakScreen.class);
                WeightLifting_Beginner.this.startActivity(mainIntent);
                WeightLifting_Beginner.this.finish();
            }
        }, 5000);
    }
    */






}
