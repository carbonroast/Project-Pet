package ece.capstoneprojectpet.WeightLifting;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import ece.capstoneprojectpet.R;

public class BreakScreen extends Activity {

    TextView cdTimer;

    private static final String FORMAT = "%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_screen);

        cdTimer=(TextView)findViewById(R.id.countDown);

        // Timer at 3 seconds for debugging purposes
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

                cdTimer.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                cdTimer.setText("done!");
                String returnScreen = getIntent().getStringExtra("ReturnScreen");
                if (returnScreen.equals("Beginner")) {
                    Intent i = new Intent(getApplicationContext(), WeightLifting_Beginner.class);
                    startActivity(i);
                }
            }
        }.start();

    }
}
