package b135.rowbot_p2;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.datatype.Duration;

public class Statistics extends FragmentActivity {
    ViewPager timeSpentViewPager;
    ViewPager spmViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        // Set window fullscreen and force landscape orientation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //here we create and instance of our viewPager
        timeSpentViewPager = findViewById(R.id.timeSpentVP);
        //here we create our imageAdapter by calling the constructor method
        StatisticsImAdapterTS tsAdapter = new StatisticsImAdapterTS(getSupportFragmentManager());
        //here we take our viewPager variable and call the setAdapter method on it and then pass our adapter
        timeSpentViewPager.setAdapter(tsAdapter);

        spmViewPager = findViewById(R.id.spmVP);
        StatisticsImAdapterSPM spmAdapter = new StatisticsImAdapterSPM(getSupportFragmentManager());
        spmViewPager.setAdapter(spmAdapter);

        /*ViewPager distViewPager = findViewById(R.id.distanceVP);
        StatisticsImAdapterDist distAdapter = new StatisticsImAdapterDist(this);
        distViewPager.setAdapter(distAdapter);*/
    }

    public void timeSelected (View view){
        TextView textView = findViewById(R.id.statStartText);
        Button tButton = findViewById(R.id.TimeButton);
        Button sButton = findViewById(R.id.spmButton);
        //Button dButton = findViewById(R.id.distButton);
        ViewPager timeSpentViewPager = findViewById(R.id.timeSpentVP);
        ViewPager spmViewPager = findViewById(R.id.spmVP);
        //ViewPager distViewPager = findViewById(R.id.distanceVP);

        textView.setVisibility(View.GONE);

        tButton.setBackgroundResource(R.drawable.statistics_buttons_selected);
        sButton.setBackgroundResource(R.drawable.statistics_buttons_deselected);
        //dButton.setBackgroundResource(R.drawable.statistics_buttons_deselected);

        timeSpentViewPager.setVisibility(View.VISIBLE);
        spmViewPager.setVisibility(View.GONE);
        //distViewPager.setVisibility(View.GONE);
    }

    public void distanceSelected (View view){
        TextView textView = findViewById(R.id.statStartText);
        Button tButton = findViewById(R.id.TimeButton);
        Button sButton = findViewById(R.id.spmButton);
        //Button dButton = findViewById(R.id.distButton);
        ViewPager timeSpentViewPager = findViewById(R.id.timeSpentVP);
        ViewPager spmViewPager = findViewById(R.id.spmVP);
        //ViewPager distViewPager = findViewById(R.id.distanceVP);

        textView.setVisibility(View.GONE);

        //dButton.setBackgroundResource(R.drawable.statistics_buttons_selected);
        tButton.setBackgroundResource(R.drawable.statistics_buttons_deselected);
        sButton.setBackgroundResource(R.drawable.statistics_buttons_deselected);

        timeSpentViewPager.setVisibility(View.GONE);
        spmViewPager.setVisibility(View.GONE);
        //distViewPager.setVisibility(View.VISIBLE);
    }

    public void spmSelected (View view){
        TextView textView = findViewById(R.id.statStartText);
        Button tButton = findViewById(R.id.TimeButton);
        Button sButton = findViewById(R.id.spmButton);
        //Button dButton = findViewById(R.id.distButton);
        ViewPager timeSpentViewPager = findViewById(R.id.timeSpentVP);
        ViewPager spmViewPager = findViewById(R.id.spmVP);
        //ViewPager distViewPager = findViewById(R.id.distanceVP);

        textView.setVisibility(View.GONE);

        sButton.setBackgroundResource(R.drawable.statistics_buttons_selected);
        tButton.setBackgroundResource(R.drawable.statistics_buttons_deselected);
        //dButton.setBackgroundResource(R.drawable.statistics_buttons_deselected);

        timeSpentViewPager.setVisibility(View.GONE);
        spmViewPager.setVisibility(View.VISIBLE);
        //distViewPager.setVisibility(View.GONE);
    }
}
