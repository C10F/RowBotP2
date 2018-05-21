package b135.rowbot_p2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
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

    private boolean runningForDrawer = true;

    //sensor variables
    private Sensor mySensor;
    private SensorManager SM;
    private LineGraphSeries values;
    private DataPoint[] displayValues = new DataPoint[20];                                                              // the array that holds graph values
    float xCounter;
    GraphView graph;


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

        elapsedTime = elapsedTimeText+elapsedTimeCounter;

        // Create our Sensor Manager
        SM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER);

        // Register sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        graph = findViewById(R.id.sessionGraph);

        for (int i = 0; i<displayValues.length;i++){
            displayValues[i] = new DataPoint(0,0);
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
        }
    }

    //when you click the button pause it's onClick listener starts this method to pause the timer
    public void pauseTimerCounter(View v) {
        //initializing buttons by id
        Button sButton = findViewById(R.id.startSession);
        Button pButton = findViewById(R.id.pauseSession);
        Button saveButton = findViewById(R.id.saveSession);
        Button rButton = findViewById(R.id.returnSession);
        GraphView graphView = findViewById(R.id.sessionGraph);
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
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) graphView.getLayoutParams();
            layoutParams.addRule(RelativeLayout.BELOW, saveButton.getId());
            graphView.setLayoutParams(layoutParams);
            sButton.setVisibility(View.GONE);
            pButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
            rButton.setVisibility(View.VISIBLE);
        }
    }

    public void saveSession(View v) {
        if (runningForDrawer){
            runningForDrawer = false;
        }
        // a test save right here:
        //Utility.writeToFile(sessionTimer.getText().toString(), "sessionTimer.txt", getApplicationContext());
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

        Utility.writeToFile(sessionTimer.getText().toString(), dayMonth+".txt", getApplicationContext());
        Utility.writeToFile(debugT.getText().toString(), dayMonth+"targetTime.txt", getApplicationContext());
        //checkForContentMonth(dayMonth+".txt");

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("SAVED_RUNNING",runningForDrawer);
        // somewhere about here, we want to save an 'entry' of a new session (save the data long term)
        // toast the user that session has been saved
        Utility.doToast(getApplicationContext(),"Session was saved");
        startActivity(intent);
    }

    private void checkForContent(String weekDay) {
        if(!weekDay.equals("0")) {
            // conv to int, add new sessiomdata as int, convert to string
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

    /*private void checkForContentMonth(String monthDay) {
        if (!monthDay.equals("0")) {
            int oldST = Integer.parseInt(Utility.divideString(Utility.readFromFile(monthDay,getApplicationContext())));
            int newST = Integer.parseInt(Utility.divideString(sessionTimer.getText().toString()));
            int combinedST = oldST+newST;
            Utility.writeToFile(Integer.toString(combinedST),monthDay,getApplicationContext());
        }
        else {
            Utility.writeToFile(sessionTimer.getText().toString(), dayMonth+".txt", getApplicationContext());
        }
    }*/

    public void returnToSession(View v) {
        //initializing buttons by id
        Button sButton = findViewById(R.id.startSession);
        Button pButton = findViewById(R.id.pauseSession);
        Button saveButton = findViewById(R.id.saveSession);
        Button rButton = findViewById(R.id.returnSession);
        GraphView graphView = findViewById(R.id.sessionGraph);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) graphView.getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, pButton.getId());
        graphView.setLayoutParams(layoutParams);

        sButton.setVisibility(View.VISIBLE);
        pButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
        rButton.setVisibility(View.GONE);
        // COMMENTS PL0X
    }

    public void openDrawerSession(View view) {
        mDrawerLayout.openDrawer(Gravity.START);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //xText.setText("X: " + event.values[0]);
        //yText.setText("Y: " + event.values[1]);
        //zText.setText("Z: " + event.values[2]);


        // NOTE: here, we want to make sure to check that if the values are negative, do *(-1) to revert them. we want negative G's to be displayed the same i think.
        updateValues(event.values[0], event.values[1]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }

    public void updateValues(float event1, float event2){
        // temporary place to hold previous values
        DataPoint[] tempValues = new DataPoint[20];

        for (int i=0; i<displayValues.length-1;i++){
            // make every spot equal to the one above from displayValues
            tempValues[i] = displayValues[i+1];
        }
        // finally add new data to last spot
        tempValues[19] = new DataPoint(xCounter,event2);
        // arbitrary 30 limit should be refactored to something else.
        if(xCounter < 30){
            xCounter++;
        }
        //invalidate does not remove the line completely.. we need another way to reset the graph.
        else if (xCounter == 30){
            graph.invalidate();
            for (int i = 0; i<tempValues.length;i++){
                tempValues[i] = new DataPoint(0,0);
            }
            xCounter = 0;

        }
        // make our temporary array the actual one, and display it IF there is a change in the last spot from initial 0.
        displayValues = tempValues;
        values = new LineGraphSeries<>(displayValues);
        if(displayValues[19] != new DataPoint(0,0)){
            graph.addSeries(values);
        }

    }
}
