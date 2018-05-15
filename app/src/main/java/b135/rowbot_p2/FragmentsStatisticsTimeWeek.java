package b135.rowbot_p2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

public class FragmentsStatisticsTimeWeek extends Fragment {
    BarChart barChart;
    float[] weekValues = new float[7];

    public FragmentsStatisticsTimeWeek() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragments_statistics_time_week, container, false);

        //Utility.writeToFile("21", "tsMonday.txt", getActivity());
        weekValues[0] = Float.parseFloat(Utility.readFromFile("tsMonday.txt",getActivity()));
        weekValues[1] = Float.parseFloat(Utility.readFromFile("tsTuesday.txt",getActivity()));
        weekValues[2] = Float.parseFloat(Utility.readFromFile("tsWednesday.txt",getActivity()));
        weekValues[3] = Float.parseFloat(Utility.readFromFile("tsThursday.txt",getActivity()));
        weekValues[4] = Float.parseFloat(Utility.readFromFile("tsFriday.txt",getActivity()));
        weekValues[5] = Float.parseFloat(Utility.readFromFile("tsSaturday.txt",getActivity()));
        weekValues[6] = Float.parseFloat(Utility.readFromFile("tsSunday.txt",getActivity()));

        barChart = view.findViewById(R.id.timeSpentWeekBarchart);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(weekValues[0], 0));
        barEntries.add(new BarEntry(weekValues[1], 1));
        barEntries.add(new BarEntry(weekValues[2], 2));
        barEntries.add(new BarEntry(weekValues[3], 3));
        barEntries.add(new BarEntry(weekValues[4], 4));
        barEntries.add(new BarEntry(weekValues[5], 5));
        barEntries.add(new BarEntry(weekValues[6], 6));
        BarDataSet barDataSet = new BarDataSet(barEntries,"Sessions");

        final ArrayList<String> theDays = new ArrayList<>();
        theDays.add("Mon");
        theDays.add("Tue");
        theDays.add("Wed");
        theDays.add("Thu");
        theDays.add("Fri");
        theDays.add("Sat");
        theDays.add("Sun");

        BarData barData = new BarData(theDays, barDataSet);
        barChart.setData(barData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setPinchZoom(true);
        barData.setValueTextSize(12);
        barChart.setDescription(null);

        return view;
    }


}
