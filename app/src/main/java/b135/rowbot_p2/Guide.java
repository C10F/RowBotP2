package b135.rowbot_p2;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Guide extends AppCompatActivity {

    boolean videoShowing;
    boolean setupShowing;
    boolean termShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        // force landscape mode and full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
}
