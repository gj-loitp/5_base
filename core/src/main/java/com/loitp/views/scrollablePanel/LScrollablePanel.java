package com.loitp.views.scrollablePanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loitp.R;

import java.util.HashSet;
import java.util.Objects;

import kotlin.Suppress;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public class LScrollablePanel extends FrameLayout {
    protected RecyclerView recyclerView;
    protected RecyclerView headerRecyclerView;
    protected PanelLineAdapter panelLineAdapter;
    protected PanelAdapter panelAdapter;
    protected FrameLayout firstItemView;

    @Suppress(names = "unused")
    public LScrollablePanel(Context context, PanelAdapter panelAdapter) {
        super(context);
        this.panelAdapter = panelAdapter;
        initView();
    }

    public LScrollablePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LScrollablePanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.l_v_scrollable_panel, this, true);
        recyclerView = findViewById(R.id.recyclerContentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        firstItemView = findViewById(R.id.firstItem);
        headerRecyclerView = findViewById(R.id.recyclerHeaderList);
        headerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        headerRecyclerView.setHasFixedSize(true);
        if (panelAdapter != null) {
            panelLineAdapter = new PanelLineAdapter(panelAdapter, recyclerView, headerRecyclerView);
            recyclerView.setAdapter(panelLineAdapter);
            setUpFirstItemView(panelAdapter);
        }
    }

    private void setUpFirstItemView(PanelAdapter panelAdapter) {
        final RecyclerView.ViewHolder viewHolder = panelAdapter.onCreateViewHolder(firstItemView, panelAdapter.getItemViewType(0, 0));
        panelAdapter.onBindViewHolder(viewHolder, 0, 0);
        assert viewHolder != null;
        firstItemView.addView(viewHolder.itemView);
    }

    public void notifyDataSetChanged() {
        if (panelLineAdapter != null) {
            setUpFirstItemView(panelAdapter);
            panelLineAdapter.notifyDataChanged();
        }
    }

    /**
     * @param panelAdapter {@link PanelAdapter}
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setPanelAdapter(PanelAdapter panelAdapter) {
        if (this.panelLineAdapter != null) {
            panelLineAdapter.setPanelAdapter(panelAdapter);
            panelLineAdapter.notifyDataSetChanged();
        } else {
            panelLineAdapter = new PanelLineAdapter(panelAdapter, recyclerView, headerRecyclerView);
            recyclerView.setAdapter(panelLineAdapter);
        }
        this.panelAdapter = panelAdapter;
        setUpFirstItemView(panelAdapter);

    }

    /**
     * Adapter used to bind dataSet to cell View that are displayed within every row of {@link LScrollablePanel}.
     */
    private static class PanelLineItemAdapter extends RecyclerView.Adapter {

        private final PanelAdapter panelAdapter;
        private int row;

        public PanelLineItemAdapter(int row, PanelAdapter panelAdapter) {
            this.row = row;
            this.panelAdapter = panelAdapter;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return Objects.requireNonNull(this.panelAdapter.onCreateViewHolder(parent, viewType));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            this.panelAdapter.onBindViewHolder(holder, row, position + 1);
        }

        @Override
        public int getItemViewType(int position) {
            return this.panelAdapter.getItemViewType(row, position + 1);
        }


        @Override
        public int getItemCount() {
            return panelAdapter.getColumnCount() - 1;
        }

        public void setRow(int row) {
            this.row = row;
        }
    }


    /**
     * Adapter used to bind dataSet to views that are displayed within a{@link LScrollablePanel}.
     */
    private static class PanelLineAdapter extends RecyclerView.Adapter<PanelLineAdapter.ViewHolder> {

        private PanelAdapter panelAdapter;
        private final RecyclerView headerRecyclerView;
        private final RecyclerView contentRV;
        private final HashSet<RecyclerView> observerList = new HashSet<>();
        private int firstPos = -1;
        private int firstOffset = -1;


        public PanelLineAdapter(final PanelAdapter panelAdapter, final RecyclerView contentRV, final RecyclerView headerRecyclerView) {
            this.panelAdapter = panelAdapter;
            this.headerRecyclerView = headerRecyclerView;
            this.contentRV = contentRV;
            initRecyclerView(headerRecyclerView);
            setUpHeaderRecyclerView();

        }

        public void setPanelAdapter(final PanelAdapter panelAdapter) {
            this.panelAdapter = panelAdapter;
            setUpHeaderRecyclerView();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return panelAdapter.getRowCount() - 1;
        }

        @NonNull
        @Override
        public PanelLineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final PanelLineAdapter.ViewHolder viewHolder = new PanelLineAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.l_v_item_content, parent, false));
            initRecyclerView(viewHolder.recyclerView);
            return viewHolder;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PanelLineItemAdapter lineItemAdapter = (PanelLineItemAdapter) holder.recyclerView.getAdapter();
            if (lineItemAdapter == null) {
                lineItemAdapter = new PanelLineItemAdapter(position + 1, panelAdapter);
                holder.recyclerView.setAdapter(lineItemAdapter);
            } else {
                lineItemAdapter.setRow(position + 1);
                lineItemAdapter.notifyDataSetChanged();
            }
            if (holder.firstColumnItemVH == null) {
                RecyclerView.ViewHolder viewHolder = panelAdapter.onCreateViewHolder(holder.firstColumnItemView, panelAdapter.getItemViewType(position + 1, 0));
                holder.firstColumnItemVH = viewHolder;
                panelAdapter.onBindViewHolder(holder.firstColumnItemVH, position + 1, 0);
                assert viewHolder != null;
                holder.firstColumnItemView.addView(viewHolder.itemView);
            } else {
                panelAdapter.onBindViewHolder(holder.firstColumnItemVH, position + 1, 0);
            }
        }


        @SuppressLint("NotifyDataSetChanged")
        public void notifyDataChanged() {
            setUpHeaderRecyclerView();
            notifyDataSetChanged();
        }

        @SuppressLint("NotifyDataSetChanged")
        private void setUpHeaderRecyclerView() {
            if (panelAdapter != null) {
                if (headerRecyclerView.getAdapter() == null) {
                    PanelLineItemAdapter lineItemAdapter = new PanelLineItemAdapter(0, panelAdapter);
                    headerRecyclerView.setAdapter(lineItemAdapter);
                } else {
                    headerRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        public void initRecyclerView(RecyclerView recyclerView) {
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (layoutManager != null && firstPos > 0 && firstOffset > 0) {
                layoutManager.scrollToPositionWithOffset(PanelLineAdapter.this.firstPos + 1, PanelLineAdapter.this.firstOffset);
            }
            observerList.add(recyclerView);
            recyclerView.setOnTouchListener((view, motionEvent) -> {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        for (RecyclerView rv : observerList) {
                            rv.stopScroll();
                        }
                }
                return false;
            });
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    assert linearLayoutManager != null;
                    int firstPos = linearLayoutManager.findFirstVisibleItemPosition();
                    View firstVisibleItem = linearLayoutManager.getChildAt(0);
                    if (firstVisibleItem != null) {
                        int firstRight = linearLayoutManager.getDecoratedRight(firstVisibleItem);
                        for (RecyclerView rv : observerList) {
                            if (recyclerView != rv) {
                                LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
                                if (layoutManager != null) {
                                    PanelLineAdapter.this.firstPos = firstPos;
                                    PanelLineAdapter.this.firstOffset = firstRight;
                                    layoutManager.scrollToPositionWithOffset(firstPos + 1, firstRight);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }

        @Suppress(names = "unused")
        private HashSet<RecyclerView> getRecyclerViews() {
            final HashSet<RecyclerView> recyclerViewHashSet = new HashSet<>();
            recyclerViewHashSet.add(headerRecyclerView);

            for (int i = 0; i < contentRV.getChildCount(); i++) {
                recyclerViewHashSet.add(contentRV.getChildAt(i).findViewById(R.id.recyclerLineList));
            }
            return recyclerViewHashSet;
        }


        static class ViewHolder extends RecyclerView.ViewHolder {
            public RecyclerView recyclerView;
            public FrameLayout firstColumnItemView;
            public RecyclerView.ViewHolder firstColumnItemVH;

            public ViewHolder(View view) {
                super(view);
                this.recyclerView = view.findViewById(R.id.recyclerLineList);
                this.firstColumnItemView = view.findViewById(R.id.firstColumnItem);
                this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            }
        }

    }


}
