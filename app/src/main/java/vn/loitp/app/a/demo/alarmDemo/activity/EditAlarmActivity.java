package vn.loitp.app.a.demo.alarmDemo.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.loitp.annotation.IsFullScreen;
import com.loitp.annotation.LogTag;
import com.loitp.core.base.BaseFontActivity;
import com.loitp.core.utilities.LActivityUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

import vn.loitp.R;
import vn.loitp.app.a.demo.alarmDemo.model.Alarm;
import vn.loitp.app.a.demo.alarmDemo.model.DateTime;

@LogTag("EditAlarmActivity")
@IsFullScreen(false)
public class EditAlarmActivity extends BaseFontActivity {
    private EditText mTitle;
    private Button mDateButton;
    private Button mTimeButton;

    private Alarm mAlarm;
    private DateTime mDateTime;

    private GregorianCalendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    static final int DAYS_DIALOG_ID = 2;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_alarm_edit;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mTitle = findViewById(R.id.title);
        CheckBox mAlarmEnabled = findViewById(R.id.alarm_checkbox);
        Spinner mOccurence = findViewById(R.id.occurence_spinner);
        mDateButton = findViewById(R.id.date_button);
        mTimeButton = findViewById(R.id.time_button);

        mAlarm = new Alarm();
        mAlarm.fromIntent(getIntent());

        mDateTime = new DateTime(this);

        mTitle.setText(mAlarm.getTitle());
        mTitle.addTextChangedListener(mTitleChangedListener);

        mOccurence.setSelection(mAlarm.getOccurence());
        mOccurence.setOnItemSelectedListener(mOccurenceSelectedListener);

        mAlarmEnabled.setChecked(mAlarm.getEnabled());
        mAlarmEnabled.setOnCheckedChangeListener(mAlarmEnabledChangeListener);

        mCalendar = new GregorianCalendar();
        mCalendar.setTimeInMillis(mAlarm.getDate());
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);

        updateButtons();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (DATE_DIALOG_ID == id)
            return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        else if (TIME_DIALOG_ID == id)
            return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, mDateTime.is24hClock());
        else if (DAYS_DIALOG_ID == id)
            return DaysPickerDialog();
        else
            return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        if (DATE_DIALOG_ID == id)
            ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
        else if (TIME_DIALOG_ID == id)
            ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
    }

    public void onDateClick(View view) {
        if (Alarm.ONCE == mAlarm.getOccurence())
            showDialog(DATE_DIALOG_ID);
        else if (Alarm.WEEKLY == mAlarm.getOccurence())
            showDialog(DAYS_DIALOG_ID);
    }

    public void onTimeClick(View view) {
        showDialog(TIME_DIALOG_ID);
    }

    public void onDoneClick(View view) {
        Intent intent = new Intent();

        mAlarm.toIntent(intent);
        setResult(RESULT_OK, intent);
        finish();//correct
        LActivityUtil.tranIn(this);
    }

    public void onCancelClick(View view) {
        setResult(RESULT_CANCELED, null);
        finish();//correct
        LActivityUtil.tranIn(this);
    }

    private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            mCalendar = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute);
            mAlarm.setDate(mCalendar.getTimeInMillis());

            updateButtons();
        }
    };

    private final TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;

            mCalendar = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute);
            mAlarm.setDate(mCalendar.getTimeInMillis());

            updateButtons();
        }
    };

    private final TextWatcher mTitleChangedListener = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            mAlarm.setTitle(mTitle.getText().toString());
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    private final AdapterView.OnItemSelectedListener mOccurenceSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            mAlarm.setOccurence(position);
            updateButtons();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private final CompoundButton.OnCheckedChangeListener mAlarmEnabledChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mAlarm.setEnabled(isChecked);
        }
    };

    private void updateButtons() {
        if (Alarm.ONCE == mAlarm.getOccurence())
            mDateButton.setText(mDateTime.formatDate(mAlarm));
        else if (Alarm.WEEKLY == mAlarm.getOccurence())
            mDateButton.setText(mDateTime.formatDays(mAlarm));
        mTimeButton.setText(mDateTime.formatTime(mAlarm));
    }

    private Dialog DaysPickerDialog() {
        AlertDialog.Builder builder;
        final boolean[] days = mDateTime.getDays(mAlarm);
        final String[] names = mDateTime.getFullDayNames();

        builder = new AlertDialog.Builder(this);

        builder.setTitle("Week days");

        builder.setMultiChoiceItems(names, days, (dialog, whichButton, isChecked) -> {
        });

        builder.setPositiveButton("OK", (dialog, whichButton) -> {
            mDateTime.setDays(mAlarm, days);
            updateButtons();
        });

        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }

}
