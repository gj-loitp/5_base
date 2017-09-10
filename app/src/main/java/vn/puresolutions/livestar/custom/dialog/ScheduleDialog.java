package vn.puresolutions.livestar.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.ScheduleAdapter;
import vn.puresolutions.livestar.core.api.model.Schedule;
import vn.puresolutions.livestar.decoration.base.ListVerticalLayoutDecoration;
import vn.puresolutions.livestar.room.RoomCenter;
import vn.puresolutions.livestarv3.utilities.old.DateUtils;

/**
 * @author hoangphu
 * @since 9/25/16
 */
public class ScheduleDialog extends Dialog {

    @BindView(R.id.dialogSchedule_vwCalendar)
    MaterialCalendarView vwCalendar;
    @BindView(R.id.dialogSchedule_rclSchedule)
    RecyclerView rclSchedule;

    private ScheduleAdapter adapter;

    public ScheduleDialog(Context context) {
        super(context, R.style.full_screen_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_schedule);
        ButterKnife.bind(this);

        assert getWindow() != null;
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

        vwCalendar.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);

        adapter = new ScheduleAdapter();
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rclSchedule.addItemDecoration(new ListVerticalLayoutDecoration(getContext(), R.dimen.view_padding_medium));
        rclSchedule.setLayoutManager(layout);
        rclSchedule.setHasFixedSize(true);
        rclSchedule.setAdapter(adapter);
    }

    @Override
    public void show() {
        super.show();

        if (adapter.getItems().size() <= 0) {
            // load data
            List<Schedule> schedules = RoomCenter.getInstance().schedules;
            if (schedules != null && schedules.size() > 0) {
                // get first day in list schedule greater then today
                Schedule schedule;
                int nextShowTime = -1;
                Date currentDate = new Date();

                for (int i = 0; i < schedules.size(); i++) {
                    schedule = schedules.get(i);
                    Date date = DateUtils.stringToDate(schedule.getDate(),
                            getContext().getString(R.string.date_format));
                    if (date != null && nextShowTime == -1 &&
                            (currentDate.equals(date) || currentDate.before(date))) {
                        nextShowTime = i;
                    }
                    vwCalendar.setDateSelected(date, true);
                    adapter.add(schedule);
                }

                adapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick(R.id.dialogSchedule_imgClose)
    void closeDialog() {
        dismiss();
    }
}
