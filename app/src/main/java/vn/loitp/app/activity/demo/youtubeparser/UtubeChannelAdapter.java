package vn.loitp.app.activity.demo.youtubeparser;

/**
 * Created by www.muathu@gmail.com on 12/8/2017.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.function.youtubeparser.models.utubechannel.UItem;

public class UtubeChannelAdapter extends RecyclerView.Adapter<UtubeChannelAdapter.UItemViewHolder> {
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    private int holderHeight;

    public interface Callback {
        public void onClick(UItem uItem, int position);

        public void onLongClick(UItem uItem, int position);

        public void onLoadMore();
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
            tvName = (TextView) view.findViewById(R.id.tv_name);
            iv = (ImageView) view.findViewById(R.id.iv);
            cv = (CardView) view.findViewById(R.id.cv);
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
        int screenW = LScreenUtil.getScreenWidth();
        holderHeight = screenW * 12 / 16;
    }

    @Override
    public UItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_uitem, parent, false);
        return new UItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UItemViewHolder holder, int position) {
        UItem uItem = uItemList.get(position);
        holder.tvName.setText(uItem.getName());
        LImageUtil.load(context, uItem.getImg(), holder.iv);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(uItem, position);
                }
            }
        });
        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (callback != null) {
                    callback.onLongClick(uItem, position);
                }
                return true;
            }
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