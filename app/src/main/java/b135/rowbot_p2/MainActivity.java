package b135.rowbot_p2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.*;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private boolean running = false;
    private boolean hasOpened;

    //private static final String KEY_RUNNING = "running boolean save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set window fullscreen and force landscape orientation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        checkForSave();

        if (Utility.readFromFile("hasOpened.txt",getApplicationContext()).equals("true")){
            hasOpened = true;
        }
        else if (!hasOpened){
            File f = new File("hasOpened.txt");
            runZero();
            Utility.writeToFile("true","hasOpened.txt",getApplicationContext());
            hasOpened = true;
        }

        //weekDays = new File[]{new File("tsMonday.txt"), new File("tsTuesday.txt"), new File("tsWednesday.txt"), new File("tsThursday.txt"), new File("tsFriday.txt"), new File("tsSaturday.txt"), new File("tsSunday")};

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            running = extras.getBoolean("SAVED_RUNNING");
        }

        mDrawerLayout = findViewById(R.id.drawerLayoutMain);

        NavigationView navigationView = findViewById(R.id.navViewMain);
            navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        int id = item.getItemId();

                        if (id == R.id.navSessionInput){
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
                        else if (id == R.id.navStatistics){
                            Intent intent = new Intent(getApplicationContext(), Statistics.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.navGuide){
                            Intent intent = new Intent(getApplicationContext(), Guide.class);
                            startActivity(intent);
                        }

                        return true;
                    }
                });
    }

    private void runZero() {
        Utility.writeToFile("0", "tsMonday.txt", getApplicationContext());
        Utility.writeToFile("0", "tsTuesday.txt", getApplicationContext());
        Utility.writeToFile("0", "tsWednesday.txt", getApplicationContext());
        Utility.writeToFile("0", "tsThursday.txt", getApplicationContext());
        Utility.writeToFile("0", "tsFriday.txt", getApplicationContext());
        Utility.writeToFile("0", "tsSaturday.txt", getApplicationContext());
        Utility.writeToFile("0", "tsSunday.txt", getApplicationContext());
    }

    private void checkForSave(){
        TextView result = findViewById(R.id.resultText);
        if(Utility.readFromFile("tsTuesday.txt", getApplicationContext()) != null) {
            result.setText(Utility.readFromFile("tsTuesday.txt", getApplicationContext()));
        }
    }

    public void goToSession (View view) {
        Intent intent = new Intent(getApplicationContext(),SessionInput.class);
        running = true;
        startActivity(intent);
    }

    public void goToStats (View view) {
        Intent intent = new Intent(getApplicationContext(),Statistics.class);
        startActivity(intent);

    }

    public void goToGuide (View view) {
        Intent intent = new Intent(getApplicationContext(),Guide.class);
        startActivity(intent);

    }
    public void goToTest (View view) {
        Intent goToTest = new Intent(getApplicationContext(), SensorTest_Activity.class);
        startActivity(goToTest);
    }

    public void openDrawer(View view) {
        mDrawerLayout.openDrawer(Gravity.START);
    }

    /*@Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(KEY_RUNNING,running);

        super.onSaveInstanceState(savedInstanceState);
    }*/

    /*@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        running = savedInstanceState.getBoolean(KEY_RUNNING);
    }*/
}
