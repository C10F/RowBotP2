package b135.rowbot_p2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


public class Session extends AppCompatActivity {

    //chronometer variable
    private Chronometer sessionTimer;
    //used to calculate the offset from when the timer is paused to when it is continued since by default it keeps running in the background
    private long pauseOffset;
    //boolean used to see whether or not the timer is running
    private boolean running;
    //boolean used to see whether or not the stop button is available and if true then the session is able to stop
    private boolean sessionStop = false;
    String targetDistance;
    String targetTime;


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
            targetDistance = extra.getString("EXTRA_DISTANCE");
            targetTime = extra.getString("EXTRA_TIME");
        }

        //here we create and instance of our viewPager
        ViewPager viewPager = findViewById(R.id.sessionViewPager);
        //here we create our imageAdapter by calling the constructor method
        ImageAdapter adapter = new ImageAdapter(this);
        //here we take our viewPager variable and call the setAdapter method on it and then pass our adapter
        viewPager.setAdapter(adapter);
        TextView debugD = findViewById(R.id.debugOutput);
        TextView debugT = findViewById(R.id.debugOutput2);
        debugD.setText(targetDistance);
        debugT.setText(targetTime);

        //sets the chronometer variable to the chronometer in the xml file by id
        sessionTimer = findViewById(R.id.timerCounter);
        //sets the input format to a string of text where the %s means the format is mm:ss after 59:59 the format change automatically to hh:mm:ss
        sessionTimer.setFormat("Elapsed time:\n%s\n");
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
            sButton.setBackgroundColor(getResources().getColor(R.color.green));
            sButton.setTextColor(getResources().getColor(R.color.black));
            pButton.setBackgroundColor(getResources().getColor(R.color.black));
            pButton.setTextColor(getResources().getColor(R.color.white));
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
        }
    }

    //when you click the button pause it's onClick listener starts this method to pause the timer
    public void pauseTimerCounter(View v) {
        //initializing buttons by id
        Button sButton = findViewById(R.id.startSession);
        Button pButton = findViewById(R.id.pauseSession);
        //if the boolean is true i.e. running the timer will pause
        if (running) {
            //sets various colors and texts
            sButton.setBackgroundColor(getResources().getColor(R.color.black));
            sButton.setTextColor(getResources().getColor(R.color.white));
            pButton.setBackgroundColor(getResources().getColor(R.color.red));
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
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }
}
