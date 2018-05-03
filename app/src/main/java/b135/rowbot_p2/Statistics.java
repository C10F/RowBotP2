package b135.rowbot_p2;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import javax.xml.datatype.Duration;

public class Statistics extends AppCompatActivity {

    private boolean weekSelected;
    private boolean monthSelected;
    private boolean timeSelected;
    private boolean distanceSelected;
    private boolean spmSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        // Set window fullscreen and force landscape orientation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void weekSelected (View view){
        // select week, dismiss month
        monthSelected = false;
        weekSelected = true;

        Button wButton = findViewById(R.id.weekButton);
        Button mButton = findViewById(R.id.monthButton);
        // change color of buttons to identify choice
        wButton.setBackgroundColor(Color.GREEN);
        mButton.setBackgroundColor(Color.LTGRAY);

        ImageView tmonthph = findViewById(R.id.monthTimeSpent);
        ImageView tweekph = findViewById(R.id.weekTimeSpent);
        ImageView dmonthph = findViewById(R.id.monthDistCov);
        ImageView dweekph = findViewById(R.id.weekDistCov);
        ImageView sweekph = findViewById(R.id.weekSPM);
        ImageView smonthph = findViewById(R.id.monthSPM);

        if (weekSelected && timeSelected){
            tmonthph.setVisibility(View.GONE);
            tweekph.setVisibility(View.VISIBLE);
        }
        else if (weekSelected && distanceSelected){
            dmonthph.setVisibility(View.GONE);
            dweekph.setVisibility(View.VISIBLE);
        }
        else if (weekSelected && spmSelected){
            smonthph.setVisibility(View.GONE);
            sweekph.setVisibility(View.VISIBLE);
        }
    }

    public void monthSelected (View view) {
        // selected month, dismiss week
        weekSelected = false;
        monthSelected = true;

        Button mButton = findViewById(R.id.monthButton);
        Button wButton = findViewById(R.id.weekButton);
        // change color of buttons to identify choice
        wButton.setBackgroundColor(Color.LTGRAY);
        mButton.setBackgroundColor(Color.GREEN);

        ImageView tmonthph = findViewById(R.id.monthTimeSpent);
        ImageView tweekph = findViewById(R.id.weekTimeSpent);
        ImageView dmonthph = findViewById(R.id.monthDistCov);
        ImageView dweekph = findViewById(R.id.weekDistCov);
        ImageView sweekph = findViewById(R.id.weekSPM);
        ImageView smonthph = findViewById(R.id.monthSPM);

        if (monthSelected && timeSelected){
            tweekph.setVisibility(View.GONE);
            tmonthph.setVisibility(View.VISIBLE);
        }
        else if (monthSelected && distanceSelected){
            dweekph.setVisibility(View.GONE);
            dmonthph.setVisibility(View.VISIBLE);
        }
        else if (monthSelected && spmSelected){
            sweekph.setVisibility(View.GONE);
            smonthph.setVisibility(View.VISIBLE);
        }
    }

    public void timeSelected (View view){
        timeSelected = true;
        distanceSelected = false;
        spmSelected = false;

        Button tButton = findViewById(R.id.TimeButton);
        Button dButton = findViewById(R.id.distButton);
        Button sButton = findViewById(R.id.spmButton);

        ImageView tmonthph = findViewById(R.id.monthTimeSpent);
        ImageView tweekph = findViewById(R.id.weekTimeSpent);
        ImageView dmonthph = findViewById(R.id.monthDistCov);
        ImageView dweekph = findViewById(R.id.weekDistCov);
        ImageView sweekph = findViewById(R.id.weekSPM);
        ImageView smonthph = findViewById(R.id.monthSPM);

        // make other selections GONE
        dmonthph.setVisibility(View.GONE);
        dweekph.setVisibility(View.GONE);
        smonthph.setVisibility(View.GONE);
        sweekph.setVisibility(View.GONE);

        tButton.setBackgroundColor(Color.GREEN);
        dButton.setBackgroundColor(Color.LTGRAY);
        sButton.setBackgroundColor(Color.LTGRAY);

        if(weekSelected){
            // show weekly overview of selected
            tmonthph.setVisibility(View.GONE);
            tweekph.setVisibility(View.VISIBLE);

        }
        else if (monthSelected){
            // show monthly overview of selected
            tweekph.setVisibility(View.GONE);
            tmonthph.setVisibility(View.VISIBLE);
        }
        // prompt user to make a decision on weekly/monthly overview if none has been selected
        else Toast.makeText(getApplicationContext(),"please select either weekly, or monthly overview",Toast.LENGTH_SHORT).show();

    }

    public void distanceSelected (View view){
        timeSelected = false;
        distanceSelected = true;
        spmSelected = false;

        Button tButton = findViewById(R.id.TimeButton);
        Button dButton = findViewById(R.id.distButton);
        Button sButton = findViewById(R.id.spmButton);

        ImageView tmonthph = findViewById(R.id.monthTimeSpent);
        ImageView tweekph = findViewById(R.id.weekTimeSpent);
        ImageView dmonthph = findViewById(R.id.monthDistCov);
        ImageView dweekph = findViewById(R.id.weekDistCov);
        ImageView sweekph = findViewById(R.id.weekSPM);
        ImageView smonthph = findViewById(R.id.monthSPM);

        // make other selections GONE
        tmonthph.setVisibility(View.GONE);
        tweekph.setVisibility(View.GONE);
        smonthph.setVisibility(View.GONE);
        sweekph.setVisibility(View.GONE);

        tButton.setBackgroundColor(Color.LTGRAY);
        dButton.setBackgroundColor(Color.GREEN);
        sButton.setBackgroundColor(Color.LTGRAY);

        if(weekSelected){
            // show weekly overview of selected
            dmonthph.setVisibility(View.GONE);
            dweekph.setVisibility(View.VISIBLE);

        }
        else if (monthSelected){
            // show monthly overview of selected
            dweekph.setVisibility(View.GONE);
            dmonthph.setVisibility(View.VISIBLE);
        }
        // prompt user to make a decision on weekly/monthly overview if none has been selected
        else Toast.makeText(getApplicationContext(),"please select either weekly, or monthly overview",Toast.LENGTH_SHORT).show();

    }

    public void spmSelected (View view){
        timeSelected = false;
        distanceSelected = false;
        spmSelected = true;

        Button tButton = findViewById(R.id.TimeButton);
        Button dButton = findViewById(R.id.distButton);
        Button sButton = findViewById(R.id.spmButton);

        ImageView tmonthph = findViewById(R.id.monthTimeSpent);
        ImageView tweekph = findViewById(R.id.weekTimeSpent);
        ImageView dmonthph = findViewById(R.id.monthDistCov);
        ImageView dweekph = findViewById(R.id.weekDistCov);
        ImageView sweekph = findViewById(R.id.weekSPM);
        ImageView smonthph = findViewById(R.id.monthSPM);

        // make other selections GONE
        tmonthph.setVisibility(View.GONE);
        tweekph.setVisibility(View.GONE);
        dmonthph.setVisibility(View.GONE);
        dweekph.setVisibility(View.GONE);

        tButton.setBackgroundColor(Color.LTGRAY);
        dButton.setBackgroundColor(Color.LTGRAY);
        sButton.setBackgroundColor(Color.GREEN);

        if(weekSelected){
            // show weekly overview of selected
            smonthph.setVisibility(View.GONE);
            sweekph.setVisibility(View.VISIBLE);
        }
        else if (monthSelected){
            // show monthly overview of selected
            sweekph.setVisibility(View.GONE);
            smonthph.setVisibility(View.VISIBLE);
        }
        // prompt user to make a decision on weekly/monthly overview if none has been selected
        else Toast.makeText(getApplicationContext(),"please select either weekly, or monthly overview",Toast.LENGTH_SHORT).show();

    }
}


/*
            NOTE: COLOR CHANGING BUTTONS HAVE A BUG WHERE THEY CHANGE THEME WHEN CHANGING COLOR, LOOK INTO THAT LATER /CF
*/
