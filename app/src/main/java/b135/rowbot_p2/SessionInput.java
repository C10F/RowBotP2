package b135.rowbot_p2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SessionInput extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private TextInputLayout sessionInputD;
    private TextInputLayout sessionInputT;
    private TextView selected;
    private Button goTo;
    private String inputTargetDistance;
    private String inputTargetTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_input);
        // force landscape mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //reference to the spinner from the xml found by id
        Spinner spinner = findViewById(R.id.presetSpinner);

        //reference to the TextInputLayout fields from the xml found by id
        sessionInputD = findViewById(R.id.distanceText);
        sessionInputT = findViewById(R.id.timeText);

        //this array adapter fits our spinner with the text from the string-array
        //this ArrayAdapter takes 3 attributes first is context in our case this which refer to this
        //specific instance of an object of a class then our array and lastly a layout file
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.Presets, android.R.layout.simple_spinner_item);

        //this creates the layout for our spinners dropdown menu
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //here we pass our adapter to our spinner variable
        spinner.setAdapter(adapter);

        //this makes our spinner react to clicks
        spinner.setOnItemSelectedListener(this);

    }

    //this method is from the implemented interface and determines what happens once an item is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //casts the variable selected to a TextView object by finding it's id
        selected = findViewById(R.id.selectedPresetText);
        //same as above just for a button instead
        goTo = findViewById(R.id.continueTo);
        //sets a specific text in the textView and make the button disappear when position is 0
        if (position == 0) {
            selected.setText(R.string.SessionInputPresetNone);
            selected.setVisibility(View.VISIBLE);
            goTo.setVisibility(View.GONE);
            sessionInputD.setVisibility(View.GONE);
            sessionInputT.setVisibility(View.GONE);
        }
        //the 6 following if statements do the same sets a specific text in the textView and makes the button appear when position is 1-6
        else if (position == 1) {
            selected.setText(R.string.SessionInputPresetSelection1);
            selected.setVisibility(View.VISIBLE);
            goTo.setVisibility(View.VISIBLE);
            sessionInputD.setVisibility(View.GONE);
            sessionInputT.setVisibility(View.GONE);
            // set variables to be put extra
            inputTargetDistance = "500";
            inputTargetTime = "20:00";
        }
        else if (position == 2) {
            selected.setText(R.string.SessionInputPresetSelection2);
            selected.setVisibility(View.VISIBLE);
            goTo.setVisibility(View.VISIBLE);
            sessionInputD.setVisibility(View.GONE);
            sessionInputT.setVisibility(View.GONE);
            inputTargetDistance = "1000";
            inputTargetTime = "30:00";
        }
        else if (position == 3) {
            selected.setText(R.string.SessionInputPresetSelection3);
            selected.setVisibility(View.VISIBLE);
            goTo.setVisibility(View.VISIBLE);
            sessionInputD.setVisibility(View.GONE);
            sessionInputT.setVisibility(View.GONE);
        }
        else if (position == 4) {
            selected.setText(R.string.SessionInputPresetSelection4);
            selected.setVisibility(View.VISIBLE);
            goTo.setVisibility(View.VISIBLE);
            sessionInputD.setVisibility(View.GONE);
            sessionInputT.setVisibility(View.GONE);
        }
        else if (position == 5) {
            selected.setText(R.string.SessionInputPresetSelection5);
            selected.setVisibility(View.VISIBLE);
            goTo.setVisibility(View.VISIBLE);
            sessionInputD.setVisibility(View.GONE);
            sessionInputT.setVisibility(View.GONE);
        }
        else if (position == 6) {
            selected.setText(R.string.SessionInputPresetSelection6);
            selected.setVisibility(View.VISIBLE);
            goTo.setVisibility(View.VISIBLE);
            sessionInputD.setVisibility(View.GONE);
            sessionInputT.setVisibility(View.GONE);
        }
        //makes the textView disappear and makes the TextInputLayouts appear
        else if (position == 7) {
            selected.setVisibility(View.GONE);
            goTo.setVisibility(View.VISIBLE);
            sessionInputD.setVisibility(View.VISIBLE);
            sessionInputT.setVisibility(View.VISIBLE);
        }
    }

    //this method is from the implemented interface and determines what happens when nothing is selected
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //missing a method here saying if this boolean is true pass preset to next activity and if false
    //pass custom to next activity

    //this method validates whether or not we can move to the next activity
    //and makes the app go to the sessions page once continue is clicked
    public void goToSessionFinal(View view) {
        //if(sessionInputD.is) {

        //}
        Intent intent = new Intent(getApplicationContext(),Session.class);
        // put extra the distance and time chosen
        intent.putExtra("EXTRA_DISTANCE", inputTargetDistance);
        intent.putExtra("EXTRA_TIME", inputTargetTime);
        startActivity(intent);
    }
}
