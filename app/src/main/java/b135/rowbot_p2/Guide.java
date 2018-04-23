package b135.rowbot_p2;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Guide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        // force landscape mode and full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void showVideo(View view){
        // set our video-view and title to be visible on button press
        ImageView videoPH = findViewById(R.id.embedVideoPH);
        TextView videoTxt = findViewById(R.id.videoText);

        videoPH.setVisibility(View.VISIBLE);
        videoTxt.setVisibility(View.VISIBLE);

    }
}
