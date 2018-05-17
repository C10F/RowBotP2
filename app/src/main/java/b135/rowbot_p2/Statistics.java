package b135.rowbot_p2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

import javax.xml.datatype.Duration;

public class Statistics extends FragmentActivity {
    ViewPager timeSpentViewPager;
    ViewPager spmViewPager;
    private DrawerLayout mDrawerLayout;
    private boolean running = false;
    //GraphView tsWeekChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        // Set window fullscreen and force landscape orientation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            running = extras.getBoolean("SAVED_RUNNING");
        }

        mDrawerLayout = findViewById(R.id.drawerLayoutStatistics);

        NavigationView navigationView = findViewById(R.id.navViewStatistics);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        int id = item.getItemId();

                        if (id == R.id.navMainFromStatistics){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.navSessionInputFromStatistics){
                            if (!running){
                                running = true;
                                Intent intent = new Intent(getApplicationContext(), SessionInput.class);
                                startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(getApplicationContext(), Session.class);
                                startActivity(intent);
                            }
                        }
                        else if (id == R.id.navGuideFromStatistics){
                            Intent intent = new Intent(getApplicationContext(), Guide.class);
                            startActivity(intent);
                        }

                        return true;
                    }
                });

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

        /*
        tsWeekChart = findViewById(R.id.timeSpentWeekBarchart);

        BarGraphSeries<DataPoint> tsWeekDataSet = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 44),
                new DataPoint(1, 88),
                new DataPoint(2, 66),
                new DataPoint(3, 12),
                new DataPoint(4, 19),
                new DataPoint(5, 91),
                new DataPoint(6, 22),
        });
        tsWeekChart.addSeries(tsWeekDataSet);
        tsWeekChart.setTitle("hej med dig");
        */
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

    public void openDrawerStatistics(View view) {
        mDrawerLayout.openDrawer(Gravity.START);
    }
}
