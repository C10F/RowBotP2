package b135.rowbot_p2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set window fullscreen and force landscape orientation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        checkForSave();
    }

    private void checkForSave(){
        TextView result = findViewById(R.id.resultText);
        if(Utility.readFromFile("sessionTimer.txt", getApplicationContext()) != null) {
            result.setText(Utility.readFromFile("sessionTimer.txt", getApplicationContext()));
        }
    }

    public void goToSession (View view) {
        Intent intent = new Intent(getApplicationContext(),SessionInput.class);
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

}
