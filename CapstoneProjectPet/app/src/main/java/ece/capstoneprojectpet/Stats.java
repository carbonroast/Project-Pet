package ece.capstoneprojectpet;

import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import android.os.Handler;

/**
 * Created by Jonathan on 3/29/2016.
 */
public class Stats {

    double hunger;
    double happy;
    double intel;

    double energy;
    double health;
    double aggression;
    double bathroom;

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
    public Stats(double a, double b, double c, double d, double e, double f, double g) {
        hunger = a;
        happy = b;
        intel = c;
        energy = d;
        health = e;
        aggression = f;
        bathroom = g;
    }

    public void changeHunger(double number) {
        hunger= hunger + number;
    }
    public void changeHappy(double number) {
        happy= happy + number;
    }
    public void changeIntel(double number){
        intel = intel + number;
    }
    public void changeEnergy(double number){
        energy = energy + number;
    }
    public void changeHealth(double number){
        health = health + number;
    }
    public void changeAggression(double number){
        aggression = aggression + number;
    }
    public void changeBathroom(double number){
        bathroom = bathroom + number;
    }

    public double getHunger() {
        return hunger;
    }
    public double getHappy() {
        return happy;
    }
    public double getIntel() {
        return intel;
    }
    public double getEnergy() {
        return energy;
    }
    public double getHealth() {
        return health;
    }
    public double getAggression() {
        return aggression;
    }
    public double getBathroom() {
        return bathroom;
    }

}
