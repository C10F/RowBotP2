package b135.rowbot_p2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class FragmentsStatisticsTimeWeek extends Fragment {


    public FragmentsStatisticsTimeWeek() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragments_statistics_time_week, container, false);

        GraphView graphView = new GraphView(getActivity());

        BarGraphSeries<DataPoint> values = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 44),
                new DataPoint(1, 88),
                new DataPoint(2, 66),
                new DataPoint(3, 12),
        });

        LinearLayout layout =  view.findViewById(R.id.timeSpentWeekBarchart);
        layout.addView(graphView);
        graphView.addSeries(values);

        return view;
    }

}
