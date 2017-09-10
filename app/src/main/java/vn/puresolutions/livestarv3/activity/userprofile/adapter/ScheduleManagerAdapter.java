package vn.puresolutions.livestarv3.activity.userprofile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.userprofile.model.ScheduleModel;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.view.LTextViewHorizontal;

/**
 * File created on 8/9/2017.
 *
 * @author anhdv
 */

public class ScheduleManagerAdapter extends RecyclerView.Adapter<ScheduleManagerAdapter.IdolEditScheduleHolder> {
    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private ArrayList<ScheduleModel> lstSchedule;
    private ScheduleManagerAdapter.Callback callback;

    public ScheduleManagerAdapter(Context mContext, ArrayList<ScheduleModel> lstSchedule, Callback callback) {
        this.mContext = mContext;
        this.lstSchedule = lstSchedule;
        this.callback = callback;
    }

    @Override
    public IdolEditScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_edit_schedule, null);
        return new ScheduleManagerAdapter.IdolEditScheduleHolder(v);
    }

    @Override
    public void onBindViewHolder(IdolEditScheduleHolder holder, final int position) {
       /* ScheduleItem scheduleItem = lstSchedule.get(position);
        if (scheduleItem.getStart() != null) {
            if (scheduleItem.getStart().length() > 10) {
                String startDate = DateUtils.convertFormatDate(scheduleItem.getStart(), "yyyy-MM-dd'T'hh:mm:ss", "dd/MM/yyyy");
                String hour = DateUtils.convertFormatDate(scheduleItem.getStart(), "yyyy-MM-dd'T'hh:mm:ss", "hh:mm a");
                Log.d("ScheduleManagerAdapter", "date: " + startDate);
                Log.d("ScheduleManagerAdapter", "hour: " + hour);
                if (startDate != null) {
                    holder.edtDate.setText(startDate);
                }
                if (hour != null) {
                    holder.edtTime.setText(hour);
                }
            } else {
                String startDate = DateUtils.convertFormatDate(scheduleItem.getStart(), "yyyy-MM-dd", "dd/MM/yyyy");
                holder.edtDate.setText(startDate);
            }
        }*/
        holder.lthSave.setText(mContext.getString(R.string.saved));
        holder.lthSave.setImageSize(10);
        holder.lthSave.setImage(R.drawable.tick);
        holder.lthSave.setBackground(R.drawable.bt_green);
        holder.lthSave.setVisibility(View.INVISIBLE);
        ScheduleModel scheduleModel = lstSchedule.get(position);
        if (scheduleModel != null) {
            holder.edtDate.setText(scheduleModel.getDate());
            holder.edtTime.setText(scheduleModel.getTime());
        }
        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDelete(scheduleModel, position);
            }
        });
        holder.imvEditeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onUpDateDate(holder, scheduleModel, position);
            }
        });
        holder.imvEditTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onUpDateTime(holder, scheduleModel, position, holder.edtDate.getText().toString());
            }
        });

        if (position == 0) {
            holder.llSpace.setVisibility(View.VISIBLE);
        } else {
            holder.llSpace.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (null != lstSchedule ? lstSchedule.size() : 0);
    }

    public interface Callback {
        public void onDelete(ScheduleModel scheduleItem, int pos);

        public void onUpDateDate(IdolEditScheduleHolder idolEditScheduleHolder, ScheduleModel scheduleItem, int pos);

        public void onUpDateTime(IdolEditScheduleHolder idolEditScheduleHolder, ScheduleModel scheduleItem, int pos, String date);
    }

    public class IdolEditScheduleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imvScheduleScreen_EditDate)
        ImageView imvEditeDate;
        @BindView(R.id.imvScheduleScreen_EditTime)
        ImageView imvEditTime;
        @BindView(R.id.tvEditScheduleScreen_Date)
        TextView edtDate;
        @BindView(R.id.tvEditScheduleScreen_Time)
        TextView edtTime;
        @BindView(R.id.imvScheduleScreen_Delete)
        ImageView imvDelete;
        @BindView(R.id.lthScheduleScreen_Saved)
        LTextViewHorizontal lthSave;
        @BindView(R.id.ll_space)
        LinearLayout llSpace;

        public IdolEditScheduleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void playFadeSaved() {
            if (lthSave != null) {
                lthSave.setVisibility(View.VISIBLE);
                LAnimationUtil.play(lthSave, Techniques.FadeIn, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }

                    @Override
                    public void onEnd() {
                        LUIUtil.setDelay(1500, new LUIUtil.DelayCallback() {
                            @Override
                            public void doAfter(int mls) {
                                LAnimationUtil.play(lthSave, Techniques.FadeOut, new LAnimationUtil.Callback() {
                                    @Override
                                    public void onCancel() {
                                        //do nothing
                                    }

                                    @Override
                                    public void onEnd() {
                                        lthSave.setVisibility(View.INVISIBLE);
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
                        });
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
    }
}
