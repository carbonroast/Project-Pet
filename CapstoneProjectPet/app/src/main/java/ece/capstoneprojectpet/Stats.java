package ece.capstoneprojectpet;

import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import android.os.Handler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Jonathan on 3/29/2016.
 */
public class Stats extends Pet {
    int hunger;
    int happy;
    int intel;

    int energy;
    int health;
    int aggression;
    int bathroom;

    // default constructor
    public Stats() {
        // get int from text file
        hunger = 5;
        happy = 5;
        intel = 5;
        energy = 5;
        health = 5;
        aggression =5;
        bathroom = 5;
    }

    // set constructor stats
    public Stats(int a, int b, int c, int d, int e, int f, int g) {
        hunger = a;
        happy = b;
        intel = c;
        energy = d;
        health = e;
        aggression = f;
        bathroom = g;
    }

    public void changeHunger(int number) {
        hunger= hunger + number;
    }
    public void changeHappy(int number) {
        happy= happy + number;
    }
    public void changeIntel(int number){
        intel = intel + number;
    }
    public void changeEnergy(int number){
        energy = energy + number;
    }
    public void changeHealth(int number){
        health = health + number;
    }
    public void changeAggression(int number){
        aggression = aggression + number;
    }
    public void changeBathroom(int number){
        bathroom = bathroom + number;
    }

    public int getHunger() {
        return hunger;
    }
    public int getHappy() {
        return happy;
    }
    public int getIntel() {
        return intel;
    }
    public int getEnergy() {
        return energy;
    }
    public int getHealth() {
        return health;
    }
    public int getAggression() {
        return aggression;
    }
    public int getBathroom() {
        return bathroom;
    }

    // write log to file
    private void writeFile(String log) {
        try {
            FileOutputStream file = openFileOutput("PETstats.txt", MODE_APPEND);
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
                log = log + readstring;
            }
            InputRead.close();
            return log;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}

