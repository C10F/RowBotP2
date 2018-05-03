package b135.rowbot_p2;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import javax.xml.datatype.Duration;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        // Set window fullscreen and force landscape orientation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //here we create and instance of our viewPager
        ViewPager timeSpentViewPager = findViewById(R.id.timeSpentVP);
        //here we create our imageAdapter by calling the constructor method
        StatisticsImAdapterTS tsAdapter = new StatisticsImAdapterTS(this);
        //here we take our viewPager variable and call the setAdapter method on it and then pass our adapter
        timeSpentViewPager.setAdapter(tsAdapter);

        ViewPager spmViewPager = findViewById(R.id.spmVP);
        StatisticsImAdapterSPM spmAdapter = new StatisticsImAdapterSPM(this);
        spmViewPager.setAdapter(spmAdapter);

        ViewPager distViewPager = findViewById(R.id.distanceVP);
        StatisticsImAdapterDist distAdapter = new StatisticsImAdapterDist(this);
        distViewPager.setAdapter(distAdapter);
    }

    public void timeSelected (View view){
        ViewPager timeSpentViewPager = findViewById(R.id.timeSpentVP);
        ViewPager spmViewPager = findViewById(R.id.spmVP);
        ViewPager distViewPager = findViewById(R.id.distanceVP);

        timeSpentViewPager.setVisibility(View.VISIBLE);
        spmViewPager.setVisibility(View.GONE);
        distViewPager.setVisibility(View.GONE);
    }

    public void distanceSelected (View view){
        ViewPager timeSpentViewPager = findViewById(R.id.timeSpentVP);
        ViewPager spmViewPager = findViewById(R.id.spmVP);
        ViewPager distViewPager = findViewById(R.id.distanceVP);

        timeSpentViewPager.setVisibility(View.GONE);
        spmViewPager.setVisibility(View.GONE);
        distViewPager.setVisibility(View.VISIBLE);
    }

    public void spmSelected (View view){
        ViewPager timeSpentViewPager = findViewById(R.id.timeSpentVP);
        ViewPager spmViewPager = findViewById(R.id.spmVP);
        ViewPager distViewPager = findViewById(R.id.distanceVP);

        timeSpentViewPager.setVisibility(View.GONE);
        spmViewPager.setVisibility(View.VISIBLE);
        distViewPager.setVisibility(View.GONE);
    }
}
