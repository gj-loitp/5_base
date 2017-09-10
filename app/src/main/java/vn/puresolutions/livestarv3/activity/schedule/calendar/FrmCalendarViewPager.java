package vn.puresolutions.livestarv3.activity.schedule.calendar;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseFragment;
import vn.puresolutions.livestarv3.utilities.v3.DateUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmCalendarViewPager extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private CalendarViewPagerAdapter calendarViewPagerAdapter;
    private ViewPager viewPager;
    private ArrayList<String> dayList = new ArrayList<>();
    private TabLayout tabLayout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_calendar, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        calendarViewPagerAdapter = new CalendarViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(calendarViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        genListCalendar();
        return view;
    }

    private void genListCalendar() {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDate = calendar.get(Calendar.DAY_OF_MONTH);
        LLog.d(TAG, "currentMonth " + currentMonth);
        LLog.d(TAG, "currentDate " + currentDate);

        List<String> tmpCalendarList = DateUtils.genListDayOfMonth(currentMonth + 1);
        if (tmpCalendarList != null && !tmpCalendarList.isEmpty()) {
            dayList.clear();
            dayList.addAll(tmpCalendarList);
        }
        if (!dayList.isEmpty() && calendarViewPagerAdapter != null) {
            calendarViewPagerAdapter.notifyDataSetChanged();
            viewPager.setCurrentItem(currentDate - 1);
        }
        //LLog.d(TAG, "dayList size: " + dayList.size());
    }

    private class CalendarViewPagerAdapter extends FragmentPagerAdapter {

        public CalendarViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new FrmSchedulePage();
        }

        @Override
        public int getCount() {
            return dayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return dayList.get(position);
        }
    }
}

