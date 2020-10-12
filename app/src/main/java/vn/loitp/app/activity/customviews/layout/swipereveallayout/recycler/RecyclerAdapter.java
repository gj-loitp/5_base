package vn.loitp.app.activity.customviews.layout.swipereveallayout.recycler;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.views.LToast;
import com.views.layout.swipereveallayout.LSwipeRevealLayout;
import com.views.layout.swipereveallayout.ViewBinderHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import vn.loitp.app.R;

public class RecyclerAdapter extends RecyclerView.Adapter {
    private List<String> mDataSet;
    private LayoutInflater mInflater;
    private Context mContext;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();


    public RecyclerAdapter(Context context, List<String> dataSet) {
        mContext = context;
        mDataSet = dataSet;
        mInflater = LayoutInflater.from(context);

        // uncomment if you want to open only one row at a time
        // binderHelper.setOpenOnlyOne(true);
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.item_swipe_reveal_layout_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder h, int position) {
        final ViewHolder holder = (ViewHolder) h;

        if (mDataSet != null && 0 <= position && position < mDataSet.size()) {
            final String data = mDataSet.get(position);

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
            binderHelper.bind(holder.swipeLayout, data);

            // Bind your data here
            holder.bind(data);
        }
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null)
            return 0;
        return mDataSet.size();
    }

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private LSwipeRevealLayout swipeLayout;
        private View frontLayout;
        private View deleteLayout;
        private TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
            frontLayout = itemView.findViewById(R.id.frontLayout);
            deleteLayout = itemView.findViewById(R.id.deleteLayout);
            textView = itemView.findViewById(R.id.text);
        }

        public void bind(final String data) {
            deleteLayout.setOnClickListener(v -> {
                mDataSet.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            });

            textView.setText(data);

            frontLayout.setOnClickListener(view -> {
                final String displayText = "" + data + " clicked";
                LToast.showShort(displayText, true);
            });
        }
    }
}
