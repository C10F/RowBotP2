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


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import javax.xml.datatype.Duration;

public class Statistics extends FragmentActivity {
    ViewPager timeSpentViewPager;
    ViewPager spmViewPager;
    //GraphView tsWeekChart;
    String[] nullAddZero = new String[]{"tsMonday.txt", "tsTuesday.txt", "tsWednesday.txt", "tsThursday.txt", "tsFriday.txt", "tsSaturday.txt", "tsSunday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        // Set window fullscreen and force landscape orientation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        /*for (int i = 0 ; i < nullAddZero.length+1 ; i++){
            if(Utility.readFromFile(nullAddZero[i], getApplicationContext()) == null) {
                Utility.writeToFile("0",nullAddZero[i], getApplicationContext());
            }
        }*/

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

    /*public static ArrayList<File> getAllFilesInDir(File dir) {
        if (dir == null)
            return null;

        ArrayList<File> files = new ArrayList<>();

        Stack<File> dirList = new Stack<>();
        dirList.clear();
        dirList.push(dir);

        while (!dirList.isEmpty()) {
            File dirCurrent = dirList.pop();

            File[] fileList = dirCurrent.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory())
                    dirList.push(aFileList);
                else
                    files.add(aFileList);
            }
        }
        return files;
    }*/
}
