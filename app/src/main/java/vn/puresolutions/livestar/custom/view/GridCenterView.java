package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.base.RecyclerAdapter;

/**
 * Created by Phu Tran on 4/10/2016.
 */
public class GridCenterView extends LinearLayout {
    private RecyclerAdapter adapter;
    private int spanNumber;
    private ArrayList<View> views;
    private OnItemClickListener listener;
    protected RecyclerView.AdapterDataObserver dataSetObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }
    };

    public GridCenterView(Context context) {
        this(context, null);
    }

    public GridCenterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridCenterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.GridCenterView, 0, 0);
        try {
            spanNumber = a.getColor(R.styleable.GridCenterView_spanNumber, 2);
        } finally {
            a.recycle();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
        if (views != null) {
            for (int i = 0; i < views.size(); i++) {
                View view = views.get(i);
                OnClickListener lt = createListener(i);
                view.setClickable(lt != null);
                view.setOnClickListener(lt);
            }
        }
    }

    private OnClickListener createListener(int position) {
        return hasItemClickListener() ? new ItemViewListener(listener, position) : null;
    }

    static class ItemViewListener implements OnClickListener {
        int position;
        OnItemClickListener handler;

        ItemViewListener(OnItemClickListener handler, int position) {
            this.position = position;
            this.handler = handler;
        }

        @Override
        public void onClick(View v) {
            handler.onItemClick(v, position);
        }
    }

    public boolean hasItemClickListener() {
        return this.listener != null;
    }

    public void setAdapter(RecyclerAdapter adapter) {
        if (this.adapter != null) {
            this.adapter.unregisterAdapterDataObserver(dataSetObserver);
        }
        this.adapter = adapter;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(dataSetObserver);
        }
        notifyDataSetChanged();
    }

    public RecyclerAdapter getAdapter() {
        return this.adapter;
    }

    protected void notifyDataSetChanged() {
        this.removeAllViews();
        if (views != null) {
            for (View view : views) {
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    parent.removeAllViews();
                }
            }
        }

        LinearLayout linearLayout = null;
        ArrayList<View> items = new ArrayList<>();
        if (adapter != null) {
            int count = adapter.getActualItemCount();
            for (int i = 0; i < count; i++) {
                int index = i % spanNumber;
                if (index == 0 || linearLayout == null) {
                    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    linearLayout = new LinearLayout(getContext());
                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setGravity(Gravity.CENTER);
                    linearLayout.setOrientation(getOrientation() == VERTICAL ? HORIZONTAL : VERTICAL);
                    addView(linearLayout);
                }
                View convertView = null;
                if (views != null) {
                    convertView = (i < views.size()) ? views.get(i) : null;
                }

                if (convertView == null) {
                    convertView = new FrameLayout(getContext());
                }
                RecyclerView.ViewHolder holder = adapter.createViewHolder((ViewGroup) convertView, 0);
                adapter.onBindViewHolder(holder, i);
                View view = holder.itemView;
                if (listener != null) {
                    OnClickListener lt = createListener(i);
                    view.setClickable(true);
                    view.setOnClickListener(lt);
                }
                linearLayout.addView(view);
                items.add(view);
            }
            this.requestLayout();
        }
        this.views = items;
    }
}
