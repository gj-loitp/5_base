package vn.puresolutions.livestarv3.activity.notification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * File created on 8/3/2017.
 *
 * @author Anhdv
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {
    private ArrayList<Notification> lstNotification;
    private Callback callback;
    private Context mContext;

    public NotificationAdapter(Context mContext,ArrayList<Notification> lstNotification, Callback callback) {
        this.lstNotification = lstNotification;
        this.callback = callback;
        this.mContext = mContext;
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_notify, null);
        return new NotificationAdapter.NotificationHolder(v);
    }

    @Override
    public void onBindViewHolder(NotificationHolder holder, int position) {
        LImageUtils.loadImage(holder.sdvIcon, lstNotification.get(position).getAvatar());
        Log.d("Anh",lstNotification.get(position).getAvatar());
        holder.tvTitle.setText(lstNotification.get(position).getTitle());
        holder.tvContent.setText(lstNotification.get(position).getSubcontent());
        holder.tvDate.setText(lstNotification.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return lstNotification != null ? lstNotification.size() : 0;
    }

    public interface Callback {
        public void onClick(View v);
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNotifyScreen_Content)
        TextView tvContent;
        @BindView(R.id.tvNotifyScreen_Title)
        TextView tvTitle;
        @BindView(R.id.tvNotifyScreen_Date)
        TextView tvDate;
        @BindView(R.id.sdvNotifyScreen_Icon)
        SimpleDraweeView sdvIcon;

        public NotificationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onClick(v);
                    }
                }
            });
        }
    }
}
