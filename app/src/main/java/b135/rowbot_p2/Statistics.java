package b135.rowbot_p2;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
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
    }

    public void timeSelected (View view){

        if(weekSelected){
            // show weekly overview of selected
            ImageView weekph = findViewById(R.id.weekTimeSpent);
            weekph.setVisibility(View.VISIBLE);

        }
        else if (monthSelected){
            // show monthly overview of selected
        }
        // prompt user to make a decision on weekly/monthly overview if none has been selected
        else Toast.makeText(getApplicationContext(),"please select either weekly, or monthly overview",Toast.LENGTH_SHORT).show();

    }

    public void distanceSelected (View view){

        if(weekSelected){
            // show weekly overview of selected

        }
        else if (monthSelected){
            // show monthly overview of selected
        }
        // prompt user to make a decision on weekly/monthly overview if none has been selected
        else Toast.makeText(getApplicationContext(),"please select either weekly, or monthly overview",Toast.LENGTH_SHORT).show();

    }

    public void spmSelected (View view){

        if(weekSelected){
            // show weekly overview of selected

        }
        else if (monthSelected){
            // show monthly overview of selected
        }
        // prompt user to make a decision on weekly/monthly overview if none has been selected
        else Toast.makeText(getApplicationContext(),"please select either weekly, or monthly overview",Toast.LENGTH_SHORT).show();

    }
}


/*
            NOTE: COLOR CHANGING BUTTONS HAVE A BUG WHERE THEY CHANGE THEME WHEN CHANGING COLOR, LOOK INTO THAT LATER /CF
*/
