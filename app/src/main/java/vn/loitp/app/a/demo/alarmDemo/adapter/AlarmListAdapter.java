package vn.loitp.app.a.demo.alarmDemo.adapter;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loitp.core.utilities.LAppResource;

import vn.loitp.R;
import vn.loitp.app.a.demo.alarmDemo.model.Alarm;
import vn.loitp.app.a.demo.alarmDemo.model.DataSource;
import vn.loitp.app.a.demo.alarmDemo.model.DateTime;
import vn.loitp.app.a.demo.alarmDemo.service.AlarmReceiver;

public class AlarmListAdapter extends BaseAdapter {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final DateTime mDateTime;
    private final int mColorOutdated;
    private final int mColorActive;
    private final AlarmManager mAlarmManager;

    public AlarmListAdapter(Context context) {
        mContext = context;
//        DataSource mDataSource = DataSource.getInstance(context);

        mInflater = LayoutInflater.from(context);
        mDateTime = new DateTime(context);

        mColorOutdated = LAppResource.INSTANCE.getColor(R.color.gray);
        mColorActive = LAppResource.INSTANCE.getColor(R.color.red);

        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        dataSetChanged();
    }

    public void save() {
        DataSource.save();
    }

    public void update(Alarm alarm) {
        DataSource.update(alarm);
        dataSetChanged();
    }

    public void updateAlarms() {
        for (int i = 0; i < DataSource.size(); i++)
            DataSource.update(DataSource.get(i));
        dataSetChanged();
    }

    public void add(Alarm alarm) {
        DataSource.add(alarm);
        dataSetChanged();
    }

    public void delete(int index) {
        cancelAlarm(DataSource.get(index));
        DataSource.remove(index);
        dataSetChanged();
    }

    public void onSettingsUpdated() {
        mDateTime.update();
        dataSetChanged();
    }

    public int getCount() {
        return DataSource.size();
    }

    public Alarm getItem(int position) {
        return DataSource.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Alarm alarm = DataSource.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.itemTitle);
            holder.details = convertView.findViewById(R.id.itemDetails);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(alarm.getTitle());
        holder.details.setText(mDateTime.formatDetails(alarm) + (alarm.getEnabled() ? "" : " [disabled]"));

        if (alarm.getOutdated())
            holder.title.setTextColor(mColorOutdated);
        else
            holder.title.setTextColor(mColorActive);

        return convertView;
    }

    private void dataSetChanged() {
        for (int i = 0; i < DataSource.size(); i++)
            setAlarm(DataSource.get(i));

        notifyDataSetChanged();
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void setAlarm(Alarm alarm) {
        PendingIntent sender;
        Intent intent;

        if (alarm.getEnabled() && !alarm.getOutdated()) {
            intent = new Intent(mContext, AlarmReceiver.class);
            alarm.toIntent(intent);
//            sender = PendingIntent.getBroadcast(mContext, (int) alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                sender = PendingIntent.getActivity(mContext, (int) alarm.getId(), intent, PendingIntent.FLAG_MUTABLE);
            } else {
                sender = PendingIntent.getActivity(mContext, (int) alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }

            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getDate(), sender);
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void cancelAlarm(Alarm alarm) {
        PendingIntent sender;
        Intent intent;

        intent = new Intent(mContext, AlarmReceiver.class);
        sender = PendingIntent.getBroadcast(mContext, (int) alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.cancel(sender);
    }

    static class ViewHolder {
        TextView title;
        TextView details;
    }
}
