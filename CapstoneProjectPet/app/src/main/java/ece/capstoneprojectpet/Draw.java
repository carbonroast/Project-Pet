package ece.capstoneprojectpet;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Draw extends Pet implements View.OnTouchListener {
    // GUI stuff
    TextView xText;
    TextView yText;
    TextView idText;
    //stores xcoord and ycoord of pointer
    int xcoord , ycoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        xText = (TextView)findViewById(R.id.xcoord);
        yText = (TextView)findViewById(R.id.ycoord);
        idText = (TextView)findViewById(R.id.pointerID);
        View onTouchView = findViewById(R.id.gesture);
        onTouchView.setOnTouchListener(this);
    }

    public void switchPet(View v) {
        Intent i = new Intent(getApplicationContext(), Pet.class);
        startActivity(i);
    }

    public boolean onTouch(View view, MotionEvent event) {
        // get pointer index and ID
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            xcoord = (int)event.getX();
            ycoord = (int)event.getY();
            xText.setText("x-coord: " + xcoord);
            yText.setText("y-coord: " + ycoord);
            idText.setText("pointerID: " + pointerId);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_draw, menu);
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
}
