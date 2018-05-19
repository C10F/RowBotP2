package b135.rowbot_p2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;


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
        // make a method that takes a string and does this to it, it will be a lot neater...
        /*
        Utility.writeToFile("0","tsMonday.txt", getActivity());
        Utility.writeToFile("0","tsTuesday.txt", getActivity());
        Utility.writeToFile("0","tsWednesday.txt", getActivity());
        Utility.writeToFile("0","tsThursday.txt", getActivity());
        Utility.writeToFile("0","tsFriday.txt", getActivity());
        Utility.writeToFile("0","tsSaturday.txt", getActivity());
        Utility.writeToFile("0","tsSunday.txt", getActivity());
        */

        weekValues[0] = Float.parseFloat(divideString(Utility.readFromFile("tsMonday.txt",getActivity())));
        weekValues[1] = Float.parseFloat(divideString(Utility.readFromFile("tsTuesday.txt",getActivity())));
        weekValues[2] = Float.parseFloat(divideString(Utility.readFromFile("tsWednesday.txt",getActivity())));
        weekValues[3] = Float.parseFloat(divideString(Utility.readFromFile("tsThursday.txt",getActivity())));
        weekValues[4] = Float.parseFloat(divideString(Utility.readFromFile("tsFriday.txt",getActivity())));
        weekValues[5] = Float.parseFloat(divideString(Utility.readFromFile("tsSaturday.txt",getActivity())));
        weekValues[6] = Float.parseFloat(divideString(Utility.readFromFile("tsSunday.txt",getActivity())));

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

    public String divideString(String weekday) {
        String returnString;

        String[] result = weekday.split(":");
        returnString = result[0];
        return returnString;
    }

}
