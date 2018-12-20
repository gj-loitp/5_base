package vn.loitp.app.activity.demo.uiza;

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
import vn.loitp.core.utilities.LDateUtils;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;

public class EntityAdapter extends RecyclerView.Adapter<EntityAdapter.MovieViewHolder> {

    public interface Callback {
        public void onClick(Data data, int position);

        public void onLongClick(Data data, int position);

        public void onLoadMore();

        public void onScrollUp();

        public void onScrollDown();
    }

    private Context context;
    private Callback callback;
    private List<Data> dataList;
    private int lastItemPosition = -1;
    private boolean onScrollDown;

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvUpdateAt;
        public CardView cv;
        private ImageView ivThumbnail;
        private View viewSpaceTop;

        public MovieViewHolder(View view) {
            super(view);
            viewSpaceTop = (View) view.findViewById(R.id.view_space_top);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvUpdateAt = (TextView) view.findViewById(R.id.tv_update_at);
            cv = (CardView) view.findViewById(R.id.cv);
            ivThumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);
        }
    }

    public EntityAdapter(Context context, List<Data> dataList, Callback callback) {
        this.context = context;
        this.dataList = dataList;
        this.callback = callback;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.uiza_entity_row, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Data data = dataList.get(position);
        holder.tvName.setText(data.getName());
        LUIUtil.setTextShadow(holder.tvName);
        holder.tvUpdateAt.setText(LDateUtils.getDateWithoutTime(data.getUpdatedAt()));
        LUIUtil.setTextShadow(holder.tvUpdateAt);
        LImageUtil.load(context, data.getThumbnail(), holder.ivThumbnail, LStoreUtil.getRandomColorLight());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(data, position);
                }
            }
        });
        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (callback != null) {
                    callback.onLongClick(data, position);
                }
                return true;
            }
        });
        if (position == 0) {
            holder.viewSpaceTop.setVisibility(View.VISIBLE);
        } else {
            holder.viewSpaceTop.setVisibility(View.GONE);
        }
        if (position == dataList.size() - 1) {
            if (callback != null) {
                callback.onLoadMore();
            }
        }
        if (position > lastItemPosition) {
            if (!onScrollDown) {
                onScrollDown = true;
                if (callback != null) {
                    callback.onScrollDown();
                }
            }
        } else {
            if (onScrollDown) {
                onScrollDown = false;
                if (callback != null) {
                    callback.onScrollUp();
                }
            }
        }
        lastItemPosition = position;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}