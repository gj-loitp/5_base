package vn.puresolutions.livestarv3.activity.schedule;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.activity.schedule.calendar.FrmCalendarViewPager;
import vn.puresolutions.livestarv3.activity.schedule.decorator.LEventDecorator;
import vn.puresolutions.livestarv3.activity.schedule.search.FrmSearchInSchedule;
import vn.puresolutions.livestarv3.base.BaseFragment;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LKeyBoardUtil;
import vn.puresolutions.livestarv3.utilities.v3.LLog;

public class ScheduleActivity extends BaseActivity {
    private Button btExit;
    private EditText etSearch;
    private LinearLayout viewGroupCalendar;
    private LinearLayout viewGroupControlCalendar;
    private MaterialCalendarView calendarView;
    //private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private String currentFragmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        customCalendar();
        replaceFragment(new FrmCalendarViewPager(), FrmCalendarViewPager.class.getSimpleName());
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_schedule_base;
    }

    private void customCalendar() {
        //set decoretor for current day by default in xml
        Calendar instance = Calendar.getInstance();
        calendarView.setSelectedDate(instance.getTime());

        //set decoretor for current day by default in custom
        //calendarView.addDecorator(new LTodayDecorator(getActivity()));

        //set min and max day in one month
        /*Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), Calendar.AUGUST, 1);
        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), Calendar.AUGUST, 31);
        calendarView.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();*/

        //add mutiple decorator, no need to use in this case
        /*calendarView.addDecorators(
                new LMySelectorDecorator(getActivity()),
                new LHighlightWeekendsDecorator(),
                oneDayDecorator
        );*/

        //add decoretor for weekend
        //calendarView.addDecorator(new LHighlightWeekendsDecorator());

        //get fake event calendar then decorator its
        new FakeEventCalendarTask().executeOnExecutor(Executors.newSingleThreadExecutor());
    }

    private void replaceFragment(BaseFragment baseFragment, String tag) {
        currentFragmentName = tag;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, baseFragment);
        fragmentTransaction.commit();
    }

    private void findViews() {
        btExit = (Button) findViewById(R.id.bt_exit);
        etSearch = (EditText) findViewById(R.id.et_search);
        viewGroupCalendar = (LinearLayout) findViewById(R.id.view_group_calendar);
        viewGroupControlCalendar = (LinearLayout) findViewById(R.id.view_group_control_calendar);
        calendarView = (MaterialCalendarView) findViewById(R.id.calendar_view);
        //calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                AlertMessage.showSuccess(activity, "setOnDateChangedListener " + date.getDay());
                //oneDayDecorator.setDate(date.getDate());
                //widget.invalidateDecorators();

                //viewGroupControlCalendar.performClick();
            }
        });
        //calendarView.setDateSelected(calendarView.getCurrentDate().getDate(), true);
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        viewGroupControlCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewGroupCalendar.getVisibility() == View.GONE) {
                    viewGroupControlCalendar.setEnabled(false);
                    viewGroupCalendar.setVisibility(View.VISIBLE);
                    LAnimationUtil.play(viewGroupCalendar, Techniques.FadeIn, new LAnimationUtil.Callback() {
                        @Override
                        public void onCancel() {
                            //do nothing
                        }

                        @Override
                        public void onEnd() {
                            viewGroupControlCalendar.setEnabled(true);
                        }

                        @Override
                        public void onRepeat() {
                            //do nothing
                        }

                        @Override
                        public void onStart() {
                            //do nothing
                        }
                    });
                } else {
                    viewGroupControlCalendar.setEnabled(false);
                    LAnimationUtil.play(viewGroupCalendar, Techniques.FadeOut, new LAnimationUtil.Callback() {
                        @Override
                        public void onCancel() {
                            //do nothing
                        }

                        @Override
                        public void onEnd() {
                            viewGroupCalendar.setVisibility(View.GONE);
                            viewGroupControlCalendar.setEnabled(true);
                        }

                        @Override
                        public void onRepeat() {
                            //do nothing
                        }

                        @Override
                        public void onStart() {
                            //do nothing
                        }
                    });
                }
            }
        });
        etSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (etSearch.getText().toString().isEmpty()) {
                        AlertMessage.showSuccess(activity, getString(R.string.pls_type_sth_in_search_view));
                    } else {
                        //hide keyboard
                        LKeyBoardUtil.toggle(activity);
                        //hide calendar if visible
                        if (viewGroupCalendar.getVisibility() == View.VISIBLE) {
                            viewGroupControlCalendar.performClick();
                        }

                        AlertMessage.showSuccess(activity, "onEditorAction: " + etSearch.getText().toString());
                        //handle below
                        replaceFragment(new FrmSearchInSchedule(), FrmSearchInSchedule.class.getSimpleName());
                    }
                    return true;
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    if (!currentFragmentName.equals(FrmCalendarViewPager.class.getSimpleName())) {
                        replaceFragment(new FrmCalendarViewPager(), FrmCalendarViewPager.class.getSimpleName());
                        LLog.d(TAG, "replace");
                    } else {
                        LLog.d(TAG, "no replace");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });
    }

    private class FakeEventCalendarTask extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                calendar.add(Calendar.DATE, 5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);
            calendarView.addDecorator(new LEventDecorator(Color.RED, calendarDays));
        }
    }
}
