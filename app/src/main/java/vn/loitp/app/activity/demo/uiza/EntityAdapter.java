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
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;

public class EntityAdapter extends RecyclerView.Adapter<EntityAdapter.MovieViewHolder> {

    public interface Callback {
        public void onClick(Data data, int position);

        public void onLongClick(Data data, int position);

        public void onLoadMore();
    }

    private Context context;
    private Callback callback;
    private List<Data> dataList;

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public CardView cv;
        private ImageView ivThumbnail;

        public MovieViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
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
        if (position == dataList.size() - 1) {
            if (callback != null) {
                callback.onLoadMore();
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}