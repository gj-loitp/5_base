package vn.puresolutions.livestar.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.base.RecyclerAdapter;
import vn.puresolutions.livestar.core.api.model.Schedule;
import vn.puresolutions.livestarv3.utilities.old.DateUtils;

/**
 * @author hoangphu
 * @since 10/2/16
 */

public class ScheduleAdapter extends RecyclerAdapter<Schedule, ScheduleAdapter.ViewHolder> {

    @Override
    protected int getLayoutItem() {
        return R.layout.list_item_schedule;
    }

    @Override
    protected void updateView(Schedule model, ViewHolder viewHolder, int position) {
        Context context = viewHolder.tvDate.getContext();
        String dateOnAir = DateUtils.convertFormatDate(model.getDate(), context.getString(R.string.date_format),
                context.getString(R.string.schedule_date_format));
        viewHolder.tvDate.setText(dateOnAir);
        viewHolder.tvStartEnd.setText(model.getStart() + "-" + model.getEnd());
    }

    @Override
    protected ViewHolder createViewHolder(View container) {
        return new ViewHolder(container);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.scheduleItem_tvDate)
        TextView tvDate;
        @BindView(R.id.scheduleItem_tvStartEnd)
        TextView tvStartEnd;
        @BindView(R.id.scheduleItem_imgAlarm)
        ImageView imgAlarm;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
