package vn.loitp.app.activity.customviews.menu.residemenu;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFragment;

import java.util.ArrayList;

import vn.loitp.app.R;

@LayoutId(R.layout.reside_menu_calendar)
@LogTag("DatabaseFirebaseSignInActivity")
public class CalendarFragment extends BaseFragment {
    private ListView listView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = getFrmRootView().findViewById(R.id.listView);
        initView();
    }

    private void initView() {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.view_row_test_retrofit,
                getCalendarData());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> Toast.makeText(getActivity(), "Clicked item!", Toast.LENGTH_LONG).show());
    }

    private ArrayList<String> getCalendarData() {
        final ArrayList<String> calendarList = new ArrayList<>();
        calendarList.add("New Year's Day");
        calendarList.add("St. Valentine's Day");
        calendarList.add("Easter Day");
        calendarList.add("April Fool's Day");
        calendarList.add("Mother's Day");
        calendarList.add("Memorial Day");
        calendarList.add("National Flag Day");
        calendarList.add("Father's Day");
        calendarList.add("Independence Day");
        calendarList.add("Labor Day");
        calendarList.add("Columbus Day");
        calendarList.add("Halloween");
        calendarList.add("All Soul's Day");
        calendarList.add("Veterans Day");
        calendarList.add("Thanksgiving Day");
        calendarList.add("Election Day");
        calendarList.add("Forefather's Day");
        calendarList.add("Christmas Day");
        return calendarList;
    }

}
