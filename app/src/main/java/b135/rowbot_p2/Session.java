package b135.rowbot_p2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;


public class Session extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "session";

    //chronometer variable
    private Chronometer sessionTimer;
    //used to calculate the offset from when the timer is paused to when it is continued since by default it keeps running in the background
    private long pauseOffset;
    //boolean used to see whether or not the timer is running
    private boolean running;
    //boolean used to see whether or not the stop button is available and if true then the session is able to stop
    private boolean sessionStop = false;
    //String targetDistance;
    String targetTime;
    Calendar c = Calendar.getInstance();
    int day = c.get(Calendar.DAY_OF_WEEK);
    int dayMonth = c.get(Calendar.DAY_OF_YEAR);
    int dayMonthTargetTime = c.get(Calendar.DAY_OF_YEAR);
    String elapsedTimeText = "Elapsed time:\n";
    String elapsedTimeCounter = "%s\n";
    String elapsedTime;
    private DrawerLayout mDrawerLayout;
    private TextView debugT;
    private TextView spmNumbers;

    private boolean runningForDrawer = true;

    // graph variables
    GraphView graph;
    LineGraphSeries<DataPoint> values;
    private int mass = 20;
    private long gOffsetInit;
    private long gOffset;
    private double graphMargin = 1.2;
    private long xVal_t_begin;
    private long xVal_t_end;
    private long xVal_t_total;
    private long xValue;
    private int xCounter;
    private int xMax = 20;
    private DataPoint[] yValues;
    private SensorManager SM;
    private Sensor mySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        // force landscape mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // extra fetched from SessionInput
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            //targetDistance = extra.getString("EXTRA_DISTANCE");
            targetTime = extra.getString("EXTRA_TIME");
        }

        // Create our Sensor Manager
        SM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        // Register sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_GAME);
        mDrawerLayout = findViewById(R.id.drawerLayoutSession);

        NavigationView navigationView = findViewById(R.id.navViewSession);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        int id = item.getItemId();

                        if (id == R.id.navMainFromSession){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("SAVED_RUNNING",runningForDrawer);
                            startActivity(intent);
                        }
                        else if (id == R.id.navStatisticsFromSession){
                            Intent intent = new Intent(getApplicationContext(), Statistics.class);
                            intent.putExtra("SAVED_RUNNING",runningForDrawer);
                            startActivity(intent);
                        }
                        else if (id == R.id.navGuideFromSession){
                            Intent intent = new Intent(getApplicationContext(), Guide.class);
                            intent.putExtra("SAVED_RUNNING",runningForDrawer);
                            startActivity(intent);
                        }

                        return true;
                    }
                });

        //gOffsetInit = SystemClock.elapsedRealtime() - pauseOffset;
        //gOffset = SystemClock.elapsedRealtime() - (pauseOffset + gOffsetInit + xVal_t_end);
        graph = findViewById(R.id.graph);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(150);
        graph.getViewport().setMaxX(xMax);
        yValues = new DataPoint[20];
        for (int i = 0; i<yValues.length;i++){
            yValues[i] = new DataPoint(0,0);
        }
        xCounter = 0;
        //String distance = currentDistance+targetDistance;

        //here we create and instance of our viewPager
        //ViewPager viewPager = findViewById(R.id.sessionViewPager);
        //here we create our imageAdapter by calling the constructor method
        //ImageAdapter adapter = new ImageAdapter(this);
        //here we take our viewPager variable and call the setAdapter method on it and then pass our adapter
        //viewPager.setAdapter(adapter);
        //TextView debugD = findViewById(R.id.debugOutput);
        debugT = findViewById(R.id.debugOutput2);
        //debugD.setText(distance);
        debugT.setText(targetTime);

        //sets the chronometer variable to the chronometer in the xml file by id
        sessionTimer = findViewById(R.id.timerCounter);
        //sets the input format to a string of text where the %s means the format is mm:ss after 59:59 the format change automatically to hh:mm:ss
        sessionTimer.setFormat("%s\n");
        //this updates the string when we start the activity
        sessionTimer.setBase(SystemClock.elapsedRealtime());

    }

    //when you click the button start it's onClick listener starts this method to run the timer
    public void startTimerCounter(View v) {
        //initializing buttons by id
        Button sButton = findViewById(R.id.startSession);
        Button pButton = findViewById(R.id.pauseSession);
        //if the boolean is false i.e. not running the timer will start
        if (!running) {
            //sets various colors and texts
            sButton.setBackgroundResource(R.drawable.session_start_button);
            sButton.setTextColor(getResources().getColor(R.color.black));
            pButton.setBackgroundResource(R.drawable.session_standard_button);
            pButton.setTextColor(getResources().getColor(R.color.black));
            pButton.setText(R.string.PauseSession);
            //this makes the chronometer start counting when we click the button and not from when we start the activity which is the default
            //we subtract the offset to make sure that if we pause it, when we the run it again it will
            //continue from where we left and not keep counting in the background
            //definition of elapsedRealtime() - Returns milliseconds since boot, including time spent in real time.
            sessionTimer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            //starts the chronometer i.e. the timer and makes the timer text continue
            sessionTimer.start();
            //sets sessionStop to false to make sure that when pause timer is called it won't stop but just pause
            sessionStop = false;
            //sets running to true so that when you press pause the if statement is active
            running = true;
            // toast the user that session has begun
            Utility.doToast(getApplicationContext(),"Session started!");

            // START THE GRAPH
            // (call a method to start graph
            //xBegin();
            //xValue = SystemClock.elapsedRealtime()/1000;

        }
    }

    //when you click the button pause it's onClick listener starts this method to pause the timer
    public void pauseTimerCounter(View v) {
        //initializing buttons by id
        Button sButton = findViewById(R.id.startSession);
        Button pButton = findViewById(R.id.pauseSession);
        Button saveButton = findViewById(R.id.saveSession);
        Button rButton = findViewById(R.id.returnSession);
        //if the boolean is true i.e. running the timer will pause
        if (running) {
            //sets various colors and texts
            sButton.setBackgroundResource(R.drawable.session_standard_button);
            sButton.setTextColor(getResources().getColor(R.color.black));
            pButton.setBackgroundResource(R.drawable.session_stop_button);
            pButton.setTextColor(getResources().getColor(R.color.black));
            pButton.setText(R.string.StopSession);
            //pauses the chronometer i.e. the timer and stops the timer text without resetting it.
            sessionTimer.stop();
            //this resets the elapsedRealtime to zero since it take the time that has elapsed so far and subtract that by getting the base
            //we set in the startTimerCounter method.
            //this makes it so that once we start the timer it start from 0 again through the setBase
            //this is the counter that resets to zero NOT the text the timer text stays the same.
            pauseOffset = SystemClock.elapsedRealtime() - sessionTimer.getBase();
            //sessionStop set to true to make sure that when stop is clicked it will be able to stop the session
            sessionStop = true;
            //sets running to false so that when you press start the if statement is active
            running = false;
        }
        else if (sessionStop) {
            //this makes sure the viewPager stays below the buttons and doesn't go to match parent when the buttons change
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
            graph.getLayoutParams();
            layoutParams.addRule(RelativeLayout.BELOW, saveButton.getId());
            graph.setLayoutParams(layoutParams);
            sButton.setVisibility(View.GONE);
            pButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
            rButton.setVisibility(View.VISIBLE);
        }
    }

    public void saveSession(View v) {
        // checks whether we already have a preset selected, if yes, go straight past
        // sessionInput, and into session.
        if (runningForDrawer){
            runningForDrawer = false;
        }
        // switch statement checks which day it is, through the calendar,
        // and uses checkForContent to either create a file, or add to an existing one.
        // these saved values are for the weekly statistics
        switch (day) {
            case Calendar.MONDAY:
                checkForContent("tsMonday.txt");
                Log.d(TAG, "Monday was selected");
                break;
            case Calendar.TUESDAY:
                checkForContent("tsTuesday.txt");
                Log.d(TAG, "Tuesday was selected");
                break;
            case Calendar.WEDNESDAY:
                checkForContent("tsWednesday.txt");
                Log.d(TAG, "Wednesday was selected");
                break;
            case Calendar.THURSDAY:
                checkForContent("tsThursday.txt");
                Log.d(TAG, "Thursday was selected");
                break;
            case Calendar.FRIDAY:
                checkForContent("tsFriday.txt");
                Log.d(TAG, "Friday was selected");
                break;
            case Calendar.SATURDAY:
                checkForContent("tsSaturday.txt");
                Log.d(TAG, "Saturday was selected");
                break;
            case Calendar.SUNDAY:
                checkForContent("tsSunday.txt");
                Log.d(TAG, "Sunday was selected");
                break;
            default:
                break;
        }
        // SPM is disabled in this version due to it being unfinished
        spmNumbers = findViewById(R.id.spmNumbers);


        // these values are for the monthly statistics
        // containing target time, session time, and the placeholder SPM
        Utility.writeToFile(sessionTimer.getText().toString(), dayMonth+".txt", getApplicationContext());
        Utility.writeToFile(debugT.getText().toString(), dayMonth+"targetTime.txt", getApplicationContext());
        Utility.writeToFile(spmNumbers.getText().toString(), dayMonth+"SPM.txt", getApplicationContext());

        // return to main screen upon saving
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        // here we notify(putExtra) that runningForDrawer is false, so that you will be
        // referred to the sessionInput in case you start another session from drawer
        intent.putExtra("SAVED_RUNNING",runningForDrawer);
        Utility.doToast(getApplicationContext(),"Session was saved");
        startActivity(intent);
    }

    // this method checks whether a file already contains data, and if it does, combines that data
    // with new data, otherwise it writes the file from scratch
    private void checkForContent(String weekDay) {
        if(!weekDay.equals("0")) {
            // conv to int, add new sessiondata as int, convert to string
            int old = Integer.parseInt(Utility.divideString(Utility.readFromFile(weekDay,getApplicationContext())));
            int newest = Integer.parseInt(Utility.divideString(sessionTimer.getText().toString()));
            int combined = old+newest;
            Utility.writeToFile(Integer.toString(combined),weekDay,getApplicationContext());
        }
        else {
            // normal procedure
            Utility.writeToFile(sessionTimer.getText().toString(), weekDay, getApplicationContext());
        }
    }



    public void returnToSession(View v) {
        //initializing buttons by id
        Button sButton = findViewById(R.id.startSession);
        Button pButton = findViewById(R.id.pauseSession);
        Button saveButton = findViewById(R.id.saveSession);
        Button rButton = findViewById(R.id.returnSession);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) graph.getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, pButton.getId());
        graph.setLayoutParams(layoutParams);

        sButton.setVisibility(View.VISIBLE);
        pButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
        rButton.setVisibility(View.GONE);
        // COMMENTS PL0X
    }

    public void openDrawerSession(View view) {
        mDrawerLayout.openDrawer(Gravity.START);
    }







    //call this in onClick for start session
    public void xBegin() {
        //negativeToPositive();


        if (2 > 1) {
            xVal_t_begin = SystemClock.elapsedRealtime() - (gOffsetInit + pauseOffset);
            //in here we need to start drawing out graph and add to it
        }


    }

    public void xEnd() {
        negativeToPositive();
        if (2 < 1) {
            xVal_t_end = 0L;
            //stop the graph from drawing
        }
    }

    public void timeFromStartToFinish() {
        xVal_t_total = xVal_t_end - xVal_t_begin;
    }

    //this is copied from the sensor test class just for now
    public void negativeToPositive() {
        /*if (event.values[0] < 0){
            valueX = valueX * (-1);
        }
        if(event.values[1] < 0){
            valueY = valueY * (-1);
        }*/
    }

    public void onSensorChanged(SensorEvent event) {
        //xText.setText("X: " + event.values[0]);
        //yText.setText("Y: " + event.values[1]);
        //zText.setText("Z: " + event.values[2]);
        float valueX = event.values[0];
        float valueY = event.values[1];
            /*
        if (event.values[0] < 0){
            valueX = valueX * (-1);
        }
        if(event.values[1] < 0){
            valueY = valueY * (-1);
        }
            */

        // NOTE: here, we want to make sure to check that if the values are negative, do *(-1) to revert them. we want negative G's to be displayed the same i think.

        updateValues(valueX, valueY);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }


    public void updateValues(float eventX, float eventY){
        // temporary place to hold previous values
        DataPoint[] tempValues = new DataPoint[20];


        for (int i=0; i<yValues.length-1;i++){
            // make every spot equal to the one above from displayValues
            tempValues[i] = yValues[i+1];
        }
        // finally add new data to last spot
        tempValues[19] = new DataPoint(xCounter,(eventX * mass));
        // arbitrary 30 limit should be refactored to something else.
        //if(xCounter < 30){
        //xCounter++;
        //}
        //invalidate does not remove the line completely.. we need another way to reset the graph.
        //else if (xCounter == 30){
        //graph.invalidate();
        //for (int i = 0; i<tempValues.length;i++){
        //    tempValues[i] = new DataPoint(0,0);
        //   }
        //   xCounter = 0;

        //}
        // make our temporary array the actual one, and display it IF there is a change in the last spot from initial 0.
        yValues = tempValues;
        graph.invalidate();
        values = new LineGraphSeries<>(yValues);
        values.setDrawBackground(true);
        values.setColor(Color.argb(60,0,0,255));
        values.setBackgroundColor(Color.argb(20,0,0,255));
        if(running) {
            if(eventX > graphMargin) {
                // make an SPM offset, and count time spent until < 1 again as SPM time. make an SPM offset, and count time spent until < 1 again as SPM time.
                graph.addSeries(values);

                if (xCounter < xMax) {
                    xCounter++;
                }
            }
            else if (eventX < graphMargin){
                // save time spent as SPM time
                if (xCounter >= xMax){
                    graph.removeAllSeries();
                    graph.invalidate();

                    xCounter = 0;
                    for(int i = 0; i < yValues.length; i++){
                        yValues[i] = new DataPoint(0,0);
                    }
                    updateValues(eventX,eventY);
                }
            }
        }



    }
}
