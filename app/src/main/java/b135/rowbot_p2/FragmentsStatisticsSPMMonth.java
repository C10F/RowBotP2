package b135.rowbot_p2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.Calendar;

public class FragmentsStatisticsSPMMonth extends Fragment {
    private static final String TAG = "calendarView";

    private CalendarView mCalendarView;
    private TextView mTextView;
    private String spm;
    Calendar c = Calendar.getInstance();
    int dayMonth = c.get(Calendar.DAY_OF_YEAR);


    public FragmentsStatisticsSPMMonth() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragments_statistics_spm_month, container, false);

        mCalendarView = view.findViewById(R.id.calendarSPMMonth);
        mTextView = view.findViewById(R.id.resultsSPMText);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                spm = Utility.readFromFile(dayMonth+"SPM.txt",getActivity());
                String results = "SPM: " + spm + "\n\n(Strokes pr. minute)";
                Log.d(TAG, "onSelectedDayChange" + results);

                mTextView.setVisibility(View.VISIBLE);
                mTextView.setText(results);

            }
        });

        return view;
    }

    public void updateLabel(){
        spm = Utility.readFromFile(dayMonth+"SPM.txt",getActivity());
        String results = "SPM: " + spm + "\n\n(Strokes pr. minute)";
        Log.d(TAG, "onSelectedDayChange" + results);

        mTextView.setVisibility(View.VISIBLE);
        mTextView.setText(results);
    }

}
