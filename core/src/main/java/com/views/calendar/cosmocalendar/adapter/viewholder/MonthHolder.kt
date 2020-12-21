package com.views.calendar.cosmocalendar.adapter.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.R;
import com.views.calendar.cosmocalendar.adapter.DaysAdapter;
import com.views.calendar.cosmocalendar.model.Month;
import com.views.calendar.cosmocalendar.settings.SettingsManager;
import com.views.calendar.cosmocalendar.view.MonthView;

public class MonthHolder extends RecyclerView.ViewHolder {

    private final LinearLayout llMonthHeader;
    private final TextView tvMonthName;
    private final View viewLeftLine;
    private final View viewRightLine;
    private final MonthView monthView;
    private final SettingsManager appearanceModel;

    public MonthHolder(View itemView, SettingsManager appearanceModel) {
        super(itemView);
        llMonthHeader = itemView.findViewById(R.id.ll_month_header);
        monthView = itemView.findViewById(R.id.month_view);
        tvMonthName = itemView.findViewById(R.id.tv_month_name);
        viewLeftLine = itemView.findViewById(R.id.view_left_line);
        viewRightLine = itemView.findViewById(R.id.view_right_line);
        this.appearanceModel = appearanceModel;
    }

    public void setDayAdapter(DaysAdapter adapter) {
        getMonthView().setAdapter(adapter);
    }

    public void bind(Month month) {
        tvMonthName.setText(month.getMonthName());
        tvMonthName.setTextColor(appearanceModel.getMonthTextColor());

        viewLeftLine.setVisibility(appearanceModel.getCalendarOrientation() == OrientationHelper.HORIZONTAL ? View.INVISIBLE : View.VISIBLE);
        viewRightLine.setVisibility(appearanceModel.getCalendarOrientation() == OrientationHelper.HORIZONTAL ? View.INVISIBLE : View.VISIBLE);
        llMonthHeader.setBackgroundResource(appearanceModel.getCalendarOrientation() == OrientationHelper.HORIZONTAL ? R.drawable.border_top_bottom : 0);

        monthView.initAdapter(month);
    }

    public MonthView getMonthView() {
        return monthView;
    }
}
