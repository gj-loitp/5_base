package vn.loitp.app.activity.demo.uiza;

/**
 * Created by www.muathu@gmail.com on 12/8/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import loitp.basemaster.R;
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
        public TextView title;
        public LinearLayout rootView;

        public MovieViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            rootView = (LinearLayout) view.findViewById(R.id.root_view);
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
        holder.title.setText(data.getEntityName() + " ->>>>>>>>>>>>>>");

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(data, position);
                }
            }
        });
        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
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