package com.function.youtubeparser.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utilities.LImageUtil;
import com.core.utilities.LScreenUtil;
import com.core.utilities.LUIUtil;
import com.function.youtubeparser.models.utubechannel.UItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import loitp.core.R;

public class UtubeChannelAdapter extends RecyclerView.Adapter<UtubeChannelAdapter.UItemViewHolder> {
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    private int holderHeight;

    public interface Callback {
        void onClick(UItem uItem, int position);

        void onLongClick(UItem uItem, int position);

        void onLoadMore();
    }

    private Context context;
    private Callback callback;
    private List<UItem> uItemList;

    public class UItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageView iv;
        public CardView cv;

        public UItemViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            iv = view.findViewById(R.id.iv);
            cv = view.findViewById(R.id.cv);
            if (holderHeight != 0) {
                cv.getLayoutParams().height = holderHeight;
                cv.requestLayout();
            }
        }
    }

    public UtubeChannelAdapter(Context context, List<UItem> uItemList, Callback callback) {
        this.context = context;
        this.uItemList = uItemList;
        this.callback = callback;
        int screenW = LScreenUtil.INSTANCE.getScreenWidth();
        holderHeight = screenW / 2;
    }

    @NotNull
    @Override
    public UItemViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_uitem, parent, false);
        return new UItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull UItemViewHolder holder, final int position) {
        if (position % 2 == 0) {
            LUIUtil.setMarginsDp(holder.cv, 10, 5, 5, 5);
        } else {
            LUIUtil.setMarginsDp(holder.cv, 5, 5, 10, 5);
        }
        final UItem uItem = uItemList.get(position);
        holder.tvName.setText(uItem.getName());
        LImageUtil.INSTANCE.load(context, uItem.getImg(), holder.iv);
        holder.cv.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClick(uItem, position);
            }
        });
        holder.cv.setOnLongClickListener(v -> {
            if (callback != null) {
                callback.onLongClick(uItem, position);
            }
            return true;
        });
        if (position == uItemList.size() - 1) {
            if (callback != null) {
                callback.onLoadMore();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (uItemList == null) {
            return 0;
        }
        return uItemList.size();
    }
}