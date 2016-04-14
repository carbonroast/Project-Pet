package ece.capstoneprojectpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PersonalityTest extends Pet {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_test);
        String petStats = readFile();
        if(!petStats.equals("")) {

        }
    }


    public void submit(View v) {
        Intent i = new Intent(getApplicationContext(), Pet.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personality_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // write log to file
    private void writeFile(String log) {
        try {
            FileOutputStream file = openFileOutput("PETstats.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(file);
            outputWriter.write(log);
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // read log from file
    private String readFile() {
        try {
            char[] buffer= new char[100];
            String log = "";
            FileInputStream file = openFileInput("PETstats.txt");
            InputStreamReader InputRead = new InputStreamReader(file);
            int read;
            while ((read = InputRead.read(buffer)) > 0) {
                // char to string
                String readstring=String.copyValueOf(buffer,0,read);
                log = log + " " + readstring;
            }
            InputRead.close();
            return log;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
