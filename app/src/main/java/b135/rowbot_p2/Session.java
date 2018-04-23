package b135.rowbot_p2;

import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;

public class Session extends AppCompatActivity {

    private Chronometer sessionTimer;
    private long pauseOffset;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        // force landscape mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sessionTimer = findViewById(R.id.timerCounter);
        sessionTimer.setFormat("Time : %s");
        sessionTimer.setBase(SystemClock.elapsedRealtime());

    }

    public void startTimerCounter(View v) {
        if (!running) {
            sessionTimer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            sessionTimer.start();
            running = true;
        }
    }

    public void pauseTimerCounter(View v) {
        if (running) {
            sessionTimer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - sessionTimer.getBase();
            running = false;
        }
    }
}
