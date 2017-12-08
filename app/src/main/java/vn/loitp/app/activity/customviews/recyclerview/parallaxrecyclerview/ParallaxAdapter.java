package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview;

/**
 * Created by www.muathu@gmail.com on 11/4/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import loitp.basemaster.R;

/**
 * Created by nanquan.lin on 2017/2/4.
 * 主列表适配器
 */

public class ParallaxAdapter extends RecyclerView.Adapter {

    private Context context;

    public ParallaxAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_parallax, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    private class Holder extends RecyclerView.ViewHolder {

        Holder(View itemView) {
            super(itemView);
        }

    }
}