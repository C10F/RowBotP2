package b135.rowbot_p2;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

public class Sensor extends Activity implements SensorEventListener{

        private TextView xText, yText, zText;
        private android.hardware.Sensor mySensor;
        private SensorManager SM;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Create our Sensor Manager
            SM = (SensorManager)getSystemService(SENSOR_SERVICE);

            // Accelerometer Sensor
            mySensor = SM.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER);

            // Register sensor Listener
            SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

            // Assign TextView
            xText = (TextView)findViewById(R.id.xText);
            yText = (TextView)findViewById(R.id.yText);
            zText = (TextView)findViewById(R.id.zText);
        }

        @Override
        public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {
            // Not in use
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            xText.setText("X: " + event.values[0]);
            yText.setText("Y: " + event.values[1]);
            zText.setText("Z: " + event.values[2]);
        }
}