package b135.rowbot_p2;

import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;

public class Session extends AppCompatActivity {

    //chronometer variable
    private Chronometer sessionTimer;
    //used to calculate the offset from when the timer is paused to when it is continued since by default it keeps running in the background
    private long pauseOffset;
    //boolean used to see whether or not the timer is running
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        // force landscape mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //sets the chronometer variable to the chronometer in the xml file by id
        sessionTimer = findViewById(R.id.timerCounter);
        //sets the input format to a string of text where the %s means the format is mm:ss after 59:59 the format change automatically to hh:mm:ss
        sessionTimer.setFormat("Time:\n%s\n");
        //this updates the string when we start the activity
        sessionTimer.setBase(SystemClock.elapsedRealtime());

    }

    //when you click the button start it's onClick listener starts this method to run the timer
    public void startTimerCounter(View v) {
        //if the boolean is false i.e. not running the timer will start
        if (!running) {
            //this makes the chronometer start counting when we click the button and not from when we start the activity which is the default
            //we subtract the offset to make sure that if we pause it, when we the run it again it will
            //continue from where we left and not keep counting in the background
            //definition of elapsedRealtime() - Returns milliseconds since boot, including time spent in real time.
            sessionTimer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            //starts the chronometer i.e. the timer and makes the timer text continue
            sessionTimer.start();
            //sets running to true so that when you press pause the if statement is active
            running = true;
        }
    }

    //when you click the button pause it's onClick listener starts this method to pause the timer
    public void pauseTimerCounter(View v) {
        //if the boolean is true i.e. running the timer will pause
        if (running) {
            //pauses the chronometer i.e. the timer and stops the timer text without resetting it.
            sessionTimer.stop();
            //this resets the elapsedRealtime to zero since it take the time that has elapsed so far and subtract that by getting the base
            //we set in the startTimerCounter method.
            //this makes it so that once we start the timer it start from 0 again through the setBase
            //this is the counter that resets to zero NOT the text the timer text stays the same.
            pauseOffset = SystemClock.elapsedRealtime() - sessionTimer.getBase();
            //sets running to false so that when you press start the if statement is active
            running = false;
        }
    }
}
