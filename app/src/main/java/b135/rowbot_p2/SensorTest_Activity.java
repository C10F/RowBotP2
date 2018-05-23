package b135.rowbot_p2;

import android.content.Context;
import android.hardware.*;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.BarGraphSeries;

import java.util.Date;


public class SensorTest_Activity extends AppCompatActivity implements SensorEventListener {

    private TextView xText, yText, zText;
    private Sensor mySensor;
    private SensorManager SM;
    private LineGraphSeries values;
    private DataPoint[] displayValues = new DataPoint[20];                                                              // the array that holds graph values
    float xCounter;
    int mass = 35;

    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensortest);

        // Create our Sensor Manager
        SM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER);

        // Register sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Assign TextView
        xText = (TextView) findViewById(R.id.xText);
        yText = (TextView) findViewById(R.id.yText);
        zText = (TextView) findViewById(R.id.zText);

        graph = findViewById(R.id.graph);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(100);
        graph.getViewport().setMaxX(30);

        for (int i = 0; i<displayValues.length;i++){
         displayValues[i] = new DataPoint(0,0);
        }
        xCounter = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //xText.setText("X: " + event.values[0]);
        //yText.setText("Y: " + event.values[1]);
        //zText.setText("Z: " + event.values[2]);
        float valueX = event.values[0];
        float valueY = event.values[1];
            /*
        if (event.values[0] < 0){
            valueX = valueX * (-1);
        }
        if(event.values[1] < 0){
            valueY = valueY * (-1);
        }
            */

        // NOTE: here, we want to make sure to check that if the values are negative, do *(-1) to revert them. we want negative G's to be displayed the same i think.

        updateValues(valueX, valueY);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }

    public void updateValues(float event1, float event2){
     // temporary place to hold previous values
     DataPoint[] tempValues = new DataPoint[20];

     for (int i=0; i<displayValues.length-1;i++){
         // make every spot equal to the one above from displayValues
        tempValues[i] = displayValues[i+1];
     }
     // finally add new data to last spot
     tempValues[19] = new DataPoint(new Date().getSeconds(),(mass*event2));
     // arbitrary 30 limit should be refactored to something else.
     //if(xCounter < 30){
     //xCounter++;
     //}
     //invalidate does not remove the line completely.. we need another way to reset the graph.
     //else if (xCounter == 30){
     //graph.invalidate();
     //for (int i = 0; i<tempValues.length;i++){
     //    tempValues[i] = new DataPoint(0,0);
     //   }
     //   xCounter = 0;

     //}
     // make our temporary array the actual one, and display it IF there is a change in the last spot from initial 0.
     displayValues = tempValues;
     graph.invalidate();
     values = new LineGraphSeries<>(displayValues);
     graph.addSeries(values);
    }
}
