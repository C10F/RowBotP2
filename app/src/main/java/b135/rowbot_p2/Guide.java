package b135.rowbot_p2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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

public class Guide extends AppCompatActivity {

    boolean videoShowing;
    boolean setupShowing;
    boolean termShowing;

    private DrawerLayout mDrawerLayout;
    private boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        // force landscape mode and full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            running = extras.getBoolean("SAVED_RUNNING");
        }

        mDrawerLayout = findViewById(R.id.drawerLayoutGuide);

        NavigationView navigationView = findViewById(R.id.navViewGuide);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        int id = item.getItemId();

                        if (id == R.id.navMainFromGuide){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else if (id == R.id.navSessionInputFromGuide){
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
                        else if (id == R.id.navStatisticsFromGuide){
                            Intent intent = new Intent(getApplicationContext(), Statistics.class);
                            startActivity(intent);
                        }

                        return true;
                    }
                });
    }


    public void showVideo(View view){
        ImageView termPH = findViewById(R.id.termPH);
        ImageView videoPH = findViewById(R.id.embedVideoPH);
        ImageView setupPH = findViewById(R.id.setupPH);
        TextView videoTxt = findViewById(R.id.videoText);
        TextView startText = findViewById(R.id.guideStartText);
        Button vButton = findViewById(R.id.videoButton);
        Button sButton = findViewById(R.id.setupButton);
        Button tButton = findViewById(R.id.TermButton);
        // check for other views showing
        if (setupShowing) {
            setupPH.setVisibility(View.GONE);
            setupShowing = false;
        }
        if (termShowing){
            termPH.setVisibility(View.GONE);
            termShowing = false;
        }
        vButton.setBackgroundResource(R.drawable.statistics_buttons_selected);
        tButton.setBackgroundResource(R.drawable.statistics_buttons_deselected);
        sButton.setBackgroundResource(R.drawable.statistics_buttons_deselected);
        startText.setVisibility(View.GONE);
        // set our video-view and title to be visible on button press
        videoPH.setVisibility(View.VISIBLE);
        videoTxt.setVisibility(View.VISIBLE);
        videoPH.bringToFront();
        videoPH.setClickable(true);
        videoPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=zQ82RYIFLN8&t=11s"));
                startActivity(videoIntent);
            }
        });
        videoShowing = true;
    }


    public void showSetup(View view){
        ImageView termPH = findViewById(R.id.termPH);
        ImageView videoPH = findViewById(R.id.embedVideoPH);
        ImageView setupPH = findViewById(R.id.setupPH);
        TextView videoTxt = findViewById(R.id.videoText);
        TextView startText = findViewById(R.id.guideStartText);
        Button vButton = findViewById(R.id.videoButton);
        Button sButton = findViewById(R.id.setupButton);
        Button tButton = findViewById(R.id.TermButton);

        // check for other views showing
        if (videoShowing) {
           videoPH.setVisibility(View.GONE);
           videoTxt.setVisibility(View.GONE);
           videoShowing = false;
        }
        if (termShowing){
            termPH.setVisibility(View.GONE);
            termShowing = false;
        }
        sButton.setBackgroundResource(R.drawable.statistics_buttons_selected);
        tButton.setBackgroundResource(R.drawable.statistics_buttons_deselected);
        vButton.setBackgroundResource(R.drawable.statistics_buttons_deselected);
        startText.setVisibility(View.GONE);
        // set our setup guide to be visible on button press
        setupPH.setVisibility(View.VISIBLE);
        setupShowing = true;
    }

    public void showTermin(View view){
        ImageView termPH = findViewById(R.id.termPH);
        ImageView videoPH = findViewById(R.id.embedVideoPH);
        ImageView setupPH = findViewById(R.id.setupPH);
        TextView videoTxt = findViewById(R.id.videoText);
        TextView startText = findViewById(R.id.guideStartText);
        Button vButton = findViewById(R.id.videoButton);
        Button sButton = findViewById(R.id.setupButton);
        Button tButton = findViewById(R.id.TermButton);

        // check for other views showing
        if(videoShowing){
            videoPH.setVisibility(View.GONE);
            videoTxt.setVisibility(View.GONE);
            videoShowing = false;
        }
        if(setupShowing){
            setupPH.setVisibility(View.GONE);
            setupShowing = false;
        }
        tButton.setBackgroundResource(R.drawable.statistics_buttons_selected);
        vButton.setBackgroundResource(R.drawable.statistics_buttons_deselected);
        sButton.setBackgroundResource(R.drawable.statistics_buttons_deselected);
        startText.setVisibility(View.GONE);
        // set our terminology infographic to be visible on button press
        termPH.setVisibility(View.VISIBLE);
        termShowing = true;

    }

    public void openDrawerGuide(View view) {
        mDrawerLayout.openDrawer(Gravity.START);
    }
}
