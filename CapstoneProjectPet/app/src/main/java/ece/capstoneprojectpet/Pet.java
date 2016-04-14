package ece.capstoneprojectpet;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Pet extends Activity {
    //stores xcoord and ycoord of pointer
    int xcoord , ycoord;
    //GUI stuff
    LinearLayout layout;
    TextView xText;
    TextView yText;
    TextView idText;
    TextView hungerText;
    TextView happyText;
    TextView intelText;
    TextView petID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        xText = (TextView)findViewById(R.id.xcoord);
        yText = (TextView)findViewById(R.id.ycoord);
        idText = (TextView)findViewById(R.id.pointerID);
        layout=(LinearLayout)findViewById(R.id.layout);
        layout.addView(new CustomView(Pet.this));

        //stat texts
        Stats stat = new Stats();
        hungerText = (TextView)findViewById(R.id.hungerText);
        happyText = (TextView)findViewById(R.id.happyText);
        intelText = (TextView)findViewById(R.id.intelText);
        petID = (TextView)findViewById(R.id.petID);
        hungerText.setText("Hunger: " + Integer.toString(stat.getHunger()));
        happyText.setText("Happy: " + Integer.toString(stat.getHappy()));
        intelText.setText("Intelligence: " + Integer.toString(stat.getIntel()));


    }

    public void switchMap(View v) {
        Intent i = new Intent(getApplicationContext(), Map.class);
        startActivity(i);
    }
    public void switchDraw(View v) {
        Intent d = new Intent(getApplicationContext(), Draw.class);
        startActivity(d);
    }
    public void switchTest(View v) {
        Intent d = new Intent(getApplicationContext(), PersonalityTest.class);
        startActivity(d);
    }
    public void switchChart(View v) {
        Intent c = new Intent(getApplicationContext(), Chart.class);
        startActivity(c);
    }

    public class CustomView extends View implements View.OnTouchListener {
        private Paint paint;
        private SparseArray<PointF> pointers;
        private int[] colors = { Color.RED, Color.GREEN, Color.BLUE,
                Color.YELLOW, Color.CYAN, Color.GRAY, Color.MAGENTA,
                Color.BLACK, Color.LTGRAY, Color.DKGRAY};

        public CustomView(Context context) {
            super(context);
            paint=new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            setFocusable(true);
            this.setOnTouchListener(this);
            pointers = new SparseArray<PointF>();
        }

        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            // draw all circles
            for (int size = pointers.size(), i = 0; i < size; i++) {
                PointF point = pointers.valueAt(i);
                /*if (point != null)
                    paint.setColor(colors[i % 9]);*/
                paint.setColor(Color.RED);
                canvas.drawCircle(point.x, point.y, 50, paint);
            }
        }

        public boolean onTouch(View view, MotionEvent event) {
            // get pointer index and ID
            int pointerIndex = event.getActionIndex();
            int pointerId = event.getPointerId(pointerIndex);
            xcoord = (int)event.getX();
            ycoord = (int)event.getY();
            xText.setText("x-coord: " + xcoord);
            yText.setText("y-coord: " + ycoord);
            idText.setText("PointerID: " + pointerId);


            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN: {
                    PointF newPoint = new PointF();
                    newPoint.x = event.getX(pointerIndex);
                    newPoint.y = event.getY(pointerIndex);
                    pointers.put(pointerId, newPoint);
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                        PointF point = pointers.get(event.getPointerId(i));
                        if (point != null) {
                            point.x = event.getX(i);
                            point.y = event.getY(i);
                        }
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    xText.setText("x-coord: null");
                    yText.setText("y-coord: null");
                    idText.setText("PointerID: null");
                }
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL: {
                    pointers.remove(pointerId);
                    break;
                }
            }
            invalidate();
            return true;
        }
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
