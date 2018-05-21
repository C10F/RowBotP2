package b135.rowbot_p2;

import android.content.Context;
import android.hardware.*;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.BarGraphSeries;

import java.util.ArrayList;


public class SensorTest_Activity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "lineGraph";

    //private TextView xText, yText, zText;
    private Sensor mySensor;
    private SensorManager SM;
    private LineGraphSeries values;
    private DataPoint[] displayValues = new DataPoint[20];                                                              // the array that holds graph values
    float xCounter;

    GraphView graph;

    private LineChart mLineChart;
    ArrayList<Entry> yValues = new ArrayList<>();
    ArrayList<String> xTime = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensortest);

        // Create our Sensor Manager
        SM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Assign TextView
        //xText = (TextView) findViewById(R.id.xText);
        //yText = (TextView) findViewById(R.id.yText);
        //zText = (TextView) findViewById(R.id.zText);

        //graph = findViewById(R.id.graph);
        mLineChart = findViewById(R.id.graph);

        mLineChart.setTouchEnabled(false);
        mLineChart.setDragEnabled(false);
        mLineChart.setScaleEnabled(false);
        mLineChart.setPinchZoom(false);
        //lineData.setValueTextSize(12);
        mLineChart.setDescription(null);

        yValues = new ArrayList<>();
        for (int i = 0 ; i < displayValues.length ; i++) {
            yValues.add(new Entry(0,i));
        }

        xTime = new ArrayList<>();
        for (int i = 0 ; i < displayValues.length ; i++) {
            xTime.add(String.valueOf(i));
        }

        LineDataSet lineDataSet = new LineDataSet(yValues, "Data set 1");

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet);

        LineData lineData = new LineData(xTime,dataSets);

        mLineChart.setData(lineData);

        /*for (int i = 0; i<displayValues.length;i++){
         displayValues[i] = new DataPoint(0,0);
        }
        xCounter = 0;*/
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //xText.setText("X: " + event.values[0]);
        //yText.setText("Y: " + event.values[1]);
        //zText.setText("Z: " + event.values[2]);


        // NOTE: here, we want to make sure to check that if the values are negative, do *(-1) to revert them. we want negative G's to be displayed the same i think.
        updateValues(event.values[0], event.values[1]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }

    public void updateValues(float event1, float event2){
        ArrayList<Entry> tempYEntry = new ArrayList<>();
        for (int i = 0 ; i < yValues.size()-1 ; i++) {
            tempYEntry.add(i, yValues.get(i+1));
        }
        tempYEntry.add(new Entry(event1,19));

        yValues = tempYEntry;

        LineDataSet lineDataSet = new LineDataSet(yValues, "Data set 1");

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet);

        LineData lineData = new LineData(xTime,dataSets);
        mLineChart.invalidate();
        mLineChart.setData(lineData);

     // temporary place to hold previous values
     /*DataPoint[] tempValues = new DataPoint[20];

     for (int i=0; i<displayValues.length-1;i++){
         // make every spot equal to the one above from displayValues
        tempValues[i] = displayValues[i+1];
     }
     // finally add new data to last spot
     tempValues[19] = new DataPoint(xCounter,event2);
     // arbitrary 30 limit should be refactored to something else.
     if(xCounter < 30){
     xCounter++;
     }
     //invalidate does not remove the line completely.. we need another way to reset the graph.
     else if (xCounter == 30){
     graph.invalidate();
     for (int i = 0; i<tempValues.length;i++){
         tempValues[i] = new DataPoint(0,0);
        }
        xCounter = 0;

     }
     // make our temporary array the actual one, and display it IF there is a change in the last spot from initial 0.
     displayValues = tempValues;
     values = new LineGraphSeries<>(displayValues);
     if(displayValues[19] != new DataPoint(0,0)){
     graph.addSeries(values);
     }*/

    }
}
