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
        // this checks for a first-launch boolean. this is only false on very first open
        if (Utility.readFromFile("hasOpened.txt",getApplicationContext()).equals("true")){
            hasOpened = true;
        }
        else if (!hasOpened){
            File f = new File("hasOpened.txt");
            // if we never opened the app before, init vars for statistics with 0's
            runZero();
            // and write the hasOpened first-launch boolean string.
            Utility.writeToFile("true","hasOpened.txt",getApplicationContext());
            hasOpened = true;
        }

        // get extras from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            running = extras.getBoolean("SAVED_RUNNING");
        }

        mDrawerLayout = findViewById(R.id.drawerLayoutMain);
        // populate the drawer with appropriate items
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
    // this method writes a 0 to all weekday files for statistics.
    // in effect, this creates the file so that we can display it's values (even if 0)
    private void runZero() {
        Utility.writeToFile("0", "tsMonday.txt", getApplicationContext());
        Utility.writeToFile("0", "tsTuesday.txt", getApplicationContext());
        Utility.writeToFile("0", "tsWednesday.txt", getApplicationContext());
        Utility.writeToFile("0", "tsThursday.txt", getApplicationContext());
        Utility.writeToFile("0", "tsFriday.txt", getApplicationContext());
        Utility.writeToFile("0", "tsSaturday.txt", getApplicationContext());
        Utility.writeToFile("0", "tsSunday.txt", getApplicationContext());

        Utility.writeToFile("0", "spmMonday.txt", getApplicationContext());
        Utility.writeToFile("0", "spmTuesday.txt", getApplicationContext());
        Utility.writeToFile("0", "spmWednesday.txt", getApplicationContext());
        Utility.writeToFile("0", "spmThursday.txt", getApplicationContext());
        Utility.writeToFile("0", "spmFriday.txt", getApplicationContext());
        Utility.writeToFile("0", "spmSaturday.txt", getApplicationContext());
        Utility.writeToFile("0", "spmSunday.txt", getApplicationContext());
    }

    //used for debugging the saved data from session to tsWeek
    /*private void checkForSave(){
        TextView result = findViewById(R.id.resultText);
        if(Utility.readFromFile("tsTuesday.txt", getApplicationContext()) != null) {
            result.setText(Utility.readFromFile("tsTuesday.txt", getApplicationContext()));
        }
    }*/
    // below are the three OnClick's for the main menu
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
    // this one is no longer used in this version of the prototype
    public void goToTest (View view) {
        Intent goToTest = new Intent(getApplicationContext(), SensorTest_Activity.class);
        startActivity(goToTest);
    }

    public void openDrawer(View view) {
        mDrawerLayout.openDrawer(Gravity.START);
    }
}
